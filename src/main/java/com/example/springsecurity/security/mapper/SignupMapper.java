package com.example.springsecurity.security.mapper;

import com.example.springsecurity.security.dto.SignupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SignupMapper {
    SignupMapper INSTANCE = Mappers.getMapper(SignupMapper.class);

    @Mapping(target = "auth", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "companyPhone", ignore = true)
    @Mapping(target = "userRole", ignore = true)
    SignupDto.SignupUserDto requestToUserDto(SignupDto.SignupRequest request);
}
