package com.hcl.bankingservice.controller;

import com.hcl.bankingservice.dto.CreditCardDto;
import com.hcl.bankingservice.dto.DebitCardDto;
import com.hcl.bankingservice.service.CreditCardService;
import com.hcl.bankingservice.service.DebitCardService;
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
@RequestMapping("/api/debitCardService/v1/debitCards")
@SecurityRequirement(name = "Authorization")
public class DebitCardController {

    private static final String CONTROLLER = "DebitCardController";

    private static final Logger LOGGER = LoggerFactory.getLogger(DebitCardController.class.getName());

    private final DebitCardService debitCardService;

    public DebitCardController(DebitCardService debitCardService) {
        this.debitCardService = debitCardService;
    }

    @GetMapping
    public ResponseEntity<List<DebitCardDto>> getAll() {
        LOGGER.info("{} - getAll", CONTROLLER);
        final List<DebitCardDto> result = debitCardService.getAll();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{debitCardId}")
    public ResponseEntity<DebitCardDto> getOneById(@PathVariable long debitCardId) {
        LOGGER.info("{} - GetOneById", debitCardId);
        final DebitCardDto result = debitCardService.getOneById(debitCardId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<DebitCardDto> create(@Valid @RequestBody DebitCardDto debitCardDto) {
        LOGGER.info("{} - create", debitCardDto);
        final DebitCardDto result = debitCardService.create(debitCardDto);
        if (result == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<DebitCardDto> update(@RequestBody DebitCardDto debitCardDto) {
        LOGGER.info("{} - update", debitCardDto);

        final DebitCardDto result = debitCardService.update(debitCardDto);
        if (result == null) {
            return ResponseEntity.unprocessableEntity()
                    .build();
        }
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{debitCardId}")
    public ResponseEntity<Void> delete(@PathVariable long debitCardId) {
        LOGGER.info("{} - deleteById", debitCardId);

        final boolean isDeleted = debitCardService.delete(debitCardId);

        if (!isDeleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

}
