package com.tcc.welfare.Welfare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HealthProfessionalType {

    NUTRICIONISTA("Nutricionista"),
    PERSONAL("Personal Trainer");

    private final String description;
}
