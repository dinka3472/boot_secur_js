package ru.kata.spring.boot_security.demo.service;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);
    User findUserById(Long id);
    List<User> findAllUsers();
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
    Optional<User> findByEmail(String email);

}

