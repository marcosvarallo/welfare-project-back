package com.tcc.welfare.Welfare.repository;

import com.tcc.welfare.Welfare.model.User;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.firstName = ?2, u.lastName = ?3, u.age = ?4, u.phoneNumber = ?5, u.username = ?6, u.password = ?7 WHERE u.id = ?1")
    int editUser(Long id, String firstName, String lastName, int age, Long phoneNumber, String username, String password);

    @Transactional
    @Query("SELECT password from User WHERE username = ?1")
    String getPassword(String username);

    @Transactional
    @Query("SELECT id from User WHERE username = ?1")
    String getUserIdByUsername(String username);

    @Query(value = "SELECT * from user WHERE health_professional_id = ?1", nativeQuery = true)
    List<User> getUsersByHealthProfessionalId(Long healthProfessionalId);
}
