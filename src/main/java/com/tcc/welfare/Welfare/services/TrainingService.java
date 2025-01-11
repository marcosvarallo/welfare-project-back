package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.model.Measures;
import com.tcc.welfare.Welfare.model.Training;
import com.tcc.welfare.Welfare.repository.MeasuresRepository;
import com.tcc.welfare.Welfare.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {

    TrainingRepository trainingRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository){
        this.trainingRepository = trainingRepository;
    }

    public Training postTraining (Training training){
        return trainingRepository.save(training);
    }

    public List<Training> getTraining(){
        return trainingRepository.findAll();
    }

    public Optional<Training> getTrainingById(Long id){
        return trainingRepository.findById(id);
    }

    public List<Training> getTrainingByUserId(Long userId){
        return trainingRepository.findByUserId(userId);
    }

    public int assignUserToTraining(Long userId) {
        return trainingRepository.assignUserToTraining(userId);
    }

}
