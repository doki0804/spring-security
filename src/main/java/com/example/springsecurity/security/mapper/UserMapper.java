package com.example.springsecurity.security.mapper;

import com.example.springsecurity.security.dto.SignupDto;
import com.example.springsecurity.security.dto.UserDto;
import com.example.springsecurity.security.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	User signupUserToEntity(SignupDto.SignupUserDto signupUserDto);
	UserDto.updateResponse toUpdateResponse(User updateUser);
	User insertRequestToEntity(UserDto.insertRequest inUser);


}
