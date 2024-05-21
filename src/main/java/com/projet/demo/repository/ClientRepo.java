package com.projet.demo.repository;


import com.projet.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {

    @Query(value = "SELECT c FROM Client c WHERE c.phoneNumber LIKE :phoneNumber ")
    Client findByPhoneNumber(@Param("phoneNumber") String phoneNumber);


}
