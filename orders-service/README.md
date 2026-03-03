# orders-service

Microservicio base para gestion de ordenes usando Spring Boot.

## Ejecutar local

```bash
./mvnw spring-boot:run
```

## Probar endpoint

```bash
curl -X POST http://localhost:8081/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{"customerId":"CUST-001","amount":120.50}'
```
