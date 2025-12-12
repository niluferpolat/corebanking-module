# 🏦 MiniBank Backend (Spring Boot)

### Deployed URL
https://corebanking-module-production.up.railway.app/swagger-ui/index.html

MiniBank Backend is a secure REST API built with Spring Boot and PostgreSQL.  
It handles user authentication, account management, fund transfers, and transaction history tracking.

---

## 🚀 Tech Stack

- Java 21  
- Spring Boot  
- Spring Security (JWT Authentication)  
- Hibernate / JPA  
- PostgreSQL  
- Lombok
- Spring Validation
- JJWT (for JWT token)  

---

## 📦 Installation

### 1️⃣ Clone the repository

```bash
git clone https://github.com/niluferpolat/corebanking-module.git
cd corebanking-module
```
### 2️⃣ Configure Database

You can find DB configurations in application.properties
```bash
src/main/resources/application.properties
```
### Example

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/minibank
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

src/main/resources/data.sql already includes an example user and account:

```bash
--added initial user and its account for testing
INSERT INTO users(id, username, email, password, created_at)
VALUES (
    gen_random_uuid(),
    'test_rockerfeller',
    'rockerfeller@hotmail.com',
    '$2a$10$JD9yHyfWAwNco8ZJxx6oMu2FMQWGj8shhnJreYszKpn3IqJTnlv3O',
    NOW()
);
INSERT INTO account(id, number, name, balance, user_id, created_at)
VALUES (
    gen_random_uuid(),
    '1234567890',
    'Test Hesabı',
    1000,
    (SELECT id FROM users WHERE email = 'rockerfeller@hotmail.com'),
    NOW()
);
```
This allows instant login and testing. 
Username and password:
**test_rockerfeller**
***Password.123***

Second User is:

**niluferr**
**Password!123**

# 3️⃣ Run the Backend
```bash
mvn spring-boot:run
```
API will start at http://localhost:8080/

📖 API Documentation (Swagger)
The swagger address is 👉 http://localhost:8080/swagger-ui/index.html

