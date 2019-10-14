package ru.mytracky.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mytracky.dto.AdminUserDto;
import ru.mytracky.dto.UserDto;
import ru.mytracky.model.Track;
import ru.mytracky.model.User;
import ru.mytracky.repository.TrackRepository;
import ru.mytracky.security.jwt.JwtTokenProvider;
import ru.mytracky.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/tracks")
public class TrackRestControllerV1 {

    private final TrackRepository trackRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public TrackRestControllerV1(TrackRepository trackRepository,
                                 UserService userService,
                                 JwtTokenProvider jwtTokenProvider) {
        this.trackRepository = trackRepository;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AdminUserDto> deleteTrack(@PathVariable(name = "id") Long trackId,
                                               @RequestHeader(name = "Authorization") String token) {
        Long userId = jwtTokenProvider.getId(token.substring(7));
        Track track = trackRepository.findOneById(trackId);
        if(track==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(userId.equals(track.getUser().getId())){
            trackRepository.delete(track);
            return new ResponseEntity<>(AdminUserDto.fromUser(userService.findById(userId)), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

