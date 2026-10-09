package com.hcl.bankingservice.mapper;

import com.hcl.bankingservice.dto.UserDto;
import com.hcl.bankingservice.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setActive(user.getActive());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public static Set<UserDto> toDto(Set<User> userSet) {
        return userSet
                .stream()
                .map(user -> toDto(user))
                .collect(Collectors.toSet());
    }

    public static List<UserDto> toDto(List<User> userSet) {
        return userSet
                .stream()
                .map(user -> toDto(user))
                .collect(Collectors.toList());
    }

    public static Set<User> toEntity(Set<UserDto> userDtos) {
        return userDtos.stream()
                .map(userDto -> toEntity(userDto))
                .collect(Collectors.toSet());
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setActive(userDto.getActive());
        user.setRole(userDto.getRole());
        return user;
    }
}



