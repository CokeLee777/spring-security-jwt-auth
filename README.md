<a name="readme-top"></a>

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

# spring-security-jwt-auth

Spring Boot, Spring security, jjwt 를 사용하여 jwt, auth를 지원하는 api 서버를 쉽게 사용할 수 있도록 만든 오픈 소스입니다.

## Contributor

| <p align="center"><img src="https://github.com/CokeLee777/spring-security-jwt-auth/assets/65009713/61bff8b2-f1bc-4387-b0fb-fc9d559b6552" width="120" height="120"/></p> CokeLee777 | <p align="center"><img src="https://github.com/CokeLee777/spring-security-jwt-auth/assets/65009713/281b2a6b-ae51-44f4-a557-858645bae0ba" width="120" height="120"/></p> JSY8869 |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <div align=center> [GitHub](https://github.com/CokeLee777) </div>                                                                                                                  | <div align=center> [GitHub](https://github.com/JSY8869) </div>                                                                                                                  |

## 특징(Features)

- JWT 인증을 통한 유저 회원가입과 로그인
- BCrypt를 사용한 비밀번호 암호화 - 솔팅 암호화 알고리즘을 이용해서 보안 강화
- 유저의 권한에 근거한 Spring Security 기반 인가
- 접근 성공, 실패 시 처리 규정 커스터마이징
- 로그아웃 과정 커스터마이징
- Access Token
- Refresh Token
- Cookie

## 사용 기술(Technologies)

- Spring Boot 3.0
- Spring Security
- JSON Web Tokens (JWT)
- BCrypt
- Gradle

## 사용법(How To Use?)

### 1. 보안 전략 선택(Choose your security strategy)

우선, 용도에 따라 어떤 소스를 사용할 것인지 선택하여야 합니다.

우리는 아래와 같은 소스 코드를 제공합니다.

| Database | Memory | Jpa |
| --- | --- | --- |
| Security | JWT | JWT |
| Security | OAUTH | OAUTH |

사용할 데이터베이스와 Security 전략에 따라 선택하여 주십시오.

### 2. 프로젝트 세팅(Set your project)

1. 우선, build.gradle 파일의 내용에 따라 spring boot 개발 환경을 세팅하세요.
2. [application.properties](http://application.properties) 내에 아래와 같은 코드를 작성합니다. (우리의 프로젝트에는 기본적으로 해당 코드가 포함되어 있습니다.)

```yaml
# 커스텀 NOT FOUND ERROR를 생성하기 위해 기본 page not found exception 제거
spring:
  mvc:
    throw-exception-if-no-handler-found: true
  web:
    resources:
      add-mappings: false
```

1. 당신의 보안 전략에 맞는 폴더를 프로젝트에 적용시킵니다.
   - 우리의 소스 코드의 구조는 폴더로 분리되어 있습니다.
   - `**common` 폴더는 모든 전략에서 사용되는 코드를 포함하고 있기 때문에 정상적인 동작을 위해 해당 폴더의 파일들을 모두 프로젝트에 포함시켜야 합니다.**
   - `Choose your security strategy` 단계에서 선택한 전략에 맞는 폴더의 파일들을 프로젝트에 저장합니다.

### 3. 커스터마이징(Customize)

- 우리의 프로젝트튼 Lombok을 사용하지 않습니다. Lombok을 사용하고자 한다면 이를 수정하여 사용할 수 있습니다.
- User를 직접 만들고 싶은 경우에는`common.entity.User` interface를 구현하여 주세요.
   - 우리의 프로젝트에서 JWT 토큰에는 유저의 Id(PK)와 Identifier, Password가 들어갑니다. 이에 주의하여 주세요.
- 아래와 같이 정의된 END_POINT를 커스터마이징 하세요.

| LOGOUT_END_POINT | PUBLIC_END_POINT | ANNONYMOUS_END_POINT |
| --- | --- | --- |
| “/user/sign-out” | “/” | "/", "/users/sign-in", "/users/sign-up" |
- JWT를 사용하는 경우 TokenProperties의 `SECRET_KEY`, `REFRESH_KEY`를 커스타미이징 해주세요. 이는 토큰 생성 시 비밀 키의 역할을 하며 보안을 위해 외부에 노출되지 않도록 주의하여 주세요.
- `UserRepository`나 `UserService`를 커스타미이징 할 때는 `common` 폴더 내의 인터페이스를 상속받아 구현하여야 합니다.

# 토큰 정보(Informations of Token)

## JWT

- JWT 토큰은 [JJWT](https://github.com/jwtk/jjwt) 라이브러리를 통해 생성합니다.
- 자세한 응답은 [API 명세서](https://github.com/CokeLee777/spring-security-jwt-auth/wiki/API-Specification)를 참고하세요.
- Refresh Token은 Set-Cookie라는 이름으로 헤더를 통해 전달합니다. **반드시 사용자는 쿠키에 Refresh Token을 저장하여야 합니다.**

# JWT 인증 및 인가 동작 원리

### 인증(Authentication) - 로그인(Sign In)

**[대략적인 인증 흐름]**

![Authentication Flow](./asset/images/authentication_flow_diagram.png)

**[실제 인증 흐름]**

1. 사용자가 로그인 경로('/users/sign-in)로 로그인 요청을 시도하게 됩니다.
2. AntPathRequestMatcher는 요청한 URL이 로그인 요청인지 검증합니다.
   - 요청한 URL이 로그인 요청이 아니라면 다음 필터로 넘어가게 됩니다.
3. 현재 요청한 사용자가 이미 인증되었는지 아닌지 확인하고, 이미 인증이 된 사용자라면 403 FORBIDDEN 응답을 내보내게 됩니다.
4. 사용자가 보낸 아이디, 비밀번호 정보를 검증하고 검증이 완료되었다면 가짜 인증객체를 만들어서 AuthenticationManager에게 실제 인증을 요청합니다.
5. AuthenticationManager는 JwtAuthenticationProvider에게 실제 인증을 위임하고, JwtAuthenticationProvider는 실제 DB에서 사용자 아이디와 비밀번호를 검증합니다.
   - 사용자의 아이디가 존재하지 않거나 비밀번호가 다르다면 401 UNAUTHORIZED 응답을 내보내게 됩니다.
6. 인증이 완료되었다면 실제 인증 객체를 만들어서 반환하고 스프링이 전역적으로 참조할 수 있도록 시큐리티 컨텍스트에 저장합니다.
7. 마지막으로 모든 과정이 성공적으로 마쳤다면 사용자에게 Access Token과 Refresh Token을 발급하여 응답합니다.

### 인가(Authorization) - 로그인 유지 또는 사용자 권한 검증

**[대략적인 인증 흐름]**

![Authorization Flow](./asset/images/authorization_flow_diagram.png)

**[실제 인증 흐름]**

1. 사용자가 원하는 아무 페이지나 요청을 시도합니다.
2. 사용자의 토큰 정보를 확인합니다.
   - 사용자의 토큰이 없고, Anonymous 페이지 요청이라면 다음 필터로 이동합니다.
   - 사용자의 토큰이 없고, 인증이 필요한 페이지 요청이라면 403 FORBIDDEN 응답을 내보내게 됩니다.
3. 사용자의 Jwt Access Token을 검증한다. 단순히 토큰이 만료되었다면 Jwt Refresh Token 검증 단계로 넘어갑니다.
   - 토큰이 변조되었다면 예외를 발생시키고 상황에 맞는 응답(400, 401, 403)을 내보내게 됩니다.
4. 사용자의 Jwt Refresh Token을 검증합니다.
   - 토큰이 만료되었다면 304 MOVED PERMANENTLY(/users/sign-in) 응답을 내보내게 됩니다.
   - 토큰이 변조되었다면 예외를 발생시키고 상황에 맞는 응답(400, 401, 403)을 내보내게 됩니다.
5. 모든 과정이 정상적으로 성공했다면 다음 필터로 이동합니다.

### 로그아웃(Sign out) - 로그아웃 또는 사용자 권한 해제

**[대략적인 인증 흐름]**

![Sign out Flow](./asset/images/signout_flow_diagram.png)

**[실제 인증 흐름]**

1. 인증된 사용자가 로그아웃 요청을 시도합니다.
2. AntPathRequestMatcher는 요청한 URL이 로그아웃 요청인지 검증합니다.
   - 요청한 URL이 로그아웃 요청이 아니라면 다음 필터로 넘어갑니다.
3. 시큐리티 컨텍스트에서 인증 객체를 꺼내서 핸들러에게 넘겨줍니다.
4. 핸들러는 리프레쉬 토큰이 들어있는 쿠키를 삭제하고, 시큐리티 컨텍스트를 비우게 됩니다.
   - 액세스 토큰이 있는 헤더는 클라이언트가 헤더에서 삭제하도록 합니다.
5. 모든 과정이 성공적으로 마쳤다면 304 MOVED PERMANENTLY(/users/sign-in) 응답을 내보내게 됩니다.

### OAUTH

[추후 작성]

## [기능 정의서(**Functional Specification)**](https://github.com/CokeLee777/spring-security-jwt-auth/wiki/Functional-Specification)

## [API 명세서(API Specification)](https://github.com/CokeLee777/spring-security-jwt-auth/wiki/API-Specification)

## **기여하는 방법(How to contribute?)**

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See [LICENSE](./LICENSE) for more information.

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/CokeLee777/spring-security-jwt-auth.svg?style=for-the-badge
[contributors-url]: https://github.com/CokeLee777/spring-security-jwt-auth/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/CokeLee777/spring-security-jwt-auth.svg?style=for-the-badge
[forks-url]: https://github.com/CokeLee777/spring-security-jwt-auth/network/members
[stars-shield]: https://img.shields.io/github/stars/CokeLee777/spring-security-jwt-auth.svg?style=for-the-badge
[stars-url]: https://github.com/CokeLee777/spring-security-jwt-auth/stargazers
[issues-shield]: https://img.shields.io/github/issues/CokeLee777/spring-security-jwt-auth.svg?style=for-the-badge
[issues-url]: https://github.com/CokeLee777/spring-security-jwt-auth/issues
[license-shield]: https://img.shields.io/github/license/CokeLee777/spring-security-jwt-auth.svg?style=for-the-badge
[license-url]: https://github.com/CokeLee777/spring-security-jwt-auth/blob/master/LICENSE.txt
