package com.projet.demo.repository;


import com.projet.demo.entity.AgentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentServiceRepo extends JpaRepository<AgentService,Long> {

    @Query("SELECT a FROM AgentService a WHERE a.agent.id = :agentId")
    List<AgentService> findAllByAgentId(@Param("agentId") Long agentId);
}
