package com.projet.demo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    AGENT_READ("agent:read"),
    AGENT_UPDATE("agent:update"),
    AGENT_CREATE("agent:create"),
    AGENT_DELETE("agent:delete"),
    CLIENT_READ("user:read"),
    CLIENT_UPDATE("user:update"),
    CLIENT_DELETE("user:delete"),
    CLIENT_CREATE("user:create")


    ;

    @Getter
    private final String permission;
}
