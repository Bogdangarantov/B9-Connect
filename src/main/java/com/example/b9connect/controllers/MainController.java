package com.example.b9connect.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final Gson gson;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        model.addAttribute("user", gson.toJson(authentication.getPrincipal()));
        return "index";
    }

    @GetMapping("/management")
    public String management(Authentication authentication, Model model) {
        model.addAttribute("user", gson.toJson(authentication.getPrincipal()));
        return "management";
    }
    @GetMapping("/services")
    public String services(Authentication authentication, Model model) {
        model.addAttribute("user", gson.toJson(authentication.getPrincipal()));
        return "services";
    }
}
