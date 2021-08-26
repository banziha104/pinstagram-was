package com.pinstagram.event.dto;

import com.pinstagram.domain.entity.auth.AccountEntity;
import com.pinstagram.domain.entity.contents.ContentsEntity;
import com.pinstagram.domain.entity.event.EventEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class RetrieveDto {
    @Getter
    @Setter
    public static class Response{
        private long eventId;
        private String title;
        private String description;
        private String fullAddress;
        private String picture;
        private double lat;
        private double lng;

        public static Response fromEntity(EventEntity entity){
            var response = new Response();
            response.setEventId(entity.getEventId());
            response.setTitle(entity.getTitle());
            response.setDescription(entity.getDescription());
            response.setFullAddress(entity.getFullAddress());
            response.setPicture(entity.getPicture());
            response.setLat(entity.getLat());
            response.setLng(entity.getLng());
            return response;
        }
    }
}
