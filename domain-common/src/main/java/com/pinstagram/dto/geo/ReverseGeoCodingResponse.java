package com.pinstagram.dto.geo;

import com.fasterxml.jackson.annotation.*;

@lombok.Data
public class ReverseGeoCodingResponse {
    @lombok.Getter(onMethod_ = {@JsonProperty("plus_code")})
    @lombok.Setter(onMethod_ = {@JsonProperty("plus_code")})
    private PlusCode plusCode;
    @lombok.Getter(onMethod_ = {@JsonProperty("results")})
    @lombok.Setter(onMethod_ = {@JsonProperty("results")})
    private Result[] results;
}
