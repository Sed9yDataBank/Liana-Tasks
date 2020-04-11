package com.kanbanedchain.lianatasks.Messages.Responses;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

public class JWTResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    public UUID uid;
    private Collection<? extends GrantedAuthority> authorities;

    public JWTResponse(String accessToken, String username, Collection<? extends GrantedAuthority> authorities,
                       UUID uid) {
        this.token = accessToken;
        this.username = username;
        this.authorities = authorities;
        this.uid = uid;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UUID getId() {
        return uid;
    }

    public void setId(UUID uid) {
        this.uid = uid;
    }
}
