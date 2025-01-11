package com.tcc.welfare.Welfare.repository;

import com.tcc.welfare.Welfare.model.Diet;
import com.tcc.welfare.Welfare.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DietRepository extends JpaRepository<Diet, Long> {

    List<Diet> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE from Diet WHERE user_id = ?1")
    void deleteDietByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Diet SET user_id = ?1 WHERE user_id is null")
    int assignUserToDiet(Long userId);
}
