package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/allUsers")
public class RestAdminController {

    private final RoleService roleService;
    private final UserService userService;

    public RestAdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity addUser(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getOneUserById(@PathVariable("id") long id) {
       return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteById(@PathVariable("id") long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
