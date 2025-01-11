package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.enums.AppUserRole;
import com.tcc.welfare.Welfare.model.HealthProfessional;
import com.tcc.welfare.Welfare.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class LoginService {

    private final HealthProfessionalService healthProfessionalService;
    private final UserService userService;
    private final EmailValidator emailValidator;

    public Map<String, Object> login(LoginRequest loginRequest){

        String role;
        String username = String.valueOf(loginRequest.getEmail());
        String userId;

        Map<String, Object> response = new HashMap();

        if (username.contains("@")){
            boolean isValidEmail = emailValidator.
                    validate(loginRequest.getEmail());

            if (!isValidEmail) {
                throw new IllegalStateException("email not valid");
            }

            healthProfessionalService.login(
                new HealthProfessional(
                        loginRequest.getEmail(),
                        loginRequest.getPassword(),
                        AppUserRole.ADMIN

                )
            );
            userId = healthProfessionalService.getHealthProfessionalIdByEmail(loginRequest.getEmail()).toString();
            role = "ADMIN";
            response.put("userRole", role);
            response.put("userId", userId);
        } else {
            userService.login(
                new User(
                        loginRequest.getEmail(),
                        loginRequest.getPassword(),
                        AppUserRole.USER
                )
            );
            userId = userService.getUserId(loginRequest.getEmail());
            role = "USER";
            response.put("userRole", role);
            response.put("userId", userId);
        }
        return response;
    }

}
