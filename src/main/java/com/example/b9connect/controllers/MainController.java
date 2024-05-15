package com.example.b9connect.controllers;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/support")
    public String support(@RequestParam(value = "service_id",required = true) Integer service_id, Authentication authentication, Model model) {
        model.addAttribute("user", gson.toJson(authentication.getPrincipal()));
        return "support";
    }
    @GetMapping("/faq")
    public String faq(@RequestParam(value = "service_id",required = true) Integer service_id, Authentication authentication, Model model) {
        model.addAttribute("user", gson.toJson(authentication.getPrincipal()));
        return "faq";
    }
    @GetMapping("/newTicket")
    public String newTicket(@RequestParam(value = "service_id",required = true) Integer service_id, Authentication authentication, Model model) {
        model.addAttribute("user", gson.toJson(authentication.getPrincipal()));
        return "newTicket";
    }
    @GetMapping("/chats")
    public String chats(Authentication authentication, Model model) {
        model.addAttribute("user", gson.toJson(authentication.getPrincipal()));
        return "chats";
    }
}
