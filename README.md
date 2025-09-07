# Retail App (Spring Boot 3.5, Java 17)

Features:
- REST APIs for Customers, Products, Sales (POST/GET/PUT/PATCH/DELETE)
- Multipart upload endpoint to import products from a `|`-delimited text file
- Sales PDF report by date range using JasperReports
- MySQL + Spring Data JPA
- Validation, global exception handling with consistent JSON errors
- Swagger UI via SpringDoc (visit `/swagger-ui.html`)
- Caching (Spring Cache; in-memory by default)
- Actuator endpoints
- Tests with JUnit + Mockito
- Code quality: PMD, SpotBugs, Sonar (optional)
- CI: GitHub Actions workflow
- Dockerfile for containerization

## Getting Started

1. **MySQL**
   ```sql
   CREATE DATABASE retaildb;
   CREATE USER 'retail'@'%' IDENTIFIED BY 'retail';
   GRANT ALL PRIVILEGES ON retaildb.* TO 'retail'@'%';
   FLUSH PRIVILEGES;
   ```
   Update `src/main/resources/application.yml` if needed.

2. **Build & Run**
   ```bash
   mvn clean verify
   mvn spring-boot:run
   ```

3. **Swagger**
   - http://localhost:8080/swagger-ui.html

4. **Upload Products**
   ```bash
   curl -F "file=@products.txt" http://localhost:8080/api/products/upload
   ```
   `products.txt` format (header optional):
   ```
   title|price|stock
   Effective Java|199.99|5
   Clean Code|149.50|10
   ```

5. **Sales Report PDF**
   ```bash
   curl -L "http://localhost:8080/api/reports/sales?from=2025-01-01&to=2025-12-31" -o sales-report.pdf
   ```

6. **Run Tests**
   ```bash
   mvn -Dtest=* test
   ```

## Docker
```bash
docker build -t retailapp:1.0 .
docker run -p 8080:8080 --env SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/retaildb retailapp:1.0
```

## Sonar
Configure your SonarQube server and run:
```bash
mvn -Dsonar.login=$SONAR_TOKEN sonar:sonar
```

## Notes
- For Redis caching or Kafka events, you can add the relevant starters and configuration.
