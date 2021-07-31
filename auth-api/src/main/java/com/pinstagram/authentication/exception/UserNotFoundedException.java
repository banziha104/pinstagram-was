package com.pinstagram.authentication.exception;

import com.pinstagram.common.response.ApiException;
import com.pinstagram.common.response.ApiResponseCode;

public class UserNotFoundedException extends ApiException {
    public UserNotFoundedException() {
        super(ApiResponseCode.USER_NOT_FOUNDED);
    }
}
