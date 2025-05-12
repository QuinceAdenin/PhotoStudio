package com.PhotoStudio.PhotoStudio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // Это будет искать шаблон home.html в папке templates
    }
}