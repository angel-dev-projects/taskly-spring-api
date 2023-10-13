package com.angeldevprojects.taskly.services;

import com.angeldevprojects.taskly.dtos.CredentialsDto;
import com.angeldevprojects.taskly.dtos.SignUpDto;
import com.angeldevprojects.taskly.dtos.UserDto;
import com.angeldevprojects.taskly.exceptions.AppException;
import com.angeldevprojects.taskly.mappers.UserMapper;
import com.angeldevprojects.taskly.models.User;
import com.angeldevprojects.taskly.repositories.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),
                user.getPassword())) {
            return userMapper.toUserDto(user);
        }

        throw new AppException("Invalid Password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto) {
        if (StringUtils.isBlank(signUpDto.username()) || StringUtils.isBlank(Arrays.toString(signUpDto.password())) || StringUtils.isBlank(signUpDto.email()) || StringUtils.isBlank(signUpDto.name()) || StringUtils.isBlank(signUpDto.surname())) {
            throw new AppException("Username, password, email, name and surname are required", HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUserByUsername = userRepository.findByUsername(signUpDto.username());
        if (optionalUserByUsername.isPresent()) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUserByEmail = userRepository.findByEmail(signUpDto.email());
        if (optionalUserByEmail.isPresent()) {
            throw new AppException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }
}
