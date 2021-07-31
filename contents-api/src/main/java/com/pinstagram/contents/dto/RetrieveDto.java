package com.pinstagram.contents.dto;

import com.pinstagram.domain.entity.auth.AccountEntity;
import com.pinstagram.domain.entity.contents.ContentsEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

public class RetrieveDto {
    @Getter
    @Setter
    public static class Response{
        private long contentsId;
        private String title;
        private String description;
        private String fullAddress;
        private String picture;
        private String tag;
        private double lat;
        private double lng;
        private Account account;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;

        public static Response fromEntity(ContentsEntity entity){
            var response = new Response();
            response.setContentsId(entity.getContentsId());
            response.setTitle(entity.getTitle());
            response.setDescription(entity.getDescription());
            response.setFullAddress(entity.getFullAddress());
            response.setPicture(entity.getPicture());
            response.setTag(entity.getTag());
            response.setLat(entity.getLat());
            response.setLng(entity.getLng());
            response.setAccount(Account.fromEntity(entity.getAccount()));
            response.setCreateAt(entity.getCreateAt());
            response.setUpdateAt(entity.getUpdateAt());
            return response;
        }
    }

    @Getter
    @Setter
    public static class Account{
        private long accountId;
        private String name;
        private String email;

        public static Account fromEntity(AccountEntity entity){
            var account = new Account();
            account.setAccountId(entity.getAccountId());
            account.setEmail(entity.getEmail());
            account.setName(entity.getName());
            return account;
        }
    }

}
