# 예외 처리

- ### 커스텀 예외 객체 정의

```java

package com.pinstagram.common.response;

@Getter
public class ApiException extends RuntimeException {

    private ApiResponseCode status;
    private String message;

    /*...*/
}

```

<br>

- ### Service Layer에서 예외가 발생 할 수 있는 메소드임을 알리고, 예외 발생

```java
package com.pinstagram.authentication.service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    /*...*/
    
    public SignInDto.SignInResponse signIn(SignInDto.SignInRequest request) throws ApiException { // throws 로 예외 발생 가능 명시
        var entity = repository.findByEmail(request.getEmail());
        if (entity.isEmpty()) {
            throw new ApiException(ApiResponseCode.USER_NOT_FOUNDED); // 예외 발생 
        }
        var isMatched = encoder.matches(request.getPassword(), entity.get().getPassword());
        if (!isMatched) {
            throw new ApiException(ApiResponseCode.PASSWORD_NOT_CORRECT); // 예외 발생 
        }
        var data = entity.get();
        var response = new SignInDto.SignInResponse();
        response.setToken(generateToken(data.getEmail(), data.getName(), data.getAccountId()));
        return response;
    }
    
    /*...*/
}

```

<br>

- ### Try Catch 문으로 예외 발생시 처리

```java
package com.pinstagram.authentication;


@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    
    /*...*/
    @PostMapping("/signin")
    ApiResponse signIn(
            @RequestBody @Valid SignInDto.SignInRequest request
    ) {
        try {
            return ApiResponse.createOK(service.signIn(request));
        } catch (ApiException e) { // 해당부분에서 예외 발생
            return ApiResponse.createException(e);
        }
    }
}

```

- ### 예외 발생에 따른 응답  

```java

package com.pinstagram.common.response;

@ToString
@NoArgsConstructor
@Getter
public class ApiResponse<T> {
    
    /*...*/
    
    public static ApiResponse<String> createException(ApiException e) {
        return new ApiResponse<>(e.getStatus(), e);
    }

    /*...*/

}

```

```java
package com.pinstagram.common.response;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode {
    OK("요청이 성공하였습니다."),
    BAD_PARAMETER("요청 파라미터가 잘못되었습니다."),
    NOT_FOUND("리소스를 찾지 못했습니다."),
    SIGNIN_DUPLICATION("중복된 아이디 입니다."),
    UNAUTHORIZED("리소스에 접근하기 위한 권한이 없습니다"),
    FORBIDDEN("보유한 권한으로 접근할수 없는 리소스 입니다."),
    PASSWORD_NOT_CORRECT("패스워드가 일치하지 않습니다"),
    SERVER_ERROR("서버 에러입니다."),
    NO_CONTENTS("송신할 컨텐츠가 없습니다"),
    USER_NOT_FOUNDED("유저 정보가 없습니다"),
    UNSUPPORTED_MEDIA_TYPE("Unsupported Media Type");
}
```
