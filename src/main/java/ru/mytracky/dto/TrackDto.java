package ru.mytracky.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ru.mytracky.model.Status;
import ru.mytracky.model.Track;
import ru.mytracky.model.User;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackDto {
    @JsonView(Views.Id.class)
    private String id;
    @JsonView(Views.IdName.class)
    private String name;
    @JsonView(Views.FullMessage.class)
    private boolean isPrivate;


    public Track toTrack(User user){
        Track track = new Track();
        track.setId(id);
        track.setPrivate(isPrivate);
        track.setName(name);
        track.setCreated(new Date());
        track.setUpdated(new Date());
        track.setStatus(Status.ACTIVE);
        track.setUser(user);
        return track;
    }

    public static TrackDto fromTrack(Track track){
        TrackDto trackDto = new TrackDto();
        trackDto.setName(track.getName());
        trackDto.setId(track.getId());
        trackDto.setPrivate(track.isPrivate());

        return trackDto;
    }
}
