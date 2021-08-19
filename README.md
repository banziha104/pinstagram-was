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
### [5.기술부채](#기술-부채)
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

> 클릭하시면 해당 마크다운 파일로 리다이렉팅 됩니다.

- ### [JWT 인증](https://github.com/banziha104/pinstagram-was/blob/master/markdown/01_JWT.md)
- ### [BCrypt 암호화](https://github.com/banziha104/pinstagram-was/blob/master/markdown/02_BCrypt.md)
- ### [Profile 분할](https://github.com/banziha104/pinstagram-was/blob/master/markdown/03_Profile.md)
- ### [예외 처리](https://github.com/banziha104/pinstagram-was/blob/master/markdown/04_Exception.md)
- ### [공통 API](https://github.com/banziha104/pinstagram-was/blob/master/markdown/05_Common_Api.md)
- ### [MVC Pattern](https://github.com/banziha104/pinstagram-was/blob/master/markdown/06_MVC.md)
- ### [테스팅 및 문서화](https://github.com/banziha104/pinstagram-was/blob/master/markdown/07_Test.md)

<br>

## 데이터베이스 보안

- 현재 각 Application 의 Profile에는 데이터베이스 주소, 유저이름,비밀번호가 명시되어있음.
- 원칙대로라면 이를 k8s에서 ConfigMap을 생성하거나, 환경변수 또는 별도의 파일로 분리하는 것이 맞음
- 그러나 해당프로젝트는 실무 프로젝트가 아니고, 토이 프로젝트이기때문에 어디서나 실행될 수 있어야하기때문에 명시
- 문제가 되는 Prod의 경우, Cloud SQL에서 읽을수 있는 권한은 GKE의 IP(서브넷마스크 32bit, 즉 한개만)로 한정해두어서 다른 곳에서는 접근할 수 없는 선에서 마무리.


<br>

## 기술 부채 

- ### **contents-api와 geometry-api 소통 문제**
    - 현재 구현은 http://localhost:8083/geo 와 같이 전체 URL을 이용해 외부에서 재접근하는 방식으로 구현
    - 배포환경에서는 https://www.coguri.com/geo 와 같이 외부에서 접근 
    - 서비스 매시(istio)와 서비스 디스커버리 및 서비스 DNS(CoreDNS)를 활용해 내부에서 서비스에 접근하는 방식으로 변경예정
    - 아직 istio와 CoreDNS는 충분히 학습이 안되었습니다. 추후 학습후 적용 예정 
- ### **geometry-api 위치 기반 조회의 Full Scan 및 고비용 문제**
    - 현재는 MySQL의 st_distance_sphere()함수를 이용해 주어진 지점에서 거리 기반으로 Full Scan
    - 대안
        1. Spatial Indexing 도입 검토 
        2. 지역별로 중앙지점에 위도,경도,거리 기반으로 해싱함수를 구현하여 접근
    - 개인적으로 2번이 조금더 도전적이고 효율이 높을 것 같아 진행할 예정입니다.
- ### **데이터베이스 분할**
    - 마이크로 아키텍쳐에서 데이터베이스의 주권을 각 컴포넌트가 가지는 패턴도 존재
    - 현재는 하나의 거대한 데이터베이스(Google Cloud SQL)에 모든 서비스들이 접근
    - 추후에 각 Deployment 마다 데이터베이스를 생성하고 Storage에 볼륨을 마운트하는 방식으로 진행 예정
- ### Pageable 처리
    - 현 상태에서는 필요없지만, 그럴일은 없지만 앱이 커진다고 가정했을때 필요함으로 구현 예정
<br>

## 후기

- 마이크로 서비스는 Golang(정말 가끔 Rust), 모노리틱은 Spring Boot로 개발하였는데, 처음으로 Spring Boot를 이용해 마이크로 서비스를 개발해보았습니다.
- Golang보다는 무겁지만 그래도 유지보수적인면에서는 더욱 편리한 것 같습니다.
- 현재 DockerFile이 Jar를 옮기도록 되어있어 조금 아쉽습니다. 하나만 바뀌어도 번번히 빌드해야되는게 아쉽습니다. 
- 데이터베이스를 분할하지 못한게 아쉽습니다. 기술부채에 달아놓았으니 추후에 분할하고자합니다.