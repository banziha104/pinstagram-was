# 공통 응답 

- ### ApiResponse기본 객체
    - ApiResponseCode code : 요청 결과를 담은 객체
    - String message : 결과 메세지를 담은 객체
    - int httpCode : Http 상태코드
    - T data : 데이터
    
```java

package com.pinstagram.common.response;


@ToString
@NoArgsConstructor
@Getter
public class ApiResponse<T> {
    public static final ApiResponse<String> DEFAULT_OK = new ApiResponse<>(ApiResponseCode.OK);
    public static final ApiResponse<String> DEFAULT_UNAUTHORIZED = new ApiResponse<>(ApiResponseCode.UNAUTHORIZED);

    private ApiResponseCode code;
    private String message;
    private int httpCode;
    private T data;

    private ApiResponse(ApiResponseCode status) {
        this.bindStatus(status);
    }

    private ApiResponse(ApiResponseCode status, T data) {
        this.bindStatus(status);
        this.data = data;
        this.httpCode = status.getHttpCode();
    }

    private ApiResponse(ApiResponseCode code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.httpCode = code.getHttpCode();
    }

    private ApiResponse(ApiResponseCode code, ApiException e) {
        this.code = code;
        this.message = e.getMessage();
        this.httpCode = code.getHttpCode();
    }

    private void bindStatus(ApiResponseCode status) {
        this.code = status;
        this.message = status.getMessage();
        this.httpCode = status.getHttpCode();
    }


    public static <T> ApiResponse<T> createOK(T data) {
        return new ApiResponse<>(ApiResponseCode.OK, data);
    }

    public static ApiResponse<String> createException(ApiException e) {
        return new ApiResponse<>(e.getStatus(), e);
    }

    public static ApiResponse<String> createException(ApiResponseCode code, String message) {
        return new ApiResponse<>(code, message, "");
    }

    public static <T> ApiResponse<T> createException(ApiResponseCode code, T data) {
        return new ApiResponse<>(code, data);
    }
}

```

<br>

- ### 예외 객체 : ApiResponse.createException() 함수로 예외 발생 

```java

package com.pinstagram.common.response;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private ApiResponseCode status;
    private String message;

    public ApiException(ApiResponseCode status, Exception e) {
        super(e);
        this.status = status;
        this.message = status.getMessage();
    }

    public ApiException(ApiResponseCode status, String message, Exception e) {
        super(e);
        this.status = status;
        this.message = message;
    }

    public ApiException(ApiResponseCode status) {
        this.status = status;
        this.message = status.getMessage();
    }

    public ApiException(ApiResponseCode status, String message) {
        this.status = status;
        this.message = message;
    }
}

```

<br>

- ### 응답 코드 

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

    private final String message;

    public String getKey() {
        return name();
    }

    public String getValue() {
        return message;
    }

    public int getHttpCode(){
        var result = 200;
        switch (this){
            case OK:
                result = 200;
                break;
            case BAD_PARAMETER:
            case SIGNIN_DUPLICATION:
                result = 400;
                break;
            case NOT_FOUND:
            case USER_NOT_FOUNDED:
                result = 404;
                break;
            case UNAUTHORIZED:
                result = 401;
                break;
            case FORBIDDEN:
                result = 403;
                break;
            case PASSWORD_NOT_CORRECT:
            case SERVER_ERROR:
                result = 500;
                break;
            case NO_CONTENTS:
                result = 204;
                break;
            case UNSUPPORTED_MEDIA_TYPE:
                result = 415;
                break;
        }
        return result;
    }
}

```


