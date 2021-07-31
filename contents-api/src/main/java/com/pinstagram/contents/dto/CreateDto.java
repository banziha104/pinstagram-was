package com.pinstagram.contents.dto;

import com.pinstagram.domain.entity.contents.ContentsEntity;
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
        private String tag;
        @NotNull
        private double lat;
        @NotNull
        private double lng;

        public ContentsEntity toEntity(){
            var entity = new ContentsEntity();
            entity.setTitle(title);
            entity.setDescription(description);
            entity.setPicture(picture);
            entity.setLat(lat);
            entity.setLng(lng);
            entity.setTag(tag);
            return entity;
        }
    }
}
