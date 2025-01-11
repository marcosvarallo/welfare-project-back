package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.enums.HealthProfessionalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final int age;
    @Enumerated(EnumType.STRING)
    private HealthProfessionalType type;
}
