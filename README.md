# Pinstagram Web Application Server

> Java & Spring Boot 를 이용한 마이크로 아키텍쳐 WAS 프로젝트

<br>

## 목차 

### [1.Pinstagram 프로젝트](#Pinstagram-프로젝트)
### [2.API 문서](#API-문서)
### [3.모듈 구조](#모듈-구조)
### [4.주요 구현 사항](#주요-구현-사항)
### [5.데이터베이스 보안](#데이터베이스-보안)
### [6.기술부채](#기술-부채)
### [7.후기](#후기)

<br>

## Pinstagram 프로젝트

![서비스](https://github.com/banziha104/pinstagram_android/blob/master/markdown/images/service.png)

- ### 기획 배경
  - 새로운 기술을 도입해야 될 때마다 새로운 토이프로젝트를 만들었는데(그러다보니 레파지토리가 250개를 넘었습니다..), 이렇게 계속 진행하다보니 다른 기술들과 유기적으로 연결되지 못하는 느낌을 받았습니다.
  - 이를 개선하고자 하나의 토이 프로젝트를 만들고, 이를 지속적으로 개선해나가는 것이 좋겠다고 판단하여 시작한 프로젝트입니다.
- ### 서비스 내용
  - 기획 내용은 단순히 특정한 위치에 사진을 올리고, 이를 볼 수 있는 서비스입니다.
  - 개발자가 처음으로 하는 프로젝트인 헬로우월드, 그리고 그 다음으로 많이하는 계산기, 그리고 그와 쌍두마차를 이루는 기본적인 게시판 어플의 확장 버전입니다.
- ### 개인적인 규약
  - Git Submodule 미활용 : WAS와 Android의 경우 각 모듈별로 git submodule에 등록하는게 효율적이지만, 레파지토리가 너무 많아지면 관리가 안될 것 같아서 통합된 Git으로 관리합니다.
  - 기획 디자인 최소화 : 개발적인 프로젝트이기 때문에 사업성, 당위성 등은 최대한 배제하고 개발하기에 깔끔한 기획과 디자인만 가져갑니다. 그렇기 때문에 기획 디자인적으로 뜬금없는 페이지(예: Talk)가 들어갈 수 있습니다
  - 기술부채 : 한시적으로 끝나는 프로젝트가 아닌, 지속적인 프로젝트임으로, 현재 가능한 기술을 사용하고, 볼륨 및 러닝커브로 못 가져간 기술은 천천히 도입할 예정입니다.
- ### 서비스 주요 기능
  - Home : 특정 위치에서 1KM 반경내에 있는 데이터들을 그리드뷰 형식으로 보여줍니다
  - Map : 특정 위치에서 1KM 반경내에 있는 데이터들을 지도 형식으로 보여줍니다.
  - Talk : Socket 통신을 이용한 메세지입니다. 위치기반으로 하는 지역톡 개념이아닌 서비스 이용자 전체와 소통합니다.
  - Write : 특정 위치에 게시물을 명시할 수 있습니다.
  - Auth : 로그인, 로그아웃, 회원가입 등을 진행할 수 있습니다.

- ### 프로젝트 목록
  - ### [📱 Pinstagram Android (Kotlin & AndroidX)](https://github.com/banziha104/pinstagram_android)
  - ### [🍃 Pinstagram WAS (Spring Boot)](https://github.com/banziha104/pinstagram-was)
  - ### [🚚 Pinstagram DevOps (GKE & K8s & Helm)](https://github.com/banziha104/pinstagram_charts)
  - ### [🕳 Pinstagram Socket (Node.js & Socket.io)](https://github.com/banziha104/pinstagram_socket)
  
<br>

## API 문서 

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
- 문제가 되는 Prod의 경우, Cloud SQL의 Private IP와 GCP VPC로 설정해두어서 다른 곳에서는 접근할 수 없는 선에서 마무리.


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
- ### **테스트 서버 구축**
    - 비용 문제로 테스트 서버를 구축하지 못함.
    - minikube로 별도 테스트 서버를 구축 예정.
- ### CI/CD
    - 젠킨스 설치 위치(클라우드에 도커로 올릴지, 로컬에서 설치할지)를 고민중.
  
<br>

## 후기

- 마이크로 서비스는 Golang(정말 가끔 Rust), 모노리틱은 Spring Boot로 개발하였는데, 처음으로 Spring Boot를 이용해 마이크로 서비스를 개발해보았습니다.
- Golang보다는 무겁지만 그래도 유지보수적인면에서는 더욱 편리한 것 같습니다.
- 현재 DockerFile이 Jar를 옮기도록 되어있어 조금 아쉽습니다. 하나만 바뀌어도 번번히 빌드해야되는게 아쉽습니다. 
- 데이터베이스를 분할하지 못한게 아쉽습니다. 기술부채에 달아놓았으니 추후에 분할하고자합니다.