package com.pawmot.hajsback.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class AuthTokensDto {
    @Getter
    @Setter
    private UUID accessToken;
}
