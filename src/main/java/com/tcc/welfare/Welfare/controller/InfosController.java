package com.tcc.welfare.Welfare.controller;

import com.tcc.welfare.Welfare.exceptions.UserNotFoundException;
import com.tcc.welfare.Welfare.model.Diet;
import com.tcc.welfare.Welfare.model.Measures;
import com.tcc.welfare.Welfare.model.Training;
import com.tcc.welfare.Welfare.model.User;
import com.tcc.welfare.Welfare.repository.DietRepository;
import com.tcc.welfare.Welfare.repository.MeasuresRepository;
import com.tcc.welfare.Welfare.repository.TrainingRepository;
import com.tcc.welfare.Welfare.repository.UserRepository;
import com.tcc.welfare.Welfare.services.DietService;
import com.tcc.welfare.Welfare.services.MeasuresService;
import com.tcc.welfare.Welfare.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin//(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "api/v1/newInfos")
@AllArgsConstructor
public class InfosController {

    @Autowired
    DietService dietService;
    @Autowired
    TrainingService trainingService;
    @Autowired
    MeasuresService measuresService;
    @Autowired
    DietRepository dietRepository;
    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    MeasuresRepository measuresRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/getDiet")
    public List<Diet> getDiet(){
        return dietService.getDiet();
    }

    @GetMapping("/getDietById/{dietId}")
    public ResponseEntity<Diet> getDietByid(@PathVariable Long dietId) throws Exception{
        return ResponseEntity.ok(dietService.getDietById(dietId).orElseThrow(() ->
                new UserNotFoundException("Diet with ID: " + dietId + " wasn't found!")));
    }

    @GetMapping("/getDietByUserId/{userId}")
    public List<Diet> getDietByUserId(@PathVariable Long userId) {
        return dietService.getDietByUserId(userId);
    }

    @PostMapping("/newDiet/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    Object newDiet(@RequestBody Diet diet, @PathVariable Long userId){
        try {
            //dietService.postDiet(diet);
            User user = userRepository.findById(userId).get();
            diet.newDiet(user);
            return dietRepository.save(diet);
        } catch (Exception ex){
            return("User with ID: " + userId + " wasn't found!");
        }
    }

    @GetMapping("/getTraining")
    public List<Training> getTraining(){
        return trainingService.getTraining();
    }

    @GetMapping("/getTrainingById/{trainingId}")
    public ResponseEntity<Training> getTrainingByid(@PathVariable Long trainingId) throws Exception{
        return ResponseEntity.ok(trainingService.getTrainingById(trainingId).orElseThrow(() ->
                new UserNotFoundException("Training with ID: " + trainingId + " wasn't found!")));
    }

    @GetMapping("/getTrainingByUserId/{userId}")
    public List<Training> getTrainingByUserId(@PathVariable Long userId) {
        return trainingService.getTrainingByUserId(userId);
    }

    @PostMapping("/newTraining/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    Object newTraining(@RequestBody Training training, @PathVariable Long userId){
        try {
            //trainingService.postTraining(training);
            User user = userRepository.findById(userId).get();
            training.newTraining(user);
            return trainingRepository.save(training);
        } catch (Exception ex){
            return("User with ID: " + userId + " wasn't found!");
        }
    }

    @GetMapping("/getMeasures")
    public List<Measures> getMeasures(){
        return measuresService.getMeasures();
    }

    @GetMapping("/getMeasuresById/{measureId}")
    public ResponseEntity<Measures> getMeasuresByid(@PathVariable Long measureId) throws Exception{
        return ResponseEntity.ok(measuresService.getMeasuresById(measureId).orElseThrow(() ->
                new UserNotFoundException("Measure with ID: " + measureId + " wasn't found!")));
    }

    @GetMapping("/getMeasuresByUserId/{userId}")
    public List<Measures> getMeasuresByUserId(@PathVariable Long userId) {
        return measuresService.getMeasuresByUserId(userId);
    }

    @PostMapping("/newMeasures/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    Object newMeasures(@RequestBody Measures measures, @PathVariable Long userId){
        try {
            //measuresService.postMeasure(measures);
            User user = userRepository.findById(userId).get();
            measures.newMeasure(user);
            return measuresRepository.save(measures);
        } catch (Exception ex){
            return("Measure with ID: " + userId + " wasn't found!");
        }
    }

}
