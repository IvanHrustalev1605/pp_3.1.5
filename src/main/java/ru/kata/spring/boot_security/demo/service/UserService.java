package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public void create(User user);
    public List<User> allUsers(User user);
    public User getUserById(Long id);
    public void update(User user);
    public void delete(Long id);
    public User findByUserName(String username);
}
