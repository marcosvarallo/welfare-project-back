package com.tcc.welfare.Welfare.repository;

import com.tcc.welfare.Welfare.model.HealthProfessional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface HealthProfessionalRepository extends JpaRepository<HealthProfessional, Long> {

    Optional<HealthProfessional> findByEmail(String email);

    //Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE HealthProfessional a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableHealthProfessional(String email);

    @Transactional
    @Modifying
    @Query("UPDATE HealthProfessional a " +
            "SET a.password = ?2 WHERE a.email = ?1")
    int updatePassword(String email, String password);

    @Transactional
    @Modifying
    @Query("UPDATE HealthProfessional a " +
            "SET a.password = ?2 WHERE a.id = ?1")
    int updatePasswordById(Long id, String password);

    @Transactional
    @Query("SELECT password from HealthProfessional WHERE email = ?1")
    String getPassword(String email);

    @Transactional
    @Query("SELECT enabled from HealthProfessional WHERE email = ?1")
    Boolean getEnabled(String email);

    @Transactional
    @Query("SELECT id from HealthProfessional WHERE email = ?1")
    Long getHealthProfessionalIdByEmail(String email);
}
