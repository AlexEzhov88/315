package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class Admin {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public Admin(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void initialization() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);
        User admin = new User("admin", "admin", 34,"admin@gmail.com", "admin");
        admin.setRoles(Set.of(roleAdmin));
        userService.saveUser(admin);
        User user = new User("user", "user",34,"user@gmail.com", "user");
        user.setRoles(Set.of(roleUser));
        userService.saveUser(user);

    }
}
