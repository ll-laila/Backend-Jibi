package com.projet.demo.repository;

import com.projet.demo.model.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentAccountRepo extends JpaRepository<PaymentAccount,Long> {
}
