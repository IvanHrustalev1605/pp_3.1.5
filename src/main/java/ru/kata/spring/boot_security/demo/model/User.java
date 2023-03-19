package ru.kata.spring.boot_security.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 25, message = "Username must be up 3 to 25 characters!")
    @NotBlank(message = "username must not be empty!")
    private String username;
    @Size(min = 4)
    private String password;
    @NotNull(message = "Name must not be empty!")
    private String name;
    @NotBlank(message = "lastName must not be empty!")
    private String lastName;
    @Min(value = 1, message = "Age should not be more than 0")
    @NotNull(message = "age must not be empty!")
    private Integer age;
    @NotBlank(message = "Email must not be empty!")
    @Email(message = "Incorrect type of email!")
    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @NotNull
    private Collection<Role> role;

}
