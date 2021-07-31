package com.pinstagram.dto.geo;
import com.fasterxml.jackson.annotation.*;

@lombok.Data
public class PlusCode {
    @lombok.Getter(onMethod_ = {@JsonProperty("compound_code")})
    @lombok.Setter(onMethod_ = {@JsonProperty("compound_code")})
    private String compoundCode;
    @lombok.Getter(onMethod_ = {@JsonProperty("global_code")})
    @lombok.Setter(onMethod_ = {@JsonProperty("global_code")})
    private String globalCode;
}