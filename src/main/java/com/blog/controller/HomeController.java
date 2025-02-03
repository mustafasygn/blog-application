package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Route "/" requests by redirecting them to the blog index page ("/blog")
    @GetMapping("/")
    public String home() {
        return "redirect:/blog";
    }
} 