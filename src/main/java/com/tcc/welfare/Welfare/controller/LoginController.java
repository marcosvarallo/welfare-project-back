package com.tcc.welfare.Welfare.controller;

import com.tcc.welfare.Welfare.repository.UserRepository;
import com.tcc.welfare.Welfare.services.LoginRequest;
import com.tcc.welfare.Welfare.services.LoginService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin//(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    UserRepository userRepository;


    @PostMapping
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }

}
