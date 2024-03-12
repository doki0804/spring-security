package com.example.springsecurity.security.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthDto implements Serializable {
    private static final long serialVersionUID = 7526472295622776147L;
    private String authCd;
    private MenuDto menu;
    private String authNm;
    private String authSelect;
    private String authInsert;
    private String authUpdate;
    private String authDelete;
    private String authDownload;
    private String authPrint;

    @Data
    @AllArgsConstructor
    public static class MenuDto implements Serializable {
        private static final long serialVersionUID = 2987272295622776147L;
        private Long menuSeq;
        private Long menuParentSeq;
        private String proCd;
        private String menuNm;
        private String menuIco;
        private String menuOrder;
        private String useYn;
        private String proUrl;
    }
}
