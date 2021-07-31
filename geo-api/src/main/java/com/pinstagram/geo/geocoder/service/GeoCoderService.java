package com.pinstagram.geo.geocoder.service;

import com.pinstagram.dto.geo.GeoCodingResponse;
import com.pinstagram.geo.config.ApiKeyConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GeoCoderService {
    @NonNull ApiKeyConfig config;

    private RestTemplate template = new RestTemplate();

    private String generateRequestApiAddress(double lat, double lng){
        return "https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&key="+config.getGoogleApiKey()+"&language=ko";
    }

    public GeoCodingResponse requestReverseGeocoding(double lat,double lng){
       return template.getForEntity(generateRequestApiAddress(lat,lng), GeoCodingResponse.class).getBody();
    }
}
