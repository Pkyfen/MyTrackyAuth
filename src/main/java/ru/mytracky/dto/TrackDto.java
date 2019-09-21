package ru.mytracky.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.mytracky.model.Status;
import ru.mytracky.model.Track;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackDto {
    private String id;
    private String name;
    private boolean isPrivate;


    public Track toTrack(){
        Track track = new Track();
        track.setId(id);
        track.setPrivate(isPrivate);
        track.setName(name);
        track.setCreated(new Date());
        track.setUpdated(new Date());
        track.setStatus(Status.ACTIVE);

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
