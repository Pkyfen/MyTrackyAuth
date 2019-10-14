package ru.mytracky.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.mytracky.model.User;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static ru.mytracky.model.EntityConstrainConstants.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationUserDto {

    @NotBlank(message = "Name " + NOT_EMPTY)
    @Size(message = "Username not be more than " + MAX_ACCOUNT_NAME + " characters", max = MAX_ACCOUNT_NAME)
    private String username;

    @NotBlank(message = "First name " + NOT_EMPTY)
    private String firstName;

    @NotBlank(message = "Last name " + NOT_EMPTY)
    private String lastName;

    @NotBlank(message = "Email " + NOT_EMPTY)
    @Email(message = INVALID_EMAIL)
    private String email;

    @NotBlank(message = "Password " + NOT_EMPTY)
    private String password;

    public RegistrationUserDto(){}

    public RegistrationUserDto(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User toUser(){
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    public static RegistrationUserDto fromUser(User user) {
        RegistrationUserDto userDto = new RegistrationUserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

}
