package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.model.Diet;
import com.tcc.welfare.Welfare.model.Training;
import com.tcc.welfare.Welfare.repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DietService {

    DietRepository dietRepository;

    @Autowired
    public DietService(DietRepository dietRepository){
        this.dietRepository = dietRepository;
    }

    public Diet postDiet (Diet diet){
        return dietRepository.save(diet);
    }

    public List<Diet> getDiet(){
        return dietRepository.findAll();
    }

    public Optional<Diet> getDietById(Long id){
        return dietRepository.findById(id);
    }

    public List<Diet> getDietByUserId(Long userId){
        return dietRepository.findByUserId(userId);
    }

    public int assignUserToDiet(Long userId) {
        return dietRepository.assignUserToDiet(userId);
    }

}
