package com.qirsam.mini_library.database.entity.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN,
    MODERATOR,
    USER_GOOGLE;

    @Override
    public String getAuthority() {
        return name();
    }
}
