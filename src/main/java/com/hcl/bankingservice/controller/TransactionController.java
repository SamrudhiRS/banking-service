package com.hcl.bankingservice.controller;

import com.hcl.bankingservice.dto.AccountDto;
import com.hcl.bankingservice.dto.TransactionDto;
import com.hcl.bankingservice.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactionService/v1/transactions")
@SecurityRequirement(name = "Authorization")
public class TransactionController {

    private static final String CONTROLLER = "TransactionController";

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class.getName());

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAll() {
        LOGGER.info("{} - getAll", CONTROLLER);
        final List<TransactionDto> result = transactionService.getAll();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getOneById(@PathVariable long transactionId) {
        LOGGER.info("{} - getAll", CONTROLLER);
        final TransactionDto result = transactionService.getOneById(transactionId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<TransactionDto> create(@Valid @RequestBody TransactionDto transactionDto) {
        LOGGER.info("{} - create", CONTROLLER);

        final TransactionDto result = transactionService.create(transactionDto);

        if (result == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> delete(@PathVariable long transactionId) {
        LOGGER.info("{} - create", CONTROLLER);
        final boolean isDeleted = transactionService.delete(transactionId);
        if (!isDeleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

}
