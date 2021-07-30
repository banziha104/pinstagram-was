package com.pinstagram.geocoder;

import com.pinstagram.dto.GeoCodingResponse;
import com.pinstagram.geocoder.config.ServerAddressConfig;
import com.pinstagram.geocoder.dto.ReverseGeoCodingRequest;
import com.pinstagram.geocoder.service.GeoCoderService;
import com.pinstagram.response.ApiResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GeoCoderController {

    @NonNull ServerAddressConfig config;
    @NonNull GeoCoderService service;


    @GetMapping("/")
    ApiResponse<GeoCodingResponse> reverseGeoCoding(
            @RequestParam("latlng") String latlng
            ){
        var newLatlng = Arrays.stream(latlng.split(",")).map(Double::parseDouble).collect(Collectors.toList());
        return ApiResponse.createOK(service.requestReverseGeocoding(newLatlng.get(0),newLatlng.get(1)));
    }
}
