package com.msp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.msp.dto.AuthResponse;
import com.msp.dto.LoginRequest;
import com.msp.dto.LoginResult;
import com.msp.dto.RegisterRequest;
import com.msp.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    // ================= REGISTER =================

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request));
    }


    // ================= LOGIN =================

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        LoginResult result = authService.login(request);


        ResponseCookie cookie = ResponseCookie.from(
                "refreshToken",
                result.getRefreshToken())

                .httpOnly(true)

                .secure(false)      // true in HTTPS production

                .path("/")

                .maxAge(7 * 24 * 60 * 60)

                .sameSite("Strict")

                .build();


        return ResponseEntity.ok()

                .header(
                        HttpHeaders.SET_COOKIE,

                        cookie.toString())

                .body(
                        result.getAuthResponse());
    }


    // ================= REFRESH =================

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(

            @CookieValue(
                    value = "refreshToken",
                    required = false)

            String refreshToken) {


        return ResponseEntity.ok(

                authService.refreshToken(
                        refreshToken));
    }


    // ================= LOGOUT =================

    @PostMapping("/logout")
    public ResponseEntity<String> logout(

            @CookieValue(
                    value = "refreshToken",
                    required = false)

            String refreshToken) {


        authService.logout(refreshToken);


        ResponseCookie cookie =

                ResponseCookie.from(

                        "refreshToken",

                        "")

                        .httpOnly(true)

                        .secure(false)

                        .path("/")

                        .maxAge(0)

                        .build();


        return ResponseEntity.ok()

                .header(

                        HttpHeaders.SET_COOKIE,

                        cookie.toString())

                .body(

                        "Logged out successfully");
    }

}