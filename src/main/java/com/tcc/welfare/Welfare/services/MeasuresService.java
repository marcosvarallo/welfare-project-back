package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.model.Measures;
import com.tcc.welfare.Welfare.repository.MeasuresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasuresService {

    MeasuresRepository measuresRepository;

    @Autowired
    public MeasuresService(MeasuresRepository measuresRepository){
        this.measuresRepository = measuresRepository;
    }

    public Measures postMeasure (Measures measures){
        return measuresRepository.save(measures);
    }

    public List<Measures> getMeasures(){
        return measuresRepository.findAll();
    }

    public Optional<Measures> getMeasuresById(Long id){
        return measuresRepository.findById(id);
    }

    public List<Measures> getMeasuresByUserId(Long userId){
        return measuresRepository.findByUserId(userId);
    }

    public int assignUserToMeasures(Long userId) {
        return measuresRepository.assignUserToMeasures(userId);
    }

}
