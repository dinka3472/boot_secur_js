package ru.kata.spring.boot_security.demo.DTO;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;

@Validated
public class RoleDTO {
    @NotEmpty
    private String name;

    public String getName() {
            return name;
        }

    public void setName(String name) {
            this.name = name;
        }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
