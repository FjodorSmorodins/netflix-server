package com.example.netflix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "html/register";  // Corresponds to src/main/resources/templates/html/register.html
    }
}
