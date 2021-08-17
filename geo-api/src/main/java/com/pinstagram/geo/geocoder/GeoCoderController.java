package com.pinstagram.geo.geocoder;

import com.pinstagram.dto.geo.ReverseGeoCodingResponse;
import com.pinstagram.geo.geocoder.dto.KakaoGeoCodingResponse;
import com.pinstagram.geo.geocoder.dto.response.GeoCodingResponse;
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

    // Kubernetes Ingress에서 헬스체크를 위한 메소드
    // readinessProbe 로 헬스체크
    @GetMapping("/healthCheck")
    ApiResponse healthCheck(){
        return ApiResponse.createOK(0);
    }

    @GetMapping("/")
    ApiResponse<ReverseGeoCodingResponse> reverseGeoCoding(
            @RequestParam("latlng") String latlng
            ){
        var newLatlng = Arrays.stream(latlng.split(",")).map(Double::parseDouble).collect(Collectors.toList());
        return ApiResponse.createOK(service.requestReverseGeocoding(newLatlng.get(0),newLatlng.get(1)));
    }

    @GetMapping("/geo")
    ApiResponse<GeoCodingResponse> geoCoding(
            @RequestParam("address") String address
    ){
        return ApiResponse.createOK(service.requestAddressResponse(address));
    }
}
