package com.example.springsecurity.security.entity;

import com.example.springsecurity.security.enums.Role;
import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    private static final long serialVersionUID = -7322436202241003579L;

	@Id
	@Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String userNm;

    @Column(nullable = false)
    private String userPwd;

    @Column(name = "AUTH")
    private String auth;

    private String status;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Role userRole;

    private String inUserId;
    private String upUserId;
    private String lockYn;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime locktime;
    private Long trycnt;

    @PrePersist
    public void prePersist() {
        status = status == null ? "Y" : status;
        lockYn = lockYn == null ? "N" : lockYn;
        trycnt = trycnt == null ? 0 : trycnt;
    }

    public void updateUser(String userNm, String mobile, String email) {
        this.userNm = userNm;
        this.mobile = mobile;
        this.email = email;
    }

    public void tryCntUpdate() {
        this.trycnt = trycnt+1;
    }


}
