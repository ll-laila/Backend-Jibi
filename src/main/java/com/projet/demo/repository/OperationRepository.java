package com.projet.demo.repository;

import com.projet.demo.entity.AgentService;
import com.projet.demo.entity.BankAccount;
import com.projet.demo.entity.Client;
import com.projet.demo.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OperationRepository extends JpaRepository<Operation,Long> {

    @Query("SELECT a FROM Operation a WHERE a.client.phoneNumber = :phoneNumber")
    List<Operation> findOperationsByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    @Query("SELECT a FROM Operation a WHERE a.idCreditor = :id_creditor")
    List<Operation> findOperationsByAgentId(@Param("id_creditor") Long id_creditor);

}