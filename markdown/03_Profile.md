# Profile 분할 

| 이름                  | 개요                                              | 사용 데이터베이스           |
|-----------------------|---------------------------------------------------|-----------------------------|
| application.yml       | 기본 프로필, 공통의 요소를 명시                   |                             |
| application-test.yml  | 테스트 환경에서 실행되는 어플리케이션 프로필      | H2                          |
| application-local.yml | 로컬 환경에서 실행되는 어플리케이션 프로필        | Local MySQL 8.x             |
| application-prod.yml  | GKE에서 실행되는 어플리케이션 프로필                                                | Google Cloud SQL(MySQL 8.x) |

<br>

- ### Base 

```yaml
server:
  port: 8082
  servlet:
    context-path: /contents
spring:
  profiles:
    active: prod
  jpa:
    hibernate:
      naming:
        physical-strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
      use-new-id-generator-mappings: false
  jackson:
    serialization.write-dates-as-timestamps: false
  main:
    allow-bean-definition-overriding: true
  flyway:
    enabled: false
logging:
  level:
    root: info
    org:
      hibernate:
        type: trace
```

<br>

- ### Test

```yaml
server:
  port: 8082

spring:
  datasource:
    username: sa
    password:
    url: jdbc:h2:tcp://localhost:9092/mem:testdb;MVCC=TRUE
    driver-class-name: org.h2.Driver
    hikari:
      jdbc-url: jdbc:h2:mem:testdb
  jpa:
    database: h2
    show-sql: true
    generate-ddl: true

    hibernate:
      ddl-auto: none

    properties:
      hibernate:
        dialect: org.hibernate.dialect.H2Dialect
        format_sql: true
        jdbc:
          lob:
            non_contextual_creation: true

  h2:
    console:
      enabled: true
  sql:
    init:
      mode: always
      platform: h2


server-address:
  geoServerAddress : http://localhost:8083/geometry
  contentsServerAddress : http://localhost:8082/contents
  authServerAddress : http://localhost:8081/auth
```

<br>

- ### Local

```yaml
spring:
  datasource:
    hikari:
      jdbcUrl: jdbc:mysql://localhost:3306/pinstagram?useUnicode=true&characterEncoding=utf8
      username: pinstagram
      password: test1234
      driver-class-name: com.mysql.jdbc.Driver
      connectionTimeout: 30000
      idleTimeout: 600000
      maxLifetime: 1800000
      minimumIdle: 40
      maximumPoolSize: 40
  jpa:
    database: mysql
    show-sql: false
    generate-ddl: false
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL5InnoDBDialect
  h2:
    console:
      enabled: false

server-address:
  geoServerAddress : http://localhost:8083/geometry
  contentsServerAddress : http://localhost:8082/contents
  authServerAddress : http://localhost:8081/auth
```

<br>

- ### Production

```yaml
spring:
  datasource:
    hikari: #https://github.com/brettwooldridge/HikariCP#configuration-knobs-baby
      jdbcUrl: jdbc:mysql://34.64.133.74:3306/pinstagram?useUnicode=true&characterEncoding=utf8
      username: pinstagram
      password: test1234
      driver-class-name: com.mysql.jdbc.Driver
      connectionTimeout: 30000
      idleTimeout: 600000
      maxLifetime: 1800000
      minimumIdle: 40
      maximumPoolSize: 40
  jpa:
    database: mysql
    show-sql: false
    generate-ddl: false
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL5InnoDBDialect
  h2:
    console:
      enabled: false
server-address:
  geoServerAddress : http://coguri.shop/geometry
  contentsServerAddress : http://coguri.shop/contents
  authServerAddress : http://coguri.shop/auth
```