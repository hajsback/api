package com.pawmot.hajsback.api.controllers;

import com.pawmot.hajsback.api.dto.AuthTokensDto;
import com.pawmot.hajsback.api.dto.RegisterUserDto;
import com.pawmot.hajsback.api.exceptions.HttpStatusException;
import com.pawmot.hajsback.api.exceptions.ValidationException;
import com.pawmot.hajsback.api.model.users.User;
import com.pawmot.hajsback.api.model.users.UserFactory;
import com.pawmot.hajsback.api.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(path = "/v1/users")
public class UsersController {
    private final UserFactory userFactory;
    private final UserRepository userRepository;

    @Autowired
    public UsersController(UserFactory userFactory, UserRepository userRepository) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = POST)
    public void registerUser(@RequestBody @Validated RegisterUserDto dto, Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException();
        }

        User user = userFactory.create(dto.getEmail());

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());

        userRepository.save(user);

        throw new HttpStatusException(HttpStatus.CREATED);
    }
}
