package ru.mytracky.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.mytracky.model.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


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
