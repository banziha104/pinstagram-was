package com.pinstagram.authentication.service;

import com.pinstagram.authentication.dto.SignInDto;
import com.pinstagram.authentication.dto.SignUpDto;
import com.pinstagram.authentication.repository.AccountRepository;
import com.pinstagram.domain.entity.auth.AccountEntity;
import com.pinstagram.common.jwt.AuthManager;
import com.pinstagram.common.jwt.JwtManager;
import com.pinstagram.common.response.ApiException;
import com.pinstagram.common.response.ApiResponseCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @NonNull AccountRepository repository;

    @NonNull JwtManager jwtManager;

    @NonNull AuthManager authManager;

    @NonNull PasswordEncoder encoder;

    private String generateToken(String name, Long id) {
        return jwtManager.createToken(name, id);
    }

    private boolean isDuplicateEmail(String email) {
        return repository.existsByEmail(email);
    }

    public SignInDto.SignInResponse signIn(SignInDto.SignInRequest request) throws ApiException {

        var entity = repository.findByEmail(request.getEmail());
        if (entity.isEmpty()) {
            throw new ApiException(ApiResponseCode.NO_CONTENTS);
        }
        var isMatched = encoder.matches(request.getPassword(), entity.get().getPassword());
        if (!isMatched) {
            throw new ApiException(ApiResponseCode.PASSWORD_NOT_CORRECT);
        }
        var data = entity.get();
        var response = new SignInDto.SignInResponse();
        response.setToken(generateToken(data.getName(), data.getAccountId()));
        return response;
    }

    public String returnTokenWithSave(SignUpDto.CreateRequest request) {
        if (isDuplicateEmail(request.getEmail())) {
            throw new ApiException(ApiResponseCode.SIGNIN_DUPLICATION);
        }

        var entity = new AccountEntity();
        entity.setEmail(request.getEmail());
        entity.setPassword(encoder.encode(request.getPassword()));
        entity.setName(request.getName());
        var savedData = repository.save(entity);
        return generateToken(savedData.getEmail(), savedData.getAccountId());
    }
}
