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
2. [x] Toolchain: point wrapper `distributionUrl` at Maven 3.9.16.
3. [x] Upgrade parent to Spring Boot 3.5.16 / Java 21; migrate coordinates
   (`mysql-connector-j`, `thymeleaf-extras-springsecurity6`), drop unused
   mail/context-support deps; javax→jakarta code migration; Security 6
   config; Spring Data 3 repository API; property renames. (One atomic
   commit — the tree must build at every commit.)
4. [x] Tests: add H2-backed smoke tests (context load + MVC security
   behavior) — the repo has none, and the migration needs a safety net.
5. [x] Fix real bugs found, one regression test each.
6. [x] Best practices: `.gitignore`/README reality check (.gitignore and
   .editorconfig were already in good shape; README updated).
7. [x] CI: temurin 21 + maven cache, mirroring the locally verified build.
8. [x] Final "Done vs Deferred" summary below.

## 3. Done vs Deferred

### Done (PR-style summary)

**Toolchain**
- Maven wrapper distribution 3.6.3 → 3.9.16 (script was already
  wrapper 3.3.4 `only-script`, and the distribution URL already pointed at
  Maven Central, which is reachable here).
- `java.version` 1.8 → 21 (the only installed JDK; also current LTS).

**Framework: Spring Boot 1.3.0.RELEASE → 3.5.16** (one atomic commit so
every commit builds). Migration-guide changes applied:
- `javax.*` → `jakarta.*` (persistence, validation, transaction, servlet);
  Hibernate-Validator-specific `@NotEmpty`/`@Email` → `jakarta.validation`
  equivalents (`@Length` kept — still current).
- Security 6: `WebSecurityConfigurerAdapter` → `SecurityFilterChain` bean;
  `antMatchers` → `requestMatchers`; deprecated `AntPathRequestMatcher` →
  `PathPatternRequestMatcher`; `@EnableGlobalMethodSecurity` →
  `@EnableMethodSecurity`. JDBC authentication + BCrypt kept identical.
  `/error` added to permitAll: Security 6 authorizes ERROR dispatches, so
  without it the registered 404/401 error pages would break for anonymous
  users (behavior preservation, not a loosening — the page only prints
  "Error handling").
- Boot 3 API moves: `SpringBootServletInitializer`, `ErrorController`
  (removed `getErrorPath()`), `EmbeddedServletContainerCustomizer` →
  `WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>`.
- Spring Data 3: repositories now extend `JpaRepository`;
  `findOne`/`delete(id)` call sites → `findById`/`deleteById`; fixed ID
  generics that were always wrong but tolerated in 2015 (`User` id is
  `String`, `Authority` id is `Integer`); removed an underivable
  `delete(Long)` declaration on `AuthorityDAO`.
- Pinned `GenerationType.IDENTITY` on every `@GeneratedValue` id so
  Hibernate 6 keeps using the MySQL `AUTO_INCREMENT` columns that
  Hibernate 4's `AUTO` produced (protects existing databases).
- Dependencies: `mysql:mysql-connector-java` → `com.mysql:mysql-connector-j`;
  `thymeleaf-extras-springsecurity4` → `…springsecurity6`; added
  `spring-boot-starter-validation` (no longer transitive); removed unused
  `com.sun.mail:javax.mail` and `spring-context-support` (zero imports).
- Properties: `multipart.maxFileSize` → `spring.servlet.multipart.max-file-size`;
  removed Tomcat-JDBC-era `testWhileIdle`/`validationQuery` (Hikari),
  `MySQL5Dialect` (auto-detected) and `ImprovedNamingStrategy` (Boot 3's
  default physical naming produces the same snake_case names).

**Tests (repo previously had none)** — 8 tests, all green:
- Context load against H2 in MySQL mode (validates all Hibernate 6 entity
  mappings, repositories, security config).
- MVC smoke tests: public pages 200, protected page → login redirect,
  admin-driven user creation persists with BCrypt-encoded password.
- Behavior pinned, not changed: anonymous POST `/register` is denied
  because `UserService.create` is `@PreAuthorize("hasAuthority('ADMIN')")`
  — this was already true on Boot 1.3/Security 4 (see Deferred).

**Bug fix (with regression test verified to fail before the fix)**
- `RecipeWebController.randomCampaign()/randomMarket()` crashed with
  `IllegalArgumentException` (`Random.nextInt(0)`) on an empty database,
  turning every `/cheff/recipe` page into a 500 on fresh installs.

**CI** — `.github/workflows/ci.yml`: temurin 8 → temurin 21
(checkout@v4/setup-java@v4 + Maven cache already current); `./mvnw -B
package` now also runs the test suite; the exact command was verified
locally on JDK 21.0.10.

**Security posture** — leaving the EOL 2015 stack resolves the known CVE
exposure of Spring Framework 4.2 / Spring Security 4.0 / Tomcat 8.0 /
Hibernate 4.3 / Jackson 2.6 / MySQL Connector 5.x (e.g. the
Jackson-databind polymorphic deserialization RCE family, Spring4Shell-class
issues, and dozens of Tomcat 8.0 CVEs). All managed versions now come from
the actively maintained Boot 3.5.x BOM.

### Verification status

| Check | Before | After |
|---|---|---|
| `./mvnw -B package` (JDK 21) | FAIL (plugin stack incompatible with module system) | PASS |
| Tests | none existed | 8/8 PASS (H2-backed) |
| WAR produced | no | `target/holidays-recipes-1.0-SNAPSHOT.war` |

### Deferred (deliberate, with rationale)

- **Anonymous registration is non-functional** (`/register` POST →
  redirected to login; `UserService.create` requires ADMIN). Pre-existing
  since 2015, clearly at odds with the public register page, but fixing it
  *loosens* security (anonymous account creation), so it is a product
  decision, not a mechanical modernization. The unused, unannotated
  `UserService.register()` looks like the intended hook.
- **`Recipe.previous` self-reference maps its join column over the
  `recipe_id` PK column** — Hibernate tolerates it (read-only association)
  and the H2 context test passes, but the mapping is fragile; a rename to
  a dedicated `previous_recipe_id` column would require a data migration
  for existing databases.
- **Spring Boot 4.x / Spring Framework 7** (released Nov 2025): stayed on
  the 3.5.x line per the "current 3.x" conservative target; 3.5 has OSS
  support well into 2026 and the 3→4 jump has its own migration guide.
- **Thymeleaf deprecated fragment syntax**: templates use the unwrapped
  `th:replace="fragments/x"` form; Thymeleaf 3.1 only warns. Rewriting all
  27 templates to `~{...}` is mechanical but touches every view for zero
  behavior change — left for a template-focused pass.
- **Hard-coded DB credentials and a Google Maps API key** in
  `application.properties` / `fragments/googleAPI.html` (both already
  public in git history). Moving them to environment variables changes the
  deployment contract; flagged rather than done.
- **`spring.jpa.hibernate.ddl-auto=update` in production** — convenient
  but risky; a migration tool (Flyway/Liquibase) is the modern practice.
- **HTTP (not HTTPS) Google Maps script URL** in `googleAPI.html` and the
  legacy Facebook fragment; front-end assets (Bootstrap 3, jQuery, Quill)
  are vendored and outdated — a front-end refresh is out of scope for a
  behavior-preserving pass.
