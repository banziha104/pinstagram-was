package com.pinstagram.event;

import com.pinstagram.common.response.ApiException;
import com.pinstagram.common.response.ApiResponse;
import com.pinstagram.event.dto.CreateDto;
import com.pinstagram.event.service.EventService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class EventController {

    // Kubernetes Ingress에서 헬스체크를 위한 메소드
    // readinessProbe 로 헬스체크

    @NonNull
    private EventService service;

    @GetMapping("/healthCheck")
    ApiResponse healthCheck(){
        return ApiResponse.createOK(0);
    }

    @GetMapping
    ApiResponse getEventList(
            @RequestParam("latlng") String latlng,
            @RequestParam("limit") double limit
    ){
        var newLatlng = Arrays.stream(latlng.split(",")).map(Double::parseDouble).collect(Collectors.toList());
        return ApiResponse.createOK(service.getByDistance(newLatlng.get(0),newLatlng.get(1),limit));
    }

    @GetMapping("/{event_id}")
    ApiResponse getEvent(
            @PathVariable(name = "event_id") long eventId
    ){
        try{
            return ApiResponse.createOK(service.getById(eventId));
        }catch (ApiException e){
            return ApiResponse.createException(e);
        }
    }

    @PostMapping
    ApiResponse createEvent(
            Authentication authentication,
            @RequestBody @Validated CreateDto.Request request
    ){
        try{
            return ApiResponse.createOK(service.create(authentication,request));
        }catch (ApiException e){
            return ApiResponse.createException(e);
        }
    }
}
