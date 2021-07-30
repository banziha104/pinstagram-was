package com.pinstagram.authentication.service;

import com.pinstagram.auth.AccountEntity;
import com.pinstagram.authentication.dto.SignInDto;
import com.pinstagram.authentication.dto.SignUpDto;
import com.pinstagram.authentication.exception.UserNotFoundedException;
import com.pinstagram.authentication.repository.AccountRepository;
import com.pinstagram.jwt.AuthManager;
import com.pinstagram.jwt.JwtManager;
import com.pinstagram.response.ApiException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @NonNull AccountRepository repository;

    @NonNull JwtManager jwtManager;

    @NonNull AuthManager authManager;

    private String generateToken(String name, Long id){
        return jwtManager.createToken(name,id);
    }

    public boolean isDuplicateEmail(String email){
        return repository.existsByEmail(email);
    }


    public SignInDto.SignInResponse signIn(SignInDto.SignInRequest request) throws ApiException {
        var entity = repository.findByEmailAndPassword(request.getEmail(),request.getPassword());
        if (entity.isPresent()){
            var data = entity.get();
            var response = new SignInDto.SignInResponse();
            response.setToken(generateToken(data.name, data.id));
            return response;
        }else{
            throw new UserNotFoundedException();
        }
    }

    public String returnTokenWithSave(SignUpDto.CreateRequest request){
        var entity = new AccountEntity();
        entity.email = request.getEmail();
        entity.password = request.getPassword();
        entity.name = request.getName();
        var savedData = repository.save(entity);
        return generateToken(savedData.email,savedData.id);
    }
}
