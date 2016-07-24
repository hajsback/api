package com.pawmot.hajsback.api.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class RegisterUserDto {
    @Email
    @Getter
    private String email;

    @NotBlank
    @Length(min=6)
    @Getter
    private String password; // TODO: change to char[] and clear after registration

    @NotBlank
    @Length(min=1)
    @Getter
    private String firstName;

    @NotBlank
    @Length(min=1)
    @Getter
    private String lastName;
}
