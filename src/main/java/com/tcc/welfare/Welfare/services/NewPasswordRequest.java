package com.tcc.welfare.Welfare.services;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NewPasswordRequest {

    private final Long id;
    private final String password;
}
