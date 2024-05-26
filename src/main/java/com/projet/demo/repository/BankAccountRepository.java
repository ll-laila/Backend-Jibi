package com.projet.demo.repository;

import com.projet.demo.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {



    BankAccount findByClientCni(String cni);
}
