package com.pinstagram.authentication;

import com.pinstagram.authentication.dto.SignInDto;
import com.pinstagram.authentication.dto.SignUpDto;
import com.pinstagram.authentication.service.AuthenticationService;
import com.pinstagram.jwt.AuthManager;
import com.pinstagram.response.ApiException;
import com.pinstagram.response.ApiResponse;
import com.pinstagram.response.ApiResponseCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @NonNull AuthenticationService service;

    @NonNull AuthManager authManager;

    @PostMapping("/signup")
    ApiResponse signup(
            Authentication authentication,
            @RequestBody @Validated SignUpDto.CreateRequest request
    ) {
        if (!authManager.isAdmin(authentication)){
            return ApiResponse.createException(new ApiException(ApiResponseCode.UNAUTHORIZED));
        }
        var isDuplicate = service.isDuplicateEmail(request.getEmail());
        if (isDuplicate) {
            return ApiResponse.createException(new ApiException(ApiResponseCode.SIGNIN_DUPLICATION));
        } else {
            var response = new SignUpDto.CreateResponse();
            response.setToken(service.returnTokenWithSave(request));
            return ApiResponse.createOK(response);
        }
    }

    @PostMapping("/signin")
    ApiResponse signIn(
            @RequestBody @Valid SignInDto.SignInRequest request
            ) {
        try {
            return ApiResponse.createOK(service.signIn(request));
        } catch (ApiException e) {
            return ApiResponse.createException(e);
        }
    }
}
