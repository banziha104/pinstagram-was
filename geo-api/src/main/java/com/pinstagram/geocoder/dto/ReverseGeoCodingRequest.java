package com.pinstagram.geocoder.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReverseGeoCodingRequest {
    @NotNull
    double lat;

    @NotNull
    double lng;
}
