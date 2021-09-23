package com.example.crudsecurityboot.controller;

import com.example.crudsecurityboot.repository.RoleRepository;
import com.example.crudsecurityboot.model.User;
import com.example.crudsecurityboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/pageForUser")
    public String getUserPage(Model model, Principal principal) {
        User u = (User) userService.getByName(principal.getName());
        model.addAttribute("user", u);
        return "pageForUser";
    }

    @GetMapping("/admin")
    public  String showAllUser(Model model, Principal principal) {
        List<User> allUs = userService.getAllUsers();
        model.addAttribute("allUs",allUs);
        User u = (User) userService.getByName(principal.getName());
        model.addAttribute("user", u);
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("new_user", new User());
        return "admin";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("user", new User());
        return "addNewUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/userUpdate/{id}")
    public String edit(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("user", userService.getUserId(id));
        model.addAttribute("roles", userService.getAllRoles());
        return "userUpdate";
    }

    @PostMapping("/userUpdate")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles", required = false)String [] roleList) {
        userService.updateUserAndRoles(user, roleList);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
