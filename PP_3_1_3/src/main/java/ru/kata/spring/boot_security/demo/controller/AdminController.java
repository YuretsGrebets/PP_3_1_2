package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;


@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        List<User> demandedUserList = userService.index();
        model.addAttribute("keyValue", demandedUserList);
        return "usersView";
    }

    @GetMapping("/admin/users/new")
    public String newCar(Model model) {
        List<Role> rolesList = roleService.getRoles();
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("rolesList", rolesList);
        return "new";
    }

    @PostMapping("/admin/users")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "rolesIdArr", required = false) long[] rolesIdArr) {
        User updatedUser = userService.setRolesToUser(user, rolesIdArr);
        userService.save(updatedUser);
        return "redirect:/admin/users/";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        User user1 = userService.getUser(id);
        List<Role> rolesList = roleService.getRoles();
        model.addAttribute("rolesList", rolesList);
        model.addAttribute("user", user1);
        return "edit";
    }

    @PostMapping("/admin/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "rolesIdArr", required = false) long[] rolesIdArr) {
        User updatedUser = userService.setRolesToUser(user, rolesIdArr);
        userService.update(updatedUser);
        return "redirect:/admin/users/";
    }

    @GetMapping("/admin/users/{id}/delete")
    public String delete(Model model, @PathVariable("id") long id) {
        User user1 = userService.getUser(id);
        model.addAttribute("user", user1);
        return "delete";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin/users/";
    }

    @GetMapping("/admin")
    public String pageRedirect(Principal principal) {
        return "redirect:/admin/users/";
    }

}