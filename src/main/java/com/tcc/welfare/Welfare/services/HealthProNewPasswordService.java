package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.repository.HealthProfessionalRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class HealthProNewPasswordService {

    private final HealthProfessionalRepository healthProfessionalRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String newPassword(NewPasswordRequest newPasswordRequest){

        String encodedPassword = bCryptPasswordEncoder.encode(newPasswordRequest.getPassword());
        healthProfessionalRepository.updatePasswordById(newPasswordRequest.getId(), encodedPassword);

        return "Success!";
    }

}
