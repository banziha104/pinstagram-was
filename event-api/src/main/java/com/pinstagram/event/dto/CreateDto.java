package com.pinstagram.event.dto;

import com.pinstagram.domain.entity.contents.ContentsEntity;
import com.pinstagram.domain.entity.event.EventEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class CreateDto {
    @Getter
    @Setter
    public static class Request{
        @NotNull
        private String title;
        @NotNull
        private String description;
        @NotNull
        private String picture;
        @NotNull
        private double lat;
        @NotNull
        private double lng;
        @NotNull
        private String fullAddress;

        public EventEntity toEntity(){
            var entity = new EventEntity();
            entity.setTitle(title);
            entity.setDescription(description);
            entity.setPicture(picture);
            entity.setLat(lat);
            entity.setLng(lng);
            entity.setFullAddress(fullAddress);
            return entity;
        }
    }
}
