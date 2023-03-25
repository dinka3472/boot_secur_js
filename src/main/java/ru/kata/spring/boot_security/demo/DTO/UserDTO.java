package ru.kata.spring.boot_security.demo.DTO;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;
@Validated
public class UserDTO {

    private Long id;

    @NotEmpty(message = "FirstName should not be empty")
    private String firstName;


    @NotEmpty(message = "SecondName should not be empty")
    private String secondName;

    @Min(value = 0, message = "Age should be greater 0")
    private Long age;


    @NotEmpty(message = "Password should not be empty")
    private String password;

    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;


    @NotEmpty(message = "User should have any role")
    private Collection<RoleDTO> roles = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", rolesDTO=" + roles +
                '}';
    }
}
