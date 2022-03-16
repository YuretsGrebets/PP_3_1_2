package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitialDataRunner implements CommandLineRunner {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        Role role1 = new Role("ROLE_USER");
        Role role2 = new Role("ROLE_ADMIN");
        roleService.save(role1);
        roleService.save(role2);
        List<Role> u1roles = new ArrayList<>();
        u1roles.add(role2);
        u1roles.add(role1);
        List<Role> u2roles = new ArrayList<>();
        u2roles.add(role1);

        User user1 = new User("admin", "admin", 8, u1roles, "admin", "admin@");
        User user2 = new User("user", "user", 10, u2roles, "user", "user@");

        userService.save(user1);
        userService.save(user2);
    }
}
