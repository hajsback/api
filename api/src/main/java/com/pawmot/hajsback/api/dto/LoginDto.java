package com.pawmot.hajsback.api.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

public class LoginDto {
    @NotEmpty
    @Getter
    private String email;

    @NotEmpty
    @Getter
    private String password;
}
