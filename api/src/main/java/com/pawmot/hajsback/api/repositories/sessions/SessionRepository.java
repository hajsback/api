package com.pawmot.hajsback.api.repositories.sessions;

import com.pawmot.hajsback.api.model.sessions.Session;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends MongoRepository<Session, ObjectId> {
    Session findByAccessToken(UUID accessToken);

    Session findByUserEmail(String userEmail);
}
