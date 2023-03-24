package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.validation.Valid;
import java.security.Principal;


@Controller(value = "/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String findAll(ModelMap model, User user, Principal principal) {
        model.addAttribute("users", userService.allUsers(user));
        model.addAttribute("authUser", userService.findByUserName(principal.getName()));
        model.addAttribute("roles", roleService.getAll());
        return "users";
    }

    @GetMapping("/moreAboutUser/{id}")
    public String getFullInformationAboutUser(@PathVariable("id") Long id,
                                              ModelMap model,
                                              Principal principal) {
            User user = userService.getUserById(id);
            if (user == null) {
                return "users";
            }
            model.addAttribute("user", user);
            model.addAttribute("authUser", userService.findByUserName(principal.getName()));
            return "moreAboutUser";
    }

    @GetMapping("/users/addForm")
    public String show(Model model, Principal principal) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("authUser", userService.findByUserName(principal.getName()));
        return "userAddForm";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user")@Valid User user,
                          BindingResult bindingResult,
                          ModelMap model, Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAll());
            model.addAttribute("authUser", userService.findByUserName(principal.getName()));
            return "userAddForm";
        }
        if (userService.findByUserName(user.getUsername()) != null) {
            bindingResult.addError(new FieldError("username", "username",
                    String.format("User with name \"%s\" is already exist!", user.getUsername())));
            model.addAttribute("roles", roleService.getAll());
            model.addAttribute("authUser", userService.findByUserName(principal.getName()));
            return "userAddForm";
        }
        userService.create(user);
        return "redirect:/users";
    }

    @DeleteMapping(value = "/del/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @PutMapping(value = "user/update/{id}")
    public String updateUser(@ModelAttribute(value = "user") User user) {
        userService.update(user);
        return "redirect:/users";
    }
}
