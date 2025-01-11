package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.exceptions.UserNotFoundException;
import com.tcc.welfare.Welfare.model.User;
import com.tcc.welfare.Welfare.repository.DietRepository;
import com.tcc.welfare.Welfare.repository.MeasuresRepository;
import com.tcc.welfare.Welfare.repository.TrainingRepository;
import com.tcc.welfare.Welfare.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "Usuário com nome de usuário %s não encontrado!";
    UserRepository userRepository;
    DietRepository dietRepository;
    MeasuresRepository measuresRepository;
    TrainingRepository trainingRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    public String login(User user){
        boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();

        String passwordDb = getPassword(user.getUsername());
        String password = user.getPassword();
        if (!userExists){
            throw new IllegalStateException("Email doesn't exist");
        }

        if(password.contains(passwordDb)){
            String pass = "Passou";
        } else {
            throw new IllegalStateException("Wrong password");
        }

        return "Success";
    }

    public String getPassword(String username) {
        return userRepository.getPassword(username);
    }

    public String getUserId(String username) {
        return userRepository.getUserIdByUsername(username);
    }

    public List<User> getUsersByHealthProfessionalId(Long healthProfessionalId) {
        return userRepository.getUsersByHealthProfessionalId(healthProfessionalId);
    }

    public String postUser(User user) {
        boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();

        if (userExists){
            throw new IllegalStateException("Username already taken");
        }
        return "Success";
    }

    public List<User> getUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User putUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        dietRepository.deleteDietByUserId(id);
        trainingRepository.deleteTrainingByUserId(id);
        measuresRepository.deleteMeasuresByUserId(id);
        userRepository.deleteById(id);
    }
}
