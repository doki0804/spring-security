package com.example.springsecurity.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class AuthId implements Serializable {

    @Column(name = "menu_seq")
    private Long menuSeq;

    @Column(name = "auth_cd")
    private String authCd;

}