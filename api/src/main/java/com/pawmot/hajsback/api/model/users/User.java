package com.pawmot.hajsback.api.model.users;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public final class User {
    @Autowired
    private transient PasswordEncoder passwordEncoder;

    User(String email) {
        this.email = email;
    }

    @Id
    @Getter
    private ObjectId id;

    @Getter
    @Indexed(unique = true)
    private String email;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    private String passwordHash;

    public void setPassword(String password) {
        passwordHash = passwordEncoder.encode(password);
    }

    public boolean checkPassword(String password) {
        return passwordEncoder.matches(password, passwordHash);
    }
}
