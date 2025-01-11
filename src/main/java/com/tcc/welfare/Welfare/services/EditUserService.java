package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EditUserService {

    private final UserRepository userRepository;

    public String editUser(EditUserRequest editUserRequest){

        userRepository.editUser(editUserRequest.getId(), editUserRequest.getFirstName(), editUserRequest.getLastName(), editUserRequest.getAge(), editUserRequest.getPhoneNumber(), editUserRequest.getUsername(), editUserRequest.getPassword());

        return "Success!";
    }
}
