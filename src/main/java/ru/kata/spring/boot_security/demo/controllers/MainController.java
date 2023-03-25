package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
