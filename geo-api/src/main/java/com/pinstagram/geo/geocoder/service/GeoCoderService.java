package com.pinstagram.geo.geocoder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.pinstagram.dto.geo.ReverseGeoCodingResponse;
import com.pinstagram.geo.config.ApiKeyConfig;
import com.pinstagram.geo.geocoder.dto.KakaoGeoCodingResponse;
import com.pinstagram.geo.geocoder.dto.response.GeoCodingResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GeoCoderService {
    @NonNull ApiKeyConfig config;

    @NonNull ObjectMapper mapper;

    private RestTemplate template = new RestTemplate();

    private Gson gson = new Gson();

    private String generateGeoCodingUrl(String address) {
        return "https://dapi.kakao.com/v2/local/search/address.json?analyze_type=similar&page=1&size=10&query=" + address;
    }

    private String generateReverseGeoCodingUrl(double lat, double lng) {
        return "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=" + config.getGoogleApiKey() + "&language=ko";
    }

    public ReverseGeoCodingResponse requestReverseGeocoding(double lat, double lng) {
        return template.getForEntity(generateReverseGeoCodingUrl(lat, lng), ReverseGeoCodingResponse.class).getBody();
    }

    public GeoCodingResponse requestAddressResponse(String address) {
        var response = new GeoCodingResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + config.getKakaoApiKey());
        HttpEntity request = new HttpEntity(headers);
        System.out.println("네트워크 시작");
        var data = template.exchange(
                generateGeoCodingUrl(address),
                HttpMethod.GET,
                request,
                KakaoGeoCodingResponse.class
        ).getBody();
        System.out.println(data.toString());
        response.setLatitude(Double.parseDouble(data.getDocuments().get(0).getY()));
        response.setLongitude(Double.parseDouble(data.getDocuments().get(0).getX()));
        response.setProvince(data.getDocuments().get(0).getAddress().getRegion2depthName() +" " +data.getDocuments().get(0).getAddress().getRegion3depthHName());
        return response;
    }
}
