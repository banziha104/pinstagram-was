
package com.pinstagram.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class ApiResponse<T> {
    public static final ApiResponse<String> DEFAULT_OK = new ApiResponse<>(ApiResponseCode.OK);
    public static final ApiResponse<String> DEFAULT_UNAUTHORIZED = new ApiResponse<>(ApiResponseCode.UNAUTHORIZED);

    private ApiResponseCode code;
    private String message;
    private T data;

    private ApiResponse(ApiResponseCode status) {
        this.bindStatus(status);
    }

    private ApiResponse(ApiResponseCode status, T data) {
        this.bindStatus(status);
        this.data = data;
    }

    private ApiResponse(ApiResponseCode code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ApiResponse(ApiResponseCode code, ApiException e) {
        this.code = code;
        this.message = e.getMessage();
    }

    private void bindStatus(ApiResponseCode status) {
        this.code = status;
        this.message = status.getMessage();
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
