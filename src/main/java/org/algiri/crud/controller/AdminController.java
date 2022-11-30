package org.algiri.crud.controller;

import org.algiri.crud.model.Role;
import org.algiri.crud.model.User;
import org.algiri.crud.service.RoleService;
import org.algiri.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author RulleR
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String getUsersAlias() {
        return "redirect:/admin";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "rolesId", defaultValue = "2") List<Long> rolesId) {
        rolesId.forEach(id -> {
            Role role = roleService.findById(id);
            if (role != null) {
                user.addRole(role);
            }
        });
        userService.add(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/users")
    public String deleteUser(@RequestParam(value = "deleteId") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PatchMapping("/users")
    public String editUser(User user,
                           @RequestParam(value = "rolesId", defaultValue = "2") List<Long> rolesId) {
        rolesId.forEach(id -> {
            Role role = roleService.findById(id);
            if (role != null) {
                user.addRole(role);
            }
        });
        userService.update(user.getId(), user);
        return "redirect:/admin";
    }

    @GetMapping("")
    public String getAllUsers(Model model) {
        if (model.getAttribute("loggedUser") == null) {
            return "redirect:/";
        }
        model.addAttribute("users", userService.findAll());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/user")
    public String getProfile() {
        return "profile";
    }

    @ModelAttribute
    public void addAttributes(Authentication authentication, Model model) {
        model.addAttribute("loggedUser", userService.getUserByAuth(authentication));
    }
}
