package com.example.springsecurity.security.dto;

import com.example.springsecurity.security.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class SignupDto {
    @Getter
    @Setter
    @ToString
    public static class SignupRequest {
        private String userId;
        private String userPwd;
        private String userNm;
        private String mobile;
        private String email;
        private String telNo;
        private String postNo;
        private String address;
        private String addressDetail;
        private String inUserId;
    }

    @Getter
    @Setter
    @ToString
    public static class SignupUserDto {
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

}



