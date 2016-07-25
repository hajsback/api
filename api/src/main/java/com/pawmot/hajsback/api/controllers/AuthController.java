package com.pawmot.hajsback.api.controllers;

import com.pawmot.hajsback.api.dto.AuthTokensDto;
import com.pawmot.hajsback.api.dto.LoginDto;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public AuthTokensDto login(@RequestBody @Validated LoginDto dto, Errors errors) {
        return null;
    }
}
