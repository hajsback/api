package com.pawmot.hajsback.api.security;

import com.pawmot.hajsback.api.exceptions.HttpStatusException;
import com.pawmot.hajsback.api.model.sessions.Session;
import com.pawmot.hajsback.api.repositories.sessions.SessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class Security {
    private final SessionRepository sessionRepository;

    private final int sessionDurationSeconds;

    public Security(SessionRepository sessionRepository, @Value("${session.duration}") int sessionDurationSeconds) {
        this.sessionRepository = sessionRepository;
        this.sessionDurationSeconds = sessionDurationSeconds;
    }

    public Session getSession(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED); // TODO: verify this HTTP status
        }

        UUID accessToken = UUID.fromString(authHeader.substring(7));
        // TODO: Transaction?
        Session session = sessionRepository.findByAccessToken(accessToken);

        if (session == null || session.getExpiresBy().isBefore(LocalDateTime.now())) {
            // TODO: remove the session?
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED); // TODO: verify this HTTP status
        }

        session.setExpiresBy(LocalDateTime.now().plusSeconds(sessionDurationSeconds));
        sessionRepository.save(session);

        return session;
    }
}
