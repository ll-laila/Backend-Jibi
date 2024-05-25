package com.projet.demo.repository;

import com.projet.demo.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepo extends JpaRepository<CustomerOrder,Long> {
}
