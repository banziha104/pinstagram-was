package com.pinstagram.authentication.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignInDto {

    @Getter
    @Setter
    public static class SignInRequest {

        @NotNull
        @Email
        String email;

        @NotNull
        @Size(min = 4, max = 14)
        String password;
    }

    @Getter
    @Setter
    public static class SignInResponse {
        String token;
    }
}
