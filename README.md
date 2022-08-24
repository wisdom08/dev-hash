# dev-hash
- 개발자 관련 물품을 개인 간 채팅을 통해 거래합니다. 
- [Frontend Repository](https://github.com/youngdong82/dev-hash)

## 팀 프로젝트
- 프론트
  - 김영동 https://github.com/youngdong82
- 백엔드
  - 김유정 https://github.com/mediumorange
  - 변지혜 https://github.com/wisdom08
- 개발 기간
  - 2022.08.12 ~ 2022.08.18

## 전체 기능
> 구현한 기능에 대한 좀 더 상세한 내용은 [이슈 탭](https://github.com/wisdom08/dev-hash/issues)을 확인해주세요.
- 인증
  - Spring Security + JWT(ACCESS TOKEN, REFRESH TOKEN)
- 마이페이지
- 상품글 CRUD
- 상품글 찜하기
- AWS S3 업로드
  - 상품 이미지(다중)
  - 프로필 이미지(단일)
- 채팅
  - Spring Websocket + STOMP

## API 문서
https://dev-hash-project.herokuapp.com/swagger-ui/index.html

## ERD
<img width="1174" alt="image" src="https://user-images.githubusercontent.com/61692282/184932454-b9830893-fc7d-44c1-aca2-28a9343b6e44.png">


## 백엔드 배포
https://dev-hash-project.herokuapp.com/
- Heroku

## 개발 환경
- Intellij IDEA Ultimate
- Spring Boot 2.7.2
- Java 17
- Gradle 7.5

