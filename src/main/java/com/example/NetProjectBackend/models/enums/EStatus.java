package com.example.NetProjectBackend.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum EStatus implements GrantedAuthority {
    NOT_VERIFY,
    RECOVERY,
    ACTIVE;

    @Override
    public String getAuthority() {
        return name();
    }
}