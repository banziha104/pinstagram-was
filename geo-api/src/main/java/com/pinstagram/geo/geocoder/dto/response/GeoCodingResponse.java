package com.pinstagram.geo.geocoder.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoCodingResponse {
    private double latitude;
    private double longitude;
    private String province;
}
