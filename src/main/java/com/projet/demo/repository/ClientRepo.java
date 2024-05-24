package com.projet.demo.repository;

import com.projet.demo.entity.Client;
import com.projet.demo.entity.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {

    Client findClientById(Long id);

    Client findClientByPhoneNumber(String phoneNumber);



    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

}
