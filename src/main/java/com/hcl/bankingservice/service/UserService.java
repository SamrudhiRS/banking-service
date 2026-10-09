package com.hcl.bankingservice.service;

import com.hcl.bankingservice.dao.service.UserDaoService;
import com.hcl.bankingservice.dto.UserDto;
import com.hcl.bankingservice.exception.UserIdNotFoundException;
import com.hcl.bankingservice.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.hcl.bankingservice.mapper.UserMapper.toDto;
import static com.hcl.bankingservice.mapper.UserMapper.toEntity;

@Service
public class UserService {

    private final UserDaoService userDaoService;

    @Autowired
    public UserService(final UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    public List<UserDto> getAllUsers() {
        final List<User> users = userDaoService.getAll();
        return toDto(users);
    }

    public UserDto getOneById(long id) {
        final Optional<User> optionalUser = userDaoService.getOneById(id);
        if (optionalUser.isEmpty()) {
            throw new UserIdNotFoundException(id);
        }
        return toDto(optionalUser.get());
    }

    public UserDto create(UserDto userDto) {
        final User user = toEntity(userDto);
        final User savedUser = userDaoService.create(user);
        return toDto(savedUser);
    }

    public UserDto update(UserDto userDto) {
        final User user = toEntity(userDto);
        final User updatedUser = userDaoService.update(user);
        return toDto(updatedUser);
    }

    public boolean delete(long id) {

        return userDaoService.delete(id);
    }


}


