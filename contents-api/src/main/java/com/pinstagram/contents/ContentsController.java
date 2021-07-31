package com.pinstagram.contents;

import com.pinstagram.contents.dto.UpdateDto;
import com.pinstagram.contents.service.ContentsService;
import com.pinstagram.common.response.ApiException;
import com.pinstagram.common.response.ApiResponse;
import com.pinstagram.contents.dto.CreateDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ContentsController {

    @NonNull
    private final ContentsService service;
    @GetMapping
    ApiResponse getContentsList(
            @RequestParam("latlng") String latlng,
            @RequestParam("limit") double limit
    ){
        var newLatlng = Arrays.stream(latlng.split(",")).map(Double::parseDouble).collect(Collectors.toList());
        return ApiResponse.createOK(service.getByDistance(newLatlng.get(0),newLatlng.get(1),limit));
    }

    @GetMapping("/{contents_id}")
    ApiResponse getContents(
            @PathVariable(name = "contents_id") long contentsId
    ){
        try{
            return ApiResponse.createOK(service.getById(contentsId));
        }catch (ApiException e){
            return ApiResponse.createException(e);
        }
    }

    @PostMapping
    ApiResponse createContents(
          Authentication authentication,
          @RequestBody @Validated CreateDto.Request request
    ){
        try{
            return ApiResponse.createOK(service.create(authentication,request));
        }catch (ApiException e){
            return ApiResponse.createException(e);
        }
    }

    @PutMapping("/{contents_id}")
    ApiResponse updateContents(
            Authentication authentication,
            @PathVariable("contents_id") long contentsId,
            @RequestBody @Validated UpdateDto.Request request
    ){
        try {
            return ApiResponse.createOK(service.update(authentication,request,contentsId));
        }catch (ApiException e){
            return ApiResponse.createException(e);
        }
    }

    @DeleteMapping("/{contents_id}")
    ApiResponse deleteContents(
            Authentication authentication,
            @PathVariable("contents_id") long contentsId
    ){
        try {
            return ApiResponse.createOK(service.deleteEntity(authentication,contentsId));
        }catch (ApiException e){
            return ApiResponse.createException(e);
        }
    }

}
