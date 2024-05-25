package com.projet.demo.repository;

import com.projet.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);
//    @Query(value = "SELECT * FROM user WHERE phone_number = ?1 AND role = 'CLIENT'", nativeQuery = true)
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Object> findByEmail(String email);

    @Query(value = "SELECT * FROM client WHERE role = 'AGENT'", nativeQuery = true)
    List<User> findAllAgentWithRoleClient();


    @Query(value = "SELECT * FROM client WHERE id = ?1 AND role = 'AGENT'", nativeQuery = true)
    User findAgentByClientId(Long id);
    @Query(value = "SELECT * FROM client WHERE id = ?1 AND role = 'CLIENT'", nativeQuery = true)
    User findClientByClientId(Long id);
    @Query(value = "SELECT * FROM client WHERE role = 'CLIENT'", nativeQuery = true)
    List<User> findAllClientsWithRoleClient();

   

    Optional<User> findById(Long userId);

    @Query(value = "SELECT * FROM client WHERE phone_number = ?1 AND role = 'CLIENT'", nativeQuery = true)
    User findByPhoneNumber(String phoneNumber);


}


