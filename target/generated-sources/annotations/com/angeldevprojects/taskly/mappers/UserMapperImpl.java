package com.angeldevprojects.taskly.mappers;

import com.angeldevprojects.taskly.dtos.SignUpDto;
import com.angeldevprojects.taskly.dtos.UserDto;
import com.angeldevprojects.taskly.models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-12T20:38:15+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.username( user.getUsername() );
        userDto.email( user.getEmail() );
        userDto.name( user.getName() );
        userDto.surname( user.getSurname() );

        return userDto.build();
    }

    @Override
    public User signUpToUser(SignUpDto signUpDto) {
        if ( signUpDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( signUpDto.username() );
        user.email( signUpDto.email() );
        user.name( signUpDto.name() );
        user.surname( signUpDto.surname() );

        return user.build();
    }

    @Override
    public User toUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDto.getId() );
        user.username( userDto.getUsername() );
        user.email( userDto.getEmail() );
        user.name( userDto.getName() );
        user.surname( userDto.getSurname() );

        return user.build();
    }
}
