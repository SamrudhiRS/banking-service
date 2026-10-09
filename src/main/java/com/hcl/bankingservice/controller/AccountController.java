package com.hcl.bankingservice.controller;

import com.hcl.bankingservice.dto.AccountDto;
import com.hcl.bankingservice.dto.CustomerDto;
import com.hcl.bankingservice.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accountService/v1/accounts")
@SecurityRequirement(name = "Authorization")
public class AccountController {

    private static final String CONTROLLER = "AccountController";

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class.getName());

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAll() {
        LOGGER.info("{} - getAll", CONTROLLER);
        final List<AccountDto> result = accountService.getAll();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getOneById(@PathVariable long accountId) {
        LOGGER.info("{} - GetOneById", CONTROLLER);
        final AccountDto result = accountService.getOneById(accountId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<AccountDto> create(@Valid @RequestBody AccountDto accountDto) {
        LOGGER.info("{} - create", CONTROLLER);
        final AccountDto result = accountService.create(accountDto);
        if (result == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AccountDto> update(@RequestBody AccountDto accountDto) {
        LOGGER.info("{} - update", CONTROLLER);

        final AccountDto result = accountService.update(accountDto);
        if (result == null) {
            return ResponseEntity.unprocessableEntity()
                    .build();
        }
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> delete(@PathVariable long accountId) {
        LOGGER.info("{} - delete", CONTROLLER);
        final boolean isDeleted = accountService.delete(accountId);
        if (!isDeleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
