package ru.mytracky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mytracky.controller.exception.ApiError;
import ru.mytracky.dto.RegistrationUserDto;
import ru.mytracky.dto.TrackDto;
import ru.mytracky.dto.UserDto;
import ru.mytracky.model.Track;
import ru.mytracky.model.User;
import ru.mytracky.security.jwt.JwtTokenProvider;
import ru.mytracky.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserRestControllerV1 {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserRestControllerV1(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<User> usersList = userService.getAll();
        List<UserDto>usersDtoList = new ArrayList<>();

        for(User u:usersList){
            usersDtoList.add(UserDto.fromUser(u));
        }
        return new ResponseEntity<>(usersDtoList,HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable(name = "id") String id){

        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

//        if(!user.getUsername().equals(jwtTokenProvider.getUsername(a.substring(7)))){
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
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

    @PutMapping("{id}/track")
    public ResponseEntity<UserDto> addTrack(
            @PathVariable(name = "id") String id,
            @RequestHeader(name = "Authorization") String token,
            @RequestBody List<TrackDto> tracks){
        if(jwtTokenProvider.getId(token.substring(7)).equals(String.valueOf(id))){
            User user = userService.findById(id);
            List<Track> newTracks = user.getTracks();
            for(TrackDto t:tracks){
                newTracks.add(t.toTrack(user));
            }

            user.setTracks(newTracks);

            userService.save(user);

            return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

}