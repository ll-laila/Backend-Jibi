package com.projet.demo.repository;

import com.projet.demo.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AgencyRepo extends JpaRepository<Agency,Long> {
}
