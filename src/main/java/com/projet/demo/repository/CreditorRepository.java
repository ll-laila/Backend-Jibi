package com.projet.demo.repository;

import com.projet.demo.entity.Creditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditorRepository extends JpaRepository<Creditor,Long> {

    Creditor getByName(String name);
}
