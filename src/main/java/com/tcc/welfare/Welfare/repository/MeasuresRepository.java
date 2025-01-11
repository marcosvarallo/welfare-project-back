package com.tcc.welfare.Welfare.repository;

import com.tcc.welfare.Welfare.model.HealthProfessional;
import com.tcc.welfare.Welfare.model.Measures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MeasuresRepository extends JpaRepository<Measures, Long> {

    List<Measures> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE from Measures WHERE user_id = ?1")
    void deleteMeasuresByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Measures SET user_id = ?1 WHERE user_id is null")
    int assignUserToMeasures(Long userId);
}
