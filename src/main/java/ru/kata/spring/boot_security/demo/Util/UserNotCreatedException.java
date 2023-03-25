package ru.kata.spring.boot_security.demo.Util;

public class UserNotCreatedException extends RuntimeException {
    private String message;

    public UserNotCreatedException(String message) {
        super(message);
    }
}
