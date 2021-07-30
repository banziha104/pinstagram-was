package com.pinstagram.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@Getter
public class ApiResponseList {
    private List<?> contents;

    public ApiResponseList(List<?> contents) {
        this.contents = contents;
    }

    public static ApiResponseList of(List<?> contents) {
        return new ApiResponseList(contents);
    }
}
