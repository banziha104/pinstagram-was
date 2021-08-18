# Pinstagram Web Application Server

> Java & Spring Boot 를 이용한 마이크로 아키텍쳐 WAS 프로젝트

<br>

## Pinstagram 프로젝트

- ### [📱 Pinstagram Android (Kotlin & AndroidX)](https://github.com/banziha104/pinstagram_android)
- ### [🍃 Pinstagram WAS (Spring Boot)](https://github.com/banziha104/pinstagram-was)
- ### [🚚 Pinstagram DevOps (GKE & K8s & Helm)](https://github.com/banziha104/pinstagram_charts)
- ### [🕳 Pinstagram Socket (Node.js & Socket.io)](https://github.com/banziha104/pinstagram_socket)

<br>
## 목차 

### [1.API 문서](#Document)
### [2.모듈 구조](#모듈-구조)
### [3.주요 구현 사항](#주요-구현-사항)
### [4.데이터베이스 보안](#데이터베이스-보안)
### [5.기술부채](#기술부채)
### [6.후기](#후기)

## Document 

- #### [Auth Document](https://www.coguri.shop/auth/docs/index.html)
- #### [Contents Document](https://www.coguri.shop/contents/docs/index.html)
- #### [Geometry Document](https://www.coguri.shop/geometry/docs/index.html)

<br>

## 모듈 구조 

이름          | 개요                                                                   | 구분         |
|---------------|------------------------------------------------------------------------|--------------|
| api-common    | 어플리케이션에 쓰이는 공통 응답 및 JWT 토큰 생성 및 파싱과 관련된 모듈 | 공통 모듈    |
| domain-common | 엔티티를 정의하고 어플리케이션간 소통에 사용될 공통된 객체 등을 정의   | 공통 모듈    |
| auth-api      | JWT와 인증과 관련된 어플리케이션                                       | 어플리케이션 |
| contents-api  | 컨텐츠에 관한 통신을 관리하는 어플리케이션                             | 어플리케이션 |
| geo-api       | 지오코딩, 리버스 지오코딩을 관리하는 어플리케이션                      | 어플리케이션 |

<br>

![module](https://github.com/banziha104/pinstagram-was/blob/master/markdown/images/module.png)

<br>

## 주요 구현 사항 

- ### [JWT 인증](https://github.com/banziha104/pinstagram-was/blob/master/markdown/01_JWT.md)
- ### [BCrypt 암호화](https://github.com/banziha104/pinstagram-was/blob/master/markdown/02_BCrypt.md)
- ### [Profile 분할](https://github.com/banziha104/pinstagram-was/blob/master/markdown/03_Profile.md)
- ### [예외 처리](https://github.com/banziha104/pinstagram-was/blob/master/markdown/04_Exception.md)
- ### [공통 API](https://github.com/banziha104/pinstagram-was/blob/master/markdown/05_Common_Api.md)
- ### [MVC Pattern](https://github.com/banziha104/pinstagram-was/blob/master/markdown/06_MVC.md)
- ### [테스팅 및 문서화](https://github.com/banziha104/pinstagram-was/blob/master/markdown/07_Test.md)

<br>

## 데이터베이스 보안

<br>

## 기술 부채 

- contents-api와 geometry-api 소통 문제 
    - 현재 구현은 http://localhost:8083/geo 와 같이 전체 URL을 이용해 외부에서 재접근하는 방식으로 구현
    - 배포환경에서는 https://www.coguri.com/geo 와 같이 외부에서 접근 
    - 서비스 매시(istio)와 서비스 디스커버리 및 서비스 DNS(CoreDNS)를 활용해 내부에서 서비스에 접근하는 방식으로 변경예정
    - 아직 istio와 CoreDNS는 충분히 학습이 안되었습니다. 추후 학습후 적용 예정 
- geometry-api 위치 기반 조회의 Full Scan 및 고비용 문제
    - 현재는 MySQL의 st_distance_sphere()함수를 이용해 주어진 지점에서 거리 기반으로 Full Scan
    - 대안
        1. Spatial Indexing 도입 검토 
        2. 지역별로 중앙지점에 위도,경도,거리 기반으로 해싱함수를 구현하여 접근
    - 개인적으로 2번이 조금더 도전적이고 효율이 높을 것 같아 진행할 예정입니다.
- 데이터베이스 분할
    - 마이크로 아키텍쳐에서 데이터베이스의 주권을 각 컴포넌트가 가지는 패턴도 존재
    - 현재는 하나의 거대한 데이터베이스(Google Cloud SQL)에 모든 서비스들이 접근
    - 추후에 각 Deployment 마다 데이터베이스를 생성하고 Storage에 볼륨을 마운트하는 방식으로 진행 예정
<br>

## 후기


