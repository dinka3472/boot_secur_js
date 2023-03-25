package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;

@Component
public class RunAfterStartup {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public RunAfterStartup(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");

        roleService.saveRole(userRole);
        roleService.saveRole(adminRole);
        userService.saveUser(new User("user",
                "user", 18L, "100",
                "user@gmail.com",
                Collections.singletonList(userRole)));
        userService.saveUser(new User("admin",
                "admin", 18L, "100",
                "admin@gmail.com",
                Arrays.asList(userRole, adminRole)));
    }
}
