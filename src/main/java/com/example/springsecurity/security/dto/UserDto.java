package com.example.springsecurity.security.dto;

import java.time.LocalDateTime;

import com.example.springsecurity.security.enums.Role;

import lombok.*;

public class UserDto {
    @Getter
    @Setter
    @AllArgsConstructor
	public static class updateResponse{
        private String userId;
        private String userNm;
        private String auth;
        private String status;
        private String companyCd;
        private String companyNm;
        private String companyPhone;
        private String mobile;
        private String email;
        private Role userRole;
        private String inUserId;
        private LocalDateTime inDt;
    }

    @Getter
    @AllArgsConstructor
    public static class updateRequest{
        private String userId;
        private String userNm;
        private String auth;
        private String status;
        private String companyCd;
        private String companyNm;
        private String companyPhone;
        private String mobile;
        private String email;
        private Role userRole;
    }
    
    @Getter
    @Setter
    @ToString
    public static class insertRequest {
        private String userId;
        private String userPwd;
        private String userNm;
        private String auth;
        private String status;
        private String companyCd;
        private String companyNm;
        private String companyPhone;
        private String mobile;
        private String email;
        private Role userRole;
        private String inUserId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class userInfoResponse {
        private String userId;
        private String userNm;
        private String mobile;
        private String email;
    }
}
