package ru.tgbot.tgbot.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    prava,
    bezprav,
    normprav;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
