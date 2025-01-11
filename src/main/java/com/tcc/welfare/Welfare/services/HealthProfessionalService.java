package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.model.HealthProfessional;
import com.tcc.welfare.Welfare.repository.ConfirmationTokenRepository;
import com.tcc.welfare.Welfare.repository.HealthProfessionalRepository;
import com.tcc.welfare.Welfare.services.token.ConfirmationToken;
import com.tcc.welfare.Welfare.services.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HealthProfessionalService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "Usuário com email %s não encontrado!";
    private final HealthProfessionalRepository healthProfessionalRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return healthProfessionalRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String login(HealthProfessional healthProfessional){
        boolean healthProExists = healthProfessionalRepository.findByEmail(healthProfessional.getEmail()).isPresent();
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(healthProfessional.getPassword(), getPassword(healthProfessional.getEmail()));
        boolean isHealthProEnabled = healthProfessionalRepository.getEnabled(healthProfessional.getEmail());
        String response;
        if (!healthProExists){
            response = "Email doesn't exist";
            throw new IllegalStateException(response);
        } else if(!isPasswordMatch){
            response = "Wrong password";
            throw new IllegalStateException(response);
        } else if (!isHealthProEnabled){
            response = "Health Professional isn't enabled";
            throw new IllegalStateException(response);
        } else {
            response = "Success to Login!";
        }

        return response;
    }

    public String getPassword(String email) {
        return healthProfessionalRepository.getPassword(email);
    }

    public Boolean getEnabled(String email) {
        return healthProfessionalRepository.getEnabled(email);
    }

    public Long getHealthProfessionalIdByEmail(String email){
        return healthProfessionalRepository.getHealthProfessionalIdByEmail(email);
    }

    public String signUpHealthPro(HealthProfessional healthProfessional){
        boolean healthProExists = healthProfessionalRepository.findByEmail(healthProfessional.getEmail()).isPresent();

        if (healthProExists){
            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(healthProfessional.getPassword());

        healthProfessional.setPassword(encodedPassword);

        healthProfessionalRepository.save(healthProfessional);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                healthProfessional
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableHealthProfessional(String email) {
        return healthProfessionalRepository.enableHealthProfessional(email);
    }

    public List<HealthProfessional> getHealthProfessional(){
        return healthProfessionalRepository.findAll();
    }

    public Optional<HealthProfessional> getHealthProfessionalById(Long id){
        return healthProfessionalRepository.findById(id);
    }

    public HealthProfessional putHealthProfessional(HealthProfessional healthProfessional) {
        return healthProfessionalRepository.save(healthProfessional);
    }

    public void deleteHealthProfessional(Long id) {
        confirmationTokenRepository.deleteConfirmationTokenByHealthProId(id);
        healthProfessionalRepository.deleteById(id);
    }

    //@Autowired
    //public HealthProfessionalService(HealthProfissionalRepository healthProfissionalRepository) {
    //    this.healthProfissionalRepository = healthProfissionalRepository;
    //}

    //public HealthProfessional postHealthPro(HealthProfessional healthProfessional){
    //    return healthProfissionalRepository.save(healthProfessional);
    //}
}
