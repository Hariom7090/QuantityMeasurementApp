package com.app.quantitymeasurement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/api/auth/login")
    public String login() {

        return "redirect:/oauth2/authorization/google";
    }
}