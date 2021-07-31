package com.pinstagram.contents.service;

import com.pinstagram.common.response.ApiException;
import com.pinstagram.contents.dto.CreateDto;
import com.pinstagram.contents.dto.RetrieveDto;
import com.pinstagram.contents.dto.UpdateDto;
import com.pinstagram.domain.entity.contents.ContentsEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ContentsService {
    //Get
    RetrieveDto.Response getById(long id) throws ApiException;
    List<RetrieveDto.Response> getByDistance(double lat, double lng, double limit);

    //Post
    long create(Authentication authentication, CreateDto.Request request) throws ApiException;

    //Update
    long update(Authentication authentication, UpdateDto.Request request, long id) throws ApiException;


    //Delete
    long deleteEntity(Authentication authentication, long contentsId) throws ApiException;
}
