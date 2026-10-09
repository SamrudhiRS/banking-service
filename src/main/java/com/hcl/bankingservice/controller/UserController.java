package com.hcl.bankingservice.controller;

import com.hcl.bankingservice.dto.UserDto;
import com.hcl.bankingservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userService/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        LOGGER.info("Retrieved all users");
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id){

        LOGGER.info("getOneById - {}", id);
        final UserDto result = userService.getOneById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        LOGGER.info("create user - {}", userDto.getUsername());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        final UserDto result = userService.create(userDto);
        if (result == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        LOGGER.info("User created: {}",userDto.getUsername());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        LOGGER.info("update user - {}", userDto.getUsername());
        if (userDto.getPassword() != null) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        final UserDto result = userService.update(userDto);
        if (result == null) {
            return ResponseEntity.unprocessableEntity()
                    .build();
        }
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        LOGGER.info("delete - {}", id);
        final boolean isDeleted = userService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.badRequest()
                    .build();
        }
        return ResponseEntity.noContent()
                .build();
    }

}
