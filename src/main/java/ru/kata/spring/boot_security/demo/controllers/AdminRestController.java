package ru.kata.spring.boot_security.demo.controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.RoleDTO;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.Util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.Util.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.Util.UserNotFoundException;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Validated
public class AdminRestController {
    private UserService userService;
    private RoleService roleService;
    private ModelMapper modelMapper;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<HttpStatus> saveNewUser( @RequestBody @Validated UserDTO userDTO, BindingResult bindingResult) {
        checkBindinfResultErrors(bindingResult);
        userService.saveUser(createUserFromDTO(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/get/allUsers")
    public List<UserDTO> allUsersDTO(){
        List<User> allUsers = (userService).findAllUsers();
        return allUsers.stream().map(this::createDTOfromUser).collect(Collectors.toList());
    }


    @GetMapping("/get/{userId}")
    @ResponseBody
    public UserDTO findById(@PathVariable Long userId) {
        return createDTOfromUser(userService.findUserById(userId));
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long userId) {
       userService.deleteUser(userId);
       return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        checkBindinfResultErrors(bindingResult);
        userService.updateUser(createUserFromDTO(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void checkBindinfResultErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(error -> errorMsg
                    .append(error.getCode())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";"));
            System.out.println(bindingResult.getFieldErrors());
            throw new UserNotCreatedException(errorMsg.toString());
        }
    }


    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse("User with this id wasnt found",
                System.currentTimeMillis());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);

    }

    private User createUserFromDTO(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);
        List<Role> roles = user.getRoles().stream()
                .map(role -> roleService.findRoleByName(role.getName()).orElseThrow())
                .collect(Collectors.toList());
        user.setRoles(roles);
        return user;
    }

   private UserDTO createDTOfromUser(User user) {
       UserDTO userDTO = modelMapper.map(user, UserDTO.class);
       List<RoleDTO> roles = user.getRoles().stream().map(this::createDTOfromRole).collect(Collectors.toList());
       userDTO.setRoles(roles);
       return  userDTO;
   }

    private RoleDTO createDTOfromRole(Role role) {
        return  modelMapper.map(role, RoleDTO.class);
    }
}
