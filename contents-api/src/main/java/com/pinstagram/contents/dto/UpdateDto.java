package com.pinstagram.contents.dto;

import com.pinstagram.domain.entity.contents.ContentsEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class UpdateDto {
    @Getter
    @Setter
    public static class Request {
        private String title;
        private String description;
        private String picture;
        private String tag;

        public ContentsEntity update(ContentsEntity origin){
            origin.setTitle(title);
            origin.setDescription(description);
            origin.setPicture(picture);
            origin.setTag(tag);
            origin.setUpdateAt(LocalDateTime.now());
            return origin;
        }
    }

}
