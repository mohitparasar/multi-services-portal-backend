package com.msp.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/test")
    public String test() {

        return "User Service Working";
    }

    @GetMapping("/whoami")
    public Map<String, String> whoAmI(

            @RequestHeader("X-User-Id") String userId,

            @RequestHeader("X-User-Email") String email,

            @RequestHeader("X-User-Role") String role) {

        Map<String, String> response =
                new HashMap<>();

        response.put("userId", userId);
        response.put("email", email);
        response.put("role", role);

        return response;
    }
}