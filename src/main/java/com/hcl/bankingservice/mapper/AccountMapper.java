package com.hcl.bankingservice.mapper;

import com.hcl.bankingservice.dto.AccountDto;
import com.hcl.bankingservice.dto.CreditCardDto;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.model.DebitCard;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountDto toDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountId(account.getAccountId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setBalance(account.getBalance());
        accountDto.setDateOfOpening(account.getDateOfOpening());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setCustomerId(account.getCustomer().getCustomerId());
        if (account.getCreditCard() != null)
            accountDto.setCreditCardDto(CreditCardMapper.toDto(account.getCreditCard()));
        if (account.getDebitCard() != null)
            accountDto.setDebitCardDto(DebitCardMapper.toDto(account.getDebitCard()));
        return accountDto;
    }

    public static Set<AccountDto> toDto(Set<Account> accountSet) {
        return accountSet.stream()
                .map(account -> toDto(account))
                .collect(Collectors.toSet());
    }

    public static List<AccountDto> toDto(List<Account> accountSet) {
        return accountSet.stream()
                .map(account -> toDto(account))
                .collect(Collectors.toList());
    }

    public static Set<Account> toEntity(Set<AccountDto> accountDtos) {
        return accountDtos.stream()
                .map(accountDto -> toEntity(accountDto))
                .collect(Collectors.toSet());
    }

    // 1st Way
    public static long  generatingUniqueId() {
        UUID uniqueID = UUID.randomUUID();
        long unique_no = Math.abs(uniqueID.getMostSignificantBits());
        return unique_no;
    }

    public static Account toEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setAccountId(accountDto.getAccountId());

        // Here I am adding generated ID
        account.setAccountNumber(generatingUniqueId());
//        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance());
        // we are using LocalDate.now() to set Date
        account.setDateOfOpening(LocalDate.now());
        account.setAccountType(accountDto.getAccountType());

        if (accountDto.getCreditCardDto() != null) {
            CreditCard creditCard = CreditCardMapper.toEntity(accountDto.getCreditCardDto());

            account.setCreditCard(creditCard);
            creditCard.setAccount(account);
        }

        if (accountDto.getDebitCardDto() != null) {
            DebitCard debitCard = DebitCardMapper.toEntity(accountDto.getDebitCardDto());

            account.setDebitCard(debitCard);
            debitCard.setAccount(account);
        }


        return account;
    }
}
