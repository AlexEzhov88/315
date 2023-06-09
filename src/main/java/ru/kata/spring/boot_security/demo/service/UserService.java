package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();
    User getUserById(Integer id);
    void removeUserById(Integer id);
    void saveUser(User user);
    User findByEmail(String email);
    void updateUser(User updateUser);
    List<Role> getAllRoles();
}