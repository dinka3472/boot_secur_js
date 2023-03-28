package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.*;
import java.security.Principal;


@Controller
public class MainController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String hello() {
        return "redirect:/login";
    }
    @RequestMapping("/admin")
    public String demo(Model model, Principal principal) {
        Long id = userService.findByUsername(principal.getName()).orElseThrow().getId();
        model.addAttribute("id", id);
        model.addAttribute("rolesList", roleService.getRoles());
        return "admin_page";
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        Long id = userService.findByUsername(principal.getName()).orElseThrow().getId();
        model.addAttribute("id", id);
        return "user_page";
    }

}
