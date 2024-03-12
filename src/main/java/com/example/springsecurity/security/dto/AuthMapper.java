package com.example.springsecurity.security.dto;

import com.example.springsecurity.security.entity.Auth;
import com.example.springsecurity.security.entity.Menu;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    @Mappings({
            @Mapping(source = "id.authCd", target = "authCd"),
            @Mapping(source = "menu", target = "menu"),
            @Mapping(source = "authNm", target = "authNm"),
            @Mapping(source = "authSelect", target = "authSelect"),
            @Mapping(source = "authInsert", target = "authInsert"),
            @Mapping(source = "authUpdate", target = "authUpdate"),
            @Mapping(source = "authDelete", target = "authDelete"),
            @Mapping(source = "authDownload", target = "authDownload"),
            @Mapping(source = "authPrint", target = "authPrint"),
    })
    AuthDto toAuthDto(Auth auth);

    @Mappings({
            @Mapping(source = "menuSeq", target = "menuSeq"),
            @Mapping(source = "menuParentSeq", target = "menuParentSeq"),
            @Mapping(source = "menuNm", target = "menuNm"),
            @Mapping(source = "menuIco", target = "menuIco"),
    })
    AuthDto.MenuDto toMenuDto(Menu menu);

    default List<AuthDto.MenuDto> toMenuDtoListFromAuthList(List<Auth> authList) {
        if (authList == null || authList.isEmpty()) {
            return Collections.emptyList();
        }

        List<AuthDto.MenuDto> menuDtos = new ArrayList<>();
        for (Auth auth : authList) {
            if (auth.getMenu() != null) {
                menuDtos.add(toMenuDto(auth.getMenu()));
            }
        }
        return menuDtos;
    }

    default List<AuthDto> toAuthList(List<Auth> authList) {
        if(authList == null || authList.isEmpty()) {
            return Collections.emptyList();
        }
        List<AuthDto> authDtos = new ArrayList<>();
        for(Auth auth : authList) {
            if(auth.getId().getAuthCd() != null){
                authDtos.add(toAuthDto(auth));
            }
        }
        return authDtos;
    }
}
