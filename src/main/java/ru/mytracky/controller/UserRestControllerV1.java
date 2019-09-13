package ru.mytracky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mytracky.dto.RegistrationUserDto;
import ru.mytracky.dto.UserDto;
import ru.mytracky.model.User;
import ru.mytracky.security.jwt.JwtTokenProvider;
import ru.mytracky.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserRestControllerV1(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable(name = "id") Long id,
            @RequestHeader(name = "Authorization") String a){

        User user = userService.findById(id);

        if(!user.getUsername().equals(jwtTokenProvider.getUsername(a.substring(7)))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationUserDto> registrationUser(
            @RequestBody  RegistrationUserDto userDto){

        if(userService.findByUsername(userDto.getUsername())!=null) return new ResponseEntity<>(HttpStatus.CONFLICT);

        User newUser = userService.register(userDto.toUser());

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


}