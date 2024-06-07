package com.projet.demo.repository;

import com.projet.demo.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {

    BankAccount findByClientCni(String cni);

    @Query("SELECT a FROM BankAccount a WHERE a.client.id = :idClient")
    BankAccount findByIdClient(@Param("idClient") Long idClient);
}
