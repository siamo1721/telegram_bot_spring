package ru.tgbot.tgbot.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    admin,
    manager,
    user;

    @Override
    public String getAuthority() {
        return this.name();
    }
}