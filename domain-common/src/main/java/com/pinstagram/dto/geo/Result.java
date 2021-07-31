package com.pinstagram.dto.geo;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class Result {
    @lombok.Getter(onMethod_ = {@JsonProperty("address_components")})
    @lombok.Setter(onMethod_ = {@JsonProperty("address_components")})
    private AddressComponent[] addressComponents;
    @lombok.Getter(onMethod_ = {@JsonProperty("formatted_address")})
    @lombok.Setter(onMethod_ = {@JsonProperty("formatted_address")})
    private String formattedAddress;
}

