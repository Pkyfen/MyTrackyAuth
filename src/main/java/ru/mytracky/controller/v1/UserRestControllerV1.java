package ru.mytracky.controller.v1;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mytracky.controller.exception.UserNotFoundException;
import ru.mytracky.dto.mapper.UserMapper;
import ru.mytracky.dto.track.TrackDto;
import ru.mytracky.dto.user.UserDto;
import ru.mytracky.dto.user.UserGetDto;
import ru.mytracky.model.User;
import ru.mytracky.security.jwt.JwtTokenProvider;
import ru.mytracky.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users")
public class UserRestControllerV1 {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;


    @GetMapping()
    public List<UserGetDto> getAllUsers(){
        return userService.getAll()
                .stream()
                .map(userMapper::toUserGetDto)
                .collect(Collectors.toList());
    }


    @GetMapping(value = "/{id}")
    public UserDto getUserById(
            @PathVariable(name = "id") Long id){
        User user = userService.findById(id);
        if(user == null){
           throw new UserNotFoundException(id);
        }

        return userMapper.toUserDto(user);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUsers(
            @PathVariable(name = "id") Long id,
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
            @PathVariable(name = "id") Long id,
            @RequestHeader(name = "Authorization") String token,
            @Validated @RequestBody TrackDto track){

        if(jwtTokenProvider.getId(token.substring(7))==id){
            User user = userService.findById(id);
            user.addTrack(track.toTrack(user));
            userService.save(user);

            return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.OK);
        }else{
            System.out.println(jwtTokenProvider.getId(token.substring(7))==1);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

}