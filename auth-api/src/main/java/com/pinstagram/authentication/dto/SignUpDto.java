package com.pinstagram.authentication.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SignUpDto {
    @Getter
    @Setter
    public static class CreateRequest {
        @NotNull
        @Email
        String email;
        @NotNull
        String password;
        String name;
    }

    @Getter
    @Setter
    public static class CreateResponse{
        String token;
    }
}
