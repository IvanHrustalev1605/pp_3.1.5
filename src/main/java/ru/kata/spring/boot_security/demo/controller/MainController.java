package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/api/admin/")
    public String allUsers() {
        return "mainAdmin";
    }
    @GetMapping("/api/authUser/")
    public String user() {
        return "mainUser";
    }
}
