package com.hcl.bankingservice.controller;

import com.hcl.bankingservice.dto.CustomerDto;
import com.hcl.bankingservice.model.Customer;
import com.hcl.bankingservice.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customerService/v1/customers")
@SecurityRequirement(name = "Authorization")
public class CustomerController {
    private static final String CONTROLLER = "CustomerController";

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class.getName());

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll() {
        LOGGER.info("{} - getAll", CONTROLLER);
        final List<CustomerDto> result = customerService.getAll();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getOneById(@PathVariable long customerId) {
        LOGGER.info("{} - GetOneById", customerId);

        final CustomerDto result = customerService.getOneById(customerId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody CustomerDto customerDto) {
        LOGGER.info("{} - create", customerDto);

        final CustomerDto result = customerService.create(customerDto);
        if (result == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CustomerDto> update(@RequestBody CustomerDto customerDto) {
        LOGGER.info("{} - update", customerDto);

        final CustomerDto result = customerService.update(customerDto);
        if (result == null) {
            return ResponseEntity.unprocessableEntity()
                    .build();
        }
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable long customerId) {
        LOGGER.info("{} - deleteById", customerId);

        final boolean isDeleted = customerService.delete(customerId);
        if (!isDeleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
