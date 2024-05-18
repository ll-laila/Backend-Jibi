package com.projet.demo.repository;

import com.projet.demo.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepo extends JpaRepository<Agent,Long> {
}
