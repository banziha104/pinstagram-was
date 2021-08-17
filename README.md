# Pinstagram Web Application Server

> Java & Spring Boot 를 이용한 WAS 프로젝트

<br>

## Pinstagram 프로젝트

- ### [📱 Pinstagram Android (Kotlin & AndroidX)](https://github.com/banziha104/pinstagram_android)
- ### [🍃 Pinstagram WAS (Spring Boot)](https://github.com/banziha104/pinstagram-was)
- ### [🚚 Pinstagram DevOps (GKE & K8s & Helm)](https://github.com/banziha104/pinstagram_charts)
- ### [🕳 Pinstagram Socket (Node.js & Socket.io)](https://github.com/banziha104/pinstagram_socket)

<br>

## Document 

- #### [Auth Document](https://www.coguri.shop/auth/docs/index.html)
- #### [Contents Document](https://www.coguri.shop/contents/docs/index.html)
- #### [Geometry Document](https://www.coguri.shop/geometry/docs/index.html)

<br>

## 모듈 구조 

- api-common : 공통 모듈로, 어플리케이션에 쓰이는 공통 응답 및 JWT 토큰 생성 및 파싱과 관련된 모듈 
- domain-common : 공통 모듈로, 엔티티를 정의하고 어플리케이션간 소통에 사용될 공통된 객체 등을 정의 
- auth-api : JWT와 인증과 관련된 어플리케이션
- contents-api : 컨텐츠에 관한 통신을 관리하는 어플리케이션
- geo-api : 지오코딩, 리버스 지오코딩을 관리하는 어플리케이션

![module]()
