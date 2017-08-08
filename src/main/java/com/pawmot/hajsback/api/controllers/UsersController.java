package com.pawmot.hajsback.api.controllers;

import com.pawmot.hajsback.api.dto.RegisterUserDto;
import com.pawmot.hajsback.api.dto.UserDetailsDto;
import com.pawmot.hajsback.api.exceptions.HttpStatusException;
import com.pawmot.hajsback.api.exceptions.ValidationException;
import com.pawmot.hajsback.api.model.users.User;
import com.pawmot.hajsback.api.model.users.UserFactory;
import com.pawmot.hajsback.api.repositories.users.UserRepository;
import com.pawmot.hajsback.api.security.Security;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

// TODO: remove CORS and implement rev-proxy
// TODO: move this logic to a separate microservice
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/v1/users")
@AllArgsConstructor
public class UsersController {
    private final UserFactory userFactory;
    private final UserRepository userRepository;
    private final Security security;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void registerUser(@RequestBody @Validated RegisterUserDto dto, Errors errors) {
        if (errors.hasErrors()) {
            throw new ValidationException();
        }

        User user = userFactory.create(dto.getEmail());

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());

        userRepository.save(user);
    }

    @GetMapping("{email:.+}")
    public UserDetailsDto getDetails(@PathVariable("email") String login, HttpServletRequest request) {
        val session = security.getSession(request);
        User user = userRepository.findByEmail(session.getUserEmail());

        if(!login.equals(user.getEmail())) {
            throw new HttpStatusException(HttpStatus.FORBIDDEN);
        }

        return UserDetailsDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
