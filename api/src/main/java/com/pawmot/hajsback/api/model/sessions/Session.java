package com.pawmot.hajsback.api.model.sessions;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class Session {
    @Id
    @Getter
    private ObjectId id;

    @Indexed(unique = true)
    @Getter
    private String userEmail;

    @Indexed(unique = true)
    @Getter
    private UUID accessToken;

    @Getter
    @Setter
    private LocalDateTime expiresBy;
}
