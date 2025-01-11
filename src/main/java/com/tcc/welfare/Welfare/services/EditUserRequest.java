package com.tcc.welfare.Welfare.services;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EditUserRequest {

    private Long id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final Long phoneNumber;
    private String username;
    private String password;

}
