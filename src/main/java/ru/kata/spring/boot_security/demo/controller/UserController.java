package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @GetMapping("/userProfile")
    public String authenticationUser(Principal principal, ModelMap model) {
        model.addAttribute("user", userService.findByUserName(principal.getName()));
        return "oneUser";
    }
}
