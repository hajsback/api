package com.pawmot.hajsback.api.controllers;

import com.pawmot.hajsback.api.dto.RegisterUserDto;
import com.pawmot.hajsback.api.exceptions.ValidationException;
import com.pawmot.hajsback.api.model.users.User;
import com.pawmot.hajsback.api.model.users.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(path = "/v1/users")
public class UsersController {
    private UserFactory userFactory;

    @Autowired
    public UsersController(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @RequestMapping(method = POST)
    public boolean registerUser(@Validated RegisterUserDto dto, Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException();
        }

        User user = userFactory.create(dto.getEmail());

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());

        // TODO: save the user

        return true;
    }
}
