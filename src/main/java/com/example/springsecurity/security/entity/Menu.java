package com.example.springsecurity.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MENU")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuSeq;
    private Long menuParentSeq;
    private String menuNm;
    private String menuIco;
    private String menuNav;

}
