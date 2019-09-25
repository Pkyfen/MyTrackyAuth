package ru.mytracky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mytracky.controller.exception.ApiError;
import ru.mytracky.dto.RegistrationUserDto;
import ru.mytracky.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/")
public class RegistrationControllerV1 {
    private final UserService userService;

    @Autowired
    public RegistrationControllerV1(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registrationUser(
            @RequestBody RegistrationUserDto userDto){

        if(userService.findByUsername(userDto.getUsername())!=null){
            return new ResponseEntity<>(new ApiError(HttpStatus.CONFLICT, "User already registered"),
                    HttpStatus.CONFLICT);
        }

        userService.register(userDto.toUser());

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
