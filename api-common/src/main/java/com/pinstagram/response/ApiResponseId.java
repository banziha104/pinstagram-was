package com.pinstagram.response;

import lombok.Getter;

@Getter
public class ApiResponseId {
    private Long id;

    public ApiResponseId(Long id) {
        this.id = id;
    }

//    public static RegistCarDto.ResponseOk of(Long id) {
//        return new RegistCarDto.ResponseOk(id);
//    }
}
