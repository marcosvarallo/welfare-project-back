package com.tcc.welfare.Welfare.controller;

import com.tcc.welfare.Welfare.exceptions.UserNotFoundException;
import com.tcc.welfare.Welfare.model.Diet;
import com.tcc.welfare.Welfare.model.HealthProfessional;
import com.tcc.welfare.Welfare.model.User;
import com.tcc.welfare.Welfare.repository.DietRepository;
import com.tcc.welfare.Welfare.repository.HealthProfessionalRepository;
import com.tcc.welfare.Welfare.repository.UserRepository;
import com.tcc.welfare.Welfare.services.*;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@CrossOrigin//(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "api/v1/user")
@AllArgsConstructor
public class UserController {

    @Autowired
    UserService userService;
    LoginService loginService;
    EditUserService editUserService;

    @Autowired
    HealthProfessionalRepository healthProfessionalRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DietService dietService;
    TrainingService trainingService;
    MeasuresService measuresService;

    @Autowired
    DietRepository dietRepository;

    @PostMapping("createUser/{healthProfessionalId}")
    @ResponseStatus(HttpStatus.CREATED)
    Object createUser(@RequestBody User user, @PathVariable Long healthProfessionalId){
        try {
            try {
                userService.postUser(user);
            } catch (Exception ex){
                return("Username already taken");
            }
            HealthProfessional healthProfessional = healthProfessionalRepository.findById(healthProfessionalId).get();
            user.newUser(healthProfessional);
            Long userId = userRepository.save(user).getId();
            dietService.assignUserToDiet(userId);
            trainingService.assignUserToTraining(userId);
            measuresService.assignUserToMeasures(userId);
            return userId;
        } catch (Exception ex){
            return("Health Professional with ID: " + healthProfessionalId + " wasn't found!");
        }
    }

    @PostMapping("editUser/{id}")
    public ResponseEntity<User> editUser(@RequestBody EditUserRequest editUserRequest) throws UserNotFoundException{
        try {
            editUserService.editUser(editUserRequest);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<User> getUser(){
        return userService.getUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(userService.getUserById(id).orElseThrow(() ->
                new UserNotFoundException("User with ID: " + id + " wasn't found!")));
    }

    @GetMapping("/healthProId/{healthProId}")
    public List<User> getUserByHealthProfessionalId(@PathVariable Long healthProId) {
        return userService.getUsersByHealthProfessionalId(healthProId);
    }

    @PutMapping("/{id}")
    public Object putUser(@PathVariable Long id, @RequestBody User user) throws UserNotFoundException {
        try {
            return userService.putUser(user);
        } catch (Exception ex){
            return("User with ID: " + id + " wasn't found!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) throws UserNotFoundException{
        try{
            userService.deleteUser(id);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
