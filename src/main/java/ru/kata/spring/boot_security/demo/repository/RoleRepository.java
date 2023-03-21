package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
