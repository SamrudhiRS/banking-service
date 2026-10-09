package com.hcl.bankingservice.controller;

import com.hcl.bankingservice.dto.AccountDto;
import com.hcl.bankingservice.dto.CreditCardDto;
import com.hcl.bankingservice.dto.CustomerDto;
import com.hcl.bankingservice.service.CreditCardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditCardService/v1/creditCards")
@SecurityRequirement(name = "Authorization")
public class CreditCardController {

    private static final String CONTROLLER = "CreditCardController";

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class.getName());

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping
    public ResponseEntity<List<CreditCardDto>> getAll() {
        LOGGER.info("{} - getAll", CONTROLLER);
        final List<CreditCardDto> result = creditCardService.getAll();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{creditCardId}")
    public ResponseEntity<CreditCardDto> getOneById(@PathVariable long creditCardId) {
        LOGGER.info("{} - GetOneById", creditCardId);
        final CreditCardDto result = creditCardService.getOneById(creditCardId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<CreditCardDto> create(@Valid @RequestBody CreditCardDto creditCardDto) {
        LOGGER.info("{} - create", creditCardDto);
        final CreditCardDto result = creditCardService.create(creditCardDto);
        if (result == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CreditCardDto> update(@RequestBody CreditCardDto creditCardDto) {
        LOGGER.info("{} - update", creditCardDto);

        final CreditCardDto result = creditCardService.update(creditCardDto);
        if (result == null) {
            return ResponseEntity.unprocessableEntity()
                    .build();
        }
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{creditCardId}")
    public ResponseEntity<Void> delete(@PathVariable long creditCardId) {
        LOGGER.info("{} - deleteById", creditCardId);
        final boolean isDeleted = creditCardService.delete(creditCardId);
        if (!isDeleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
