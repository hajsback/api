package com.pawmot.hajsback.api.model.sessions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class SessionFactory {
    @Value("${session.duration}")
    private int sessionDurationSeconds;

    public Session create(String email) {
        UUID token = UUID.randomUUID();
        LocalDateTime expiresBy = LocalDateTime.now().plusSeconds(sessionDurationSeconds);
        return new Session(email, token, expiresBy);
    }
}
