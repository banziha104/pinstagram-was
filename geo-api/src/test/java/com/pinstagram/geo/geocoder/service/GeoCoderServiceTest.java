package com.pinstagram.geo.geocoder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeoCoderServiceTest {

    @Autowired
    GeoCoderService service;

    @BeforeEach
    void setUp(){

    }

    @Test
    void 리버스_지오코딩(){
        service.requestReverseGeocoding(37.389420,127.122625);
    }
}