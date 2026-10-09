package com.hcl.bankingservice.repository;

import com.hcl.bankingservice.model.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
}
