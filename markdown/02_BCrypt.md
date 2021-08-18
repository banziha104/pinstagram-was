# BCrypt Password Encoding

> 사용자의 패스워드와 같이 민감한 정보를 암호화하여 데이터베이스에 저장 

- ### Bean에 등록 

```java
package com.pinstagram.authentication.config;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*...*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*...*/
}
```

<br>

- ### 저장시 암호화 작업 수행

```java
package com.pinstagram.authentication.service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
     
    /*...*/
    @NonNull PasswordEncoder encoder;


    /*...*/
    public String returnTokenWithSave(SignUpDto.CreateRequest request) {
        if (isDuplicateEmail(request.getEmail())) {
            throw new ApiException(ApiResponseCode.SIGNIN_DUPLICATION);
        }

        var entity = new AccountEntity();
        entity.setEmail(request.getEmail());
        entity.setPassword(encoder.encode(request.getPassword())); // 해당부분에서 비밀번호 암호화
        entity.setName(request.getName());
        var savedData = repository.save(entity);
        return generateToken(savedData.getEmail(), savedData.getName(),savedData.getAccountId());
    }
}
```

- ### 조회시 해당 암호와 맞는지 확인 

```java
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
    
    /*...*/
    
    @NonNull PasswordEncoder encoder;
    
    /*...*/

    public SignInDto.SignInResponse signIn(SignInDto.SignInRequest request) throws ApiException {
        var entity = repository.findByEmail(request.getEmail());
        if (entity.isEmpty()) {
            throw new ApiException(ApiResponseCode.USER_NOT_FOUNDED);
        }
        var isMatched = encoder.matches(request.getPassword(), entity.get().getPassword()); // 해당부분에서 암호화된 비밀번호와 맞는지 확인
        if (!isMatched) {
            throw new ApiException(ApiResponseCode.PASSWORD_NOT_CORRECT);
        }
        var data = entity.get();
        var response = new SignInDto.SignInResponse();
        response.setToken(generateToken(data.getEmail(),data.getName(),data.getAccountId()));
        return response;
    }
}
```