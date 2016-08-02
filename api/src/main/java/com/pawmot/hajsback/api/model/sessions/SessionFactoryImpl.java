package com.pawmot.hajsback.api.model.sessions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
class SessionFactoryImpl implements SessionFactory {
    @Value("${session.duration}")
    private int sessionDurationSeconds;

    @Override
    public Session create(String email) {
        UUID token = UUID.randomUUID();
        LocalDateTime expiresBy = LocalDateTime.now().plusSeconds(sessionDurationSeconds);
        return new Session(email, token, expiresBy);
    }
}
