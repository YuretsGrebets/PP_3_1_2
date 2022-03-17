package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailsService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;


@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserDetailsService userDetailsServiceImpl;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, UserDetailsService userDetailsServiceImpl) {
        this.userService = userService;
        this.roleService = roleService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @GetMapping("/admin/users")
    public String getUsers(Principal principal, Model model) {
        model.addAttribute("keyValue", userService.index());
        model.addAttribute("user", new User());
        model.addAttribute("rolesList", roleService.getRoles());
        model.addAttribute("user1", userDetailsServiceImpl.findByUsername(principal.getName()));
        return "usersView";
    }

    @PostMapping("/admin/users")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "rolesIdArr", required = false) long[] rolesIdArr) {
        User updatedUser = userService.setRolesToUser(user, rolesIdArr);
        userService.save(updatedUser);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/edit")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "rolesIdArr", required = false) long[] rolesIdArr) {
        User updatedUser = userService.setRolesToUser(user, rolesIdArr);
        userService.update(updatedUser);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

}