package com.pinstagram.authentication;

import com.pinstagram.authentication.dto.SignInDto;
import com.pinstagram.authentication.dto.SignUpDto;
import com.pinstagram.authentication.service.AuthenticationService;
import com.pinstagram.common.jwt.AuthManager;
import com.pinstagram.common.response.ApiException;
import com.pinstagram.common.response.ApiResponse;
import com.pinstagram.common.response.ApiResponseCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @NonNull AuthenticationService service;

    @NonNull AuthManager authManager;

    // Kubernetes Ingress에서 헬스체크를 위한 메소드
    // readinessProbe 로 헬스체크
    @GetMapping("/healthCheck")
    ApiResponse healthCheck(){
        return ApiResponse.createOK(0);
    }

    @PostMapping("/signup")
    ApiResponse signup(
            @RequestBody @Validated SignUpDto.CreateRequest request
    ) {
        try{
            var response = new SignUpDto.CreateResponse();
            response.setToken(service.returnTokenWithSave(request));
            return ApiResponse.createOK(response);
        }catch (ApiException e){
            return ApiResponse.createException(e);
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
