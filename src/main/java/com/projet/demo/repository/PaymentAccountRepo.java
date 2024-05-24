package com.projet.demo.repository;

import com.projet.demo.entity.Admin;
import com.projet.demo.entity.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentAccountRepo extends JpaRepository<PaymentAccount, Long> {
    PaymentAccount getByClientId(long id);
}
