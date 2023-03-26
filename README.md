# TEST_SHOP

### Description:
- java version: 18 (Amazon Corretto 18.0.2);
- Spring Boot version: 3.0.4;
- Spring Boot security (used Basic authentification);
- database: h2, PostgreSQL (for PostgreSQL, please use _postgres_ prifile);
- database version control: liquibase;
- springdoc-openapi v2.0.2;
- sl4j (log in file);  
- JUnit5.

### Tests:
1) Code covered by unit tests.  
(Tests will be additionaly updated in nearest time)

2) Tests for Postman presented in folder __Postman_test__  
Link: https://github.com/DmitrySheyko/test_shop/tree/master/postman_test  
Tests should be imported in Postman.
(Tests will be additionaly updated in nearest time)

### Functions:
 - creation of user accounts, companies, products, discounts, purchases, notifications, rates, comments;
 - user has function buy poduct and return it in 24 hours;

### Launching:
From IDEA: launch class _src/main/java/com/dmitrySheyko/kameleoon_test_task/KameleoonTestTaskApplication.java_.  
PROFILES: 
- for running with inmemory H2 database, please use empty prifile;  
- for running with PostgreSQL database, please use _postgres_ prifile;  

Admin;
username: admin;  
passwors: admin.

### Docs:
Swagger UI:
1) Launch application;
2) Use link: http://localhost:8080/swagger-ui/index.html

OpenAPI:
1) Launch application;
2) Use link : http://localhost:8080/v3/api-docs

Author: Dmitry Sheyko   
