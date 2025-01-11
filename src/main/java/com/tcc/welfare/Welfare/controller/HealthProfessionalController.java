package com.tcc.welfare.Welfare.controller;

import com.tcc.welfare.Welfare.exceptions.UserNotFoundException;
import com.tcc.welfare.Welfare.model.HealthProfessional;
import com.tcc.welfare.Welfare.model.User;
import com.tcc.welfare.Welfare.services.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin//(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "api/v1/healthPro")
@AllArgsConstructor
public class HealthProfessionalController {

    @Autowired
    HealthProfessionalService healthProfessionalService;
    HealthProRegistrationService healthProRegistrationService;
    HealthProRecoverPasswordService healthProRecoverPasswordService;
    HealthProNewPasswordService healthProNewPasswordService;
    LoginService loginService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createHealthPro(@RequestBody RegistrationRequest healthProfessionalRequest) {
        return healthProRegistrationService.register(healthProfessionalRequest);
    }

    @GetMapping("/recoverPassword")
    public String recoverPasswordHealthPro(@RequestParam("email") String email) {
        return healthProRecoverPasswordService.recoverPassword(email);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){
        return healthProRegistrationService.confirmToken(token);
    }

    @GetMapping
    public List<HealthProfessional> getHealthProfessional(){
        return healthProfessionalService.getHealthProfessional();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthProfessional> getHealthProfessionalById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(healthProfessionalService.getHealthProfessionalById(id).orElseThrow(() ->
                new UserNotFoundException("HealthProfessional with ID: " + id + " wasn't found!")));
    }

    @PutMapping("/{id}")
    public Object putHealthProfessional(@PathVariable Long id, @RequestBody HealthProfessional healthProfessional) throws UserNotFoundException {
        try {
            return healthProfessionalService.putHealthProfessional(healthProfessional);
        } catch (Exception ex){
            return("HealthProfessional with ID: " + id + " wasn't found!");
        }
    }

    @PostMapping("/newPassword")
    public String newPasswordHealthProfessional(@RequestBody NewPasswordRequest newPasswordRequest){
        return healthProNewPasswordService.newPassword(newPasswordRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteHealthProfessional(@PathVariable Long id) throws UserNotFoundException{
        try{
            healthProfessionalService.deleteHealthProfessional(id);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
