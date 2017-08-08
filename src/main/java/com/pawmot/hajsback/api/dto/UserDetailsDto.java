package com.pawmot.hajsback.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDetailsDto {
    private String email;

    private String firstName;

    private String lastName;
}
