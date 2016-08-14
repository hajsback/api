package com.pawmot.hajsback.api.controllers;

import com.pawmot.hajsback.api.dto.AuthTokensDto;
import com.pawmot.hajsback.api.dto.LoginDto;
import com.pawmot.hajsback.api.exceptions.HttpStatusException;
import com.pawmot.hajsback.api.exceptions.ValidationException;
import com.pawmot.hajsback.api.model.sessions.Session;
import com.pawmot.hajsback.api.model.sessions.SessionFactory;
import com.pawmot.hajsback.api.model.users.User;
import com.pawmot.hajsback.api.repositories.sessions.SessionRepository;
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
    private final SessionFactory sessionFactory;
    private final SessionRepository sessionRepository;
    private final AutowireCapableBeanFactory beanFactory;

    @Autowired
    public AuthController(
            UserRepository userRepository,
            SessionFactory sessionFactory,
            SessionRepository sessionRepository,
            AutowireCapableBeanFactory beanFactory) {
        this.userRepository = userRepository;
        this.sessionFactory = sessionFactory;
        this.sessionRepository = sessionRepository;
        this.beanFactory = beanFactory;
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

        Session session = sessionRepository.findByUserEmail(dto.getEmail());
        if (session != null) {
            sessionRepository.delete(session);
        }

        session = sessionFactory.create(dto.getEmail());
        sessionRepository.save(session);

        AuthTokensDto tokens = new AuthTokensDto();
        tokens.setAccessToken(session.getAccessToken());
        return tokens;
    }
}
