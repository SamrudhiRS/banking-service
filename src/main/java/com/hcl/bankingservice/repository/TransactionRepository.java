package com.hcl.bankingservice.repository;

import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
