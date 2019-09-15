package ru.mytracky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mytracky.controller.exception.ApiError;
import ru.mytracky.dto.RegistrationUserDto;
import ru.mytracky.dto.UserDto;
import ru.mytracky.model.User;
import ru.mytracky.security.jwt.JwtTokenProvider;
import ru.mytracky.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestControllerV1 {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserRestControllerV1(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping()
    public List<User> getAllUsers(){
        return userService.getAll();
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable(name = "id") String id,
            @RequestHeader(name = "Authorization") String a){

        System.out.println(a);
        System.out.println(id + " TEST");
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if(!user.getUsername().equals(jwtTokenProvider.getUsername(a.substring(7)))){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteUsers(
            @PathVariable(name = "id") String id,
            @RequestHeader(name = "Authorization") String token){
        if(jwtTokenProvider.getId(token.substring(7)).equals(String.valueOf(id))){
            userService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("test")
    public ResponseEntity<Object> test(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}