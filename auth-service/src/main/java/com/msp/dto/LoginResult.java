package com.msp.dto;

public class LoginResult {

    private AuthResponse authResponse;

    private String refreshToken;

    public LoginResult(AuthResponse authResponse, String refreshToken) {
        this.authResponse = authResponse;
        this.refreshToken = refreshToken;
    }

    public AuthResponse getAuthResponse() {
        return authResponse;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}