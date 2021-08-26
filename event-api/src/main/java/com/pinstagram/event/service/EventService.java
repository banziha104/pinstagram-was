package com.pinstagram.event.service;

import com.pinstagram.common.response.ApiException;
import com.pinstagram.event.dto.CreateDto;
import com.pinstagram.event.dto.RetrieveDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EventService{
    //Get
    RetrieveDto.Response getById(long id) throws ApiException;

    List<RetrieveDto.Response> getByDistance(double lat, double lng, double limit);

    //Post
    long create(Authentication authentication, CreateDto.Request request) throws ApiException;
}