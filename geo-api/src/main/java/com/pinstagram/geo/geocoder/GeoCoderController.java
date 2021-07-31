package com.pinstagram.geo.geocoder;

import com.pinstagram.dto.geo.GeoCodingResponse;
import com.pinstagram.geo.geocoder.service.GeoCoderService;
import com.pinstagram.common.response.ApiResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GeoCoderController {

    @NonNull GeoCoderService service;


    @GetMapping("/")
    ApiResponse<GeoCodingResponse> reverseGeoCoding(
            @RequestParam("latlng") String latlng
            ){
        var newLatlng = Arrays.stream(latlng.split(",")).map(Double::parseDouble).collect(Collectors.toList());
        return ApiResponse.createOK(service.requestReverseGeocoding(newLatlng.get(0),newLatlng.get(1)));
    }
}
