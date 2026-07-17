# Holidays Recipes

[![CI](https://github.com/cluttazi/holidays-recipes/actions/workflows/ci.yml/badge.svg)](https://github.com/cluttazi/holidays-recipes/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-1.3-brightgreen.svg)](https://spring.io/projects/spring-boot)

A Spring Boot web application where home chefs share holiday recipes from their culture, and find suppliers for hard-to-source cultural ingredients.

## Overview

Many traditional holiday recipes call for ingredients that are not available locally. Holidays Recipes connects the people who cook those recipes with the suppliers who can provide the ingredients:

- **Holiday Chef** – any registered user who shares their recipes
- **Holiday** – a cultural celebration that occurs in a specific time period
- **Recipe** – a set of instructions and the ingredients they require
- **Cultural Ingredient** – an ingredient that is not locally available
- **Cultural Ingredient Supplier** – a supplier able to provide a specific cultural ingredient
- **Culture of the Recipe** – the combination of the recipe's country of origin and the chef's origin

## Tech Stack

| Layer | Technology |
| --- | --- |
| Framework | Spring Boot 1.3 (Spring MVC, Spring Data JPA, Spring Security) |
| Views | Thymeleaf, Bootstrap, jQuery |
| Persistence | MySQL, Hibernate |
| Integrations | Google Maps API, Facebook API |
| Build | Maven (wrapper included) |

## Getting Started

### Prerequisites

- JDK 8
- MySQL 5.x running locally

### Database Setup

Create the schema and point the application at it in `src/main/resources/application.properties`:

```sql
CREATE DATABASE `holiday-recipes`;
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/holiday-recipes
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
```

The schema is created automatically on first run (`spring.jpa.hibernate.ddl-auto=update`).

### Run

```bash
./mvnw spring-boot:run
```

The application starts on <http://localhost:8080>.

### Build

```bash
./mvnw -B package
```

This produces a deployable WAR under `target/`.

## Project Structure

```
src/main/java/com/chrisluttazi/holidaysrecipes/
├── config/                 # Spring configuration (security, MVC)
├── webcontrollers/         # Spring MVC controllers
├── model/                  # JPA entities (Recipe, Holiday, Ingredient, ...)
│   └── persistance/
│       ├── dao/            # Data access objects
│       └── service/        # Service layer
src/main/resources/
├── templates/              # Thymeleaf views (recipe, holiday, supplier, ...)
├── static/                 # CSS, JS, fonts
└── application.properties  # Datasource and JPA configuration
```

## Continuous Integration

Every push and pull request is built with Maven on JDK 8 via [GitHub Actions](.github/workflows/ci.yml).

## License

This project is licensed under the MIT License – see [LICENSE](LICENSE) for details.
