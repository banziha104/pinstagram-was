package com.pinstagram.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
