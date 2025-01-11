package com.tcc.welfare.Welfare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ObjetiveType {

    HIPERTROFIA("Hipertrofia"),
    EMAGRECIMENTO("Emagrecimento");

    private final String description;

}
