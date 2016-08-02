package com.pawmot.hajsback.api.model.sessions;

public interface SessionFactory {
    Session create(String email);
}
