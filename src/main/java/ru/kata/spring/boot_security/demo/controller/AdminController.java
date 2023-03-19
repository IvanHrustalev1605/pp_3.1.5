package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleServiceImpl roleServiceImpl;

    public AdminController(UserService userService, RoleServiceImpl roleServiceImpl) {
        this.userService = userService;
        this.roleServiceImpl = roleServiceImpl;
    }
@GetMapping("/admin/users")
public String findAll(ModelMap model, User user, Principal principal) {
    model.addAttribute("users", userService.allUsers(user));
    model.addAttribute("authUser", userService.findByUserName(principal.getName()));
    return "users";
    }
    @GetMapping("/admin/moreAboutUser/{id}")
    public String getFullInformationAboutUser(@PathVariable("id") Long id,
                                              ModelMap model) {
            User user = userService.getUserById(id);
            if (user == null) {
                return "users";
            }
        model.addAttribute("user", user);
            return "moreAboutUser";
    }
    @GetMapping("/admin/users/addForm")
    public String show(Model model, Principal principal) {
        model.addAttribute("user", new User());
        List<Role> roles = roleServiceImpl.getAll();
        model.addAttribute("roles", roles);
        model.addAttribute("authUser", userService.findByUserName(principal.getName()));
        return "userAddForm";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("user")@Valid User user,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "userAddForm";
        }
        userService.create(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "admin/user/edit/{id}")
    public String edit(ModelMap model, @ModelAttribute(value = "id") Long id,
                       Principal principal) {
        List<Role> roles = roleServiceImpl.getAll();
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roles);
        model.addAttribute("authUser", userService.findByUserName(principal.getName()));
        return "userUpdateForm";
    }

    @PostMapping(value = "admin/user/{id}")
    public String updateUser(@ModelAttribute(value = "user")@Valid User user,
                             BindingResult bindingResult,
                             @PathVariable("id") Long id) {
            if(bindingResult.hasErrors()) {
                return "userUpdateForm";
            }

        userService.update(user, id);
        return "redirect:/admin/users";
    }
}
