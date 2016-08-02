package com.pawmot.hajsback.api.controllers;

import com.pawmot.hajsback.api.dto.AuthTokensDto;
import com.pawmot.hajsback.api.dto.LoginDto;
import com.pawmot.hajsback.api.exceptions.HttpStatusException;
import com.pawmot.hajsback.api.exceptions.ValidationException;
import com.pawmot.hajsback.api.model.users.User;
import com.pawmot.hajsback.api.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/auth/")
public class AuthController {
    private final UserRepository userRepository;
    private final AutowireCapableBeanFactory beanFactory;

    @Autowired
    public AuthController(UserRepository userRepository, ApplicationContext ctx) {
        this.userRepository = userRepository;
        this.beanFactory = ctx.getAutowireCapableBeanFactory();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public AuthTokensDto login(@RequestBody @Validated LoginDto dto, Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException();
        }

        User user = userRepository.findByEmail(dto.getEmail());
        beanFactory.autowireBean(user);

        if (user == null || !user.checkPassword(dto.getPassword())) {
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED);
        }

        AuthTokensDto authTokensDto = new AuthTokensDto();
        authTokensDto.setAccessToken(UUID.randomUUID());
        return authTokensDto;
    }
}
