package com.angeldevprojects.taskly.mappers;

import com.angeldevprojects.taskly.dtos.SignUpDto;
import com.angeldevprojects.taskly.dtos.UserDto;
import com.angeldevprojects.taskly.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}