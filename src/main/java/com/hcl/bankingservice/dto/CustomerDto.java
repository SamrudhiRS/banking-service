package com.hcl.bankingservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Set;

public class CustomerDto {
    private long customerId;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String firstName;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String lastName;

    @NotEmpty
    @Email(message = "Invalid email format")
    private String email;

    @NotNull
    @Past(message = "Date of birth must be in past")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digit")
    private String phoneNumber;

    @Valid
    private Set<AccountDto> accountDtos;

    public CustomerDto() {
    }

    public CustomerDto(long customerId, String firstName, String lastName, String email, LocalDate dateOfBirth, String phoneNumber) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<AccountDto> getAccountDtos() {
        return accountDtos;
    }

    public void setAccountDtos(Set<AccountDto> accountDtos) {
        this.accountDtos = accountDtos;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accountDtos=" + accountDtos +
                '}';
    }
}
