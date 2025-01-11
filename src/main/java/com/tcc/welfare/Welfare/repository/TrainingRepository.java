package com.tcc.welfare.Welfare.repository;

import com.tcc.welfare.Welfare.model.Measures;
import com.tcc.welfare.Welfare.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE from Training WHERE user_id = ?1")
    void deleteTrainingByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Training SET user_id = ?1 WHERE user_id is null")
    int assignUserToTraining(Long userId);
}
