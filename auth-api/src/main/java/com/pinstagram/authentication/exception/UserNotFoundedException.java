package com.pinstagram.authentication.exception;

import com.pinstagram.response.ApiException;
import com.pinstagram.response.ApiResponseCode;

public class UserNotFoundedException extends ApiException {
    public UserNotFoundedException() {
        super(ApiResponseCode.USER_NOT_FOUNDED);
    }
}
