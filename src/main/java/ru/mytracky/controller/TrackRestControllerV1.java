package ru.mytracky.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mytracky.dto.AdminUserDto;
import ru.mytracky.dto.TrackDto;
import ru.mytracky.model.Track;
import ru.mytracky.repository.TrackRepository;
import ru.mytracky.security.jwt.JwtTokenProvider;
import ru.mytracky.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tracks")
public class TrackRestControllerV1 {

    private final TrackRepository trackRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public TrackRestControllerV1(TrackRepository trackRepository, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.trackRepository = trackRepository;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping()
    public ResponseEntity<List<TrackDto>> getAllUsers() {
        List<Track> trackList = trackRepository.findAll();
        List<TrackDto> trackDtos = new ArrayList<>();

        for (Track t : trackList) {
            trackDtos.add(TrackDto.fromTrack(t));
        }

        return new ResponseEntity<>(trackDtos, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AdminUserDto> deleteTrack(@PathVariable(name = "id") Track track,
                                                    @RequestHeader(name = "Authorization") String token) {
        String id = jwtTokenProvider.getId(token.substring(7));
        if(id.equals(track.getUser().getId())){
            trackRepository.delete(track);
            return new ResponseEntity<>(AdminUserDto.fromUser(userService.findById(id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

