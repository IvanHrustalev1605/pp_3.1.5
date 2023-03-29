package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final RoleService roleServiceImpl;
    private final UserService userServiceImpl;

    public RestController(RoleServiceImpl roleServiceImpl, UserServiceImpl userServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userServiceImpl.allUsers(), HttpStatus.OK);
    }
    @RequestMapping(value = "/allUsers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addUser(@RequestBody User user) {
        userServiceImpl.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/allUsers/{id}")
    public ResponseEntity<User> getOneUserById(@PathVariable("id") long id) {
       return new ResponseEntity<User>(userServiceImpl.getUserById(id), HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<User> getAuthUser(Principal principal) {
        return new ResponseEntity<>(userServiceImpl.findByUserName(principal.getName()), HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleServiceImpl.getAll(), HttpStatus.OK);
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteById(@PathVariable("id") long id) {
        userServiceImpl.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping(value = "/allUsers")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userServiceImpl.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
