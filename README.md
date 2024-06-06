## R&D Spring without JPA 

### Содержание 
- [О проекте](#о-проекте)
- [Built With](#built-with)
- [Задача](#задача)
- [Getting Started](#getting-started)

### О проекте
Цель данного проекта, исследовать возможности работы с РСУБД в Spring приложение без использования JPA(Hibernate).
Существует множество способов взаимодействия с Реляционными БД в Java приложениях и в Spring в частности. Самым популярным способом, остается JPA и его основная реализация Hibernate. Обсуждение, "что выбрать для нового проекта/сервиса JPA или JDBC?" одно из самых актуальных в Spring Community. У JPA есть свои преимущества и не достатки. Мы не будем их обсуждать в рамках этого проекта, я лишь приведу несколько ссылок:
- https://www.baeldung.com/jpa-vs-jdbc
- https://stackoverflow.com/a/75144180
- https://www.geeksforgeeks.org/spring-data-jpa-vs-spring-jdbc-template/
- https://habr.com/ru/companies/otus/articles/686082/


### Built With
- Spring Boot
- Java 21
- Spring JDBC
- Spring Data JDBC
- JOOQ
- Docker Compose
- PostgreSQL
- Flyway
- Lombok
- Junit

### Задача
В качестве предмета исследования мы возьмем всем известную, в мире Spring, модель [petclinic](https://github.com/spring-projects/spring-petclinic). Возьмем не всю модель, а только Owner, Pet и PetType. И реализуем интерфейс OwnerService, который включает в себя следующие методы... Реализация будет выполнена с использованием следующих технологий:
- Чистый Spring JDBC(JdbcClient)
- Spring JDBC + SelectBuilder из библиотеки spring-data-relational
- Spring Data JDBC
- JOOQ

### Getting Started
1. Clone project
2. Go to project directory
3. docker-compose up
```shell
docker-compose -f docker-compose.yaml -p spring-jdbc2 up -d --build
```
4. Run project tests
```shell
./gradlew test
```