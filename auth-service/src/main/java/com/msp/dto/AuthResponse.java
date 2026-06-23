package com.msp.dto;

import com.msp.entity.Role;

public class AuthResponse {

    private Long userId;

    private Role role;

    private String accessToken;

   

    public AuthResponse() {
    }

    public AuthResponse(
            Long userId,
            Role role,
            String accessToken
          ) {

        this.userId = userId;
        this.role = role;
        this.accessToken = accessToken;
       
    }

    public Long getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }

    public String getAccessToken() {
        return accessToken;
    }

 
}