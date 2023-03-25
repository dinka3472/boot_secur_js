package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public List<Role> getRoles();
    public void saveRole(Role role);
    public Optional<Role> findRoleByName(String name);
}
