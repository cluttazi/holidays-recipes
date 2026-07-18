# Modernization Plan — holidays-recipes

Audit date: 2026-07-18 · Branch: `claude/repo-modernization-audit-6se2sn`

## 1. Audit findings

### What this repo is

A Spring Boot MVC + Thymeleaf + Spring Data JPA web application (recipe /
holiday CRUD with form login, JDBC-backed auth, MySQL storage, WAR
packaging with provided Tomcat). 51 Java files, ~3,000 LOC, no tests.

### Stack as found

| Component | Found | Latest stable | Status |
|---|---|---|---|
| Spring Boot parent | **1.3.0.RELEASE** (Nov 2015) | 3.5.16 (3.x line) | EOL since 2019; multiple known CVEs across Spring Framework 4.2 / Spring Security 4.0 / Tomcat 8.0 / Hibernate 4.3 / Jackson 2.6 (e.g. Spring4Shell CVE-2022-22965 class, Jackson databind RCE chain CVE-2017-7525 et al.) |
| Java target | 1.8 | 21 LTS | Build **fails** on JDK 21 (only JDK installed) |
| Maven wrapper distribution | 3.6.3 | 3.9.16 | wrapper script 3.3.4 (`only-script`) is current |
| `mysql:mysql-connector-java` | 5.x (managed) | `com.mysql:mysql-connector-j` 9.x | Coordinates relocated; 5.x EOL, CVE-2019-2692 etc. |
| `com.sun.mail:javax.mail` | 1.5.x (managed) | — | **Unused** (no `javax.mail` import anywhere) |
| `org.springframework:spring-context-support` | 4.2.x (managed) | — | **Unused** (only needed for mail/caching/quartz glue) |
| `thymeleaf-extras-springsecurity4` | 2.x | `…-springsecurity6` | Must track Security major |
| CI (`.github/workflows/ci.yml`) | checkout@v4 / setup-java@v4 / temurin 8 | temurin 21 | Action majors already current; JDK is not |

### Baseline verification (local, JDK 21.0.10, Maven wrapper)

- `./mvnw -B package` → **FAIL**: `ExceptionInInitializerError: Unable to make
  field … java.util.TreeMap.comparator accessible` from the 2015-era
  maven-war-plugin/xstream stack. Spring Boot 1.3's plugin management cannot
  run on the JDK 21 module system. There is no JDK 8 in this environment, so
  the repo currently **cannot be built at all** here.
- Tests: none exist (`src/test` absent).

### Code-level findings (migration surface)

- `javax.persistence`, `javax.validation`, `javax.transaction`,
  `javax.servlet` imports throughout → jakarta.
- Deprecated/removed Hibernate Validator constraints
  (`org.hibernate.validator.constraints.NotEmpty/Length/Email`) → `jakarta.validation.constraints`.
- `WebSecurityConfigurerAdapter` (removed in Security 6) → `SecurityFilterChain` bean;
  `antMatchers` → `requestMatchers`; `@EnableGlobalMethodSecurity` → `@EnableMethodSecurity`.
- `SpringBootServletInitializer` moved packages; `EmbeddedServletContainerCustomizer`
  → `WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>`;
  `ErrorController.getErrorPath()` removed.
- Spring Data 3: `PagingAndSortingRepository` no longer extends `CrudRepository`;
  `findOne(id)`/`delete(id)` are gone → repositories extend `CrudRepository`
  too, call sites use `findById`/`deleteById`.
- Bare `@GeneratedValue` (AUTO): Hibernate 4 picked MySQL `AUTO_INCREMENT`
  (IDENTITY); Hibernate 6 would switch to a sequence/table strategy and break
  existing schemas → pin `GenerationType.IDENTITY` to preserve behavior.
- `application.properties`: `spring.jpa.hibernate.naming-strategy=ImprovedNamingStrategy`
  (Boot 3's default physical naming strategy produces the same snake_case),
  `multipart.maxFileSize` → `spring.servlet.multipart.max-file-size`,
  Tomcat-JDBC-era `testWhileIdle`/`validationQuery` obsolete under Hikari,
  `MySQL5Dialect` no longer needed (auto-detected).
- Pre-existing latent bugs (candidates for regression-tested fixes):
  `Recipe.previous` is mapped to the same column as the `recipeId` PK;
  `RecipeWebController.randomCampaign()/randomMarket()` call
  `Random.nextInt(list.size())` which throws on empty tables.

## 2. Prioritized checklist

1. [x] Audit + this plan (commit first).
2. [ ] Toolchain: point wrapper `distributionUrl` at Maven 3.9.16.
3. [ ] Upgrade parent to Spring Boot 3.5.16 / Java 21; migrate coordinates
   (`mysql-connector-j`, `thymeleaf-extras-springsecurity6`), drop unused
   mail/context-support deps; javax→jakarta code migration; Security 6
   config; Spring Data 3 repository API; property renames. (One atomic
   commit — the tree must build at every commit.)
4. [ ] Tests: add H2-backed smoke tests (context load + MVC security
   behavior) — the repo has none, and the migration needs a safety net.
5. [ ] Fix real bugs found, one regression test each.
6. [ ] Best practices: `.gitignore`/README reality check.
7. [ ] CI: temurin 21 + maven cache, mirroring the locally verified build.
8. [ ] Final "Done vs Deferred" summary below.

## 3. Done vs Deferred

Filled in at the end of the modernization pass — see bottom of file.
