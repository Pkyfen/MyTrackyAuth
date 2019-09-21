package ru.mytracky.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mytracky.dto.TrackDto;
import ru.mytracky.dto.UserDto;
import ru.mytracky.model.Track;
import ru.mytracky.model.User;
import ru.mytracky.repository.TrackRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tracks")
public class TrackRestControllerV1 {

    @Autowired
    TrackRepository trackRepository;

    @GetMapping()
    public ResponseEntity<List<TrackDto>> getAllUsers(){
        List<Track> trackList = trackRepository.findAll();
        List<TrackDto>trackDtos = new ArrayList<>();

        for(Track t:trackList){
            trackDtos.add(TrackDto.fromTrack(t));
        }

        return new ResponseEntity<>(trackDtos, HttpStatus.OK);
    }
}
