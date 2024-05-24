package com.projet.demo.repository;

import com.projet.demo.entity.Admin;
import com.projet.demo.entity.AgentService;
import com.projet.demo.entity.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentAccountRepo extends JpaRepository<PaymentAccount, Long> {

    @Query("SELECT a FROM PaymentAccount a WHERE a.client.id = :clientId")
    PaymentAccount findPaymentAccountByClientId(@Param("clientId") Long clientId);
}
