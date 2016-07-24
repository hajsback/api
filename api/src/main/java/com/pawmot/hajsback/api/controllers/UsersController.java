package com.pawmot.hajsback.api.controllers;

import com.pawmot.hajsback.api.dto.RegisterUserDto;
import com.pawmot.hajsback.api.exceptions.ValidationException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(path = "/v1/users")
public class UsersController {
    @RequestMapping(method = POST)
    public boolean registerUser(@Validated RegisterUserDto dto, Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException();
        }

        // TODO: create the user

        return true;
    }
}
