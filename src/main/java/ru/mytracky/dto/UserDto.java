package ru.mytracky.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.mytracky.model.Track;
import ru.mytracky.model.User;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @JsonView(Views.Id.class)
    private String id;
    @JsonView(Views.IdName.class)
    private String username;
    @JsonView(Views.IdName.class)
    private String firstName;
    @JsonView(Views.IdName.class)
    private String lastName;
    @JsonView(Views.FullMessage.class)
    private String email;
    @JsonView(Views.FullMessage.class)
    private List<TrackDto> tracks;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        List<Track> tracks = user.getTracks();
        List<TrackDto> trackDtos = new ArrayList<>();
        for(Track t:tracks){
            trackDtos.add(TrackDto.fromTrack(t));
        }

        userDto.setTracks(trackDtos);

        return userDto;
    }
}