package com.qirsam.mini_library.http.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String homePage() {
        return "redirect:/books";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }
}
