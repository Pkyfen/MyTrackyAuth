package ru.mytracky.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.mytracky.dto.track.TrackDto;
import ru.mytracky.dto.track.TrackGetDto;
import ru.mytracky.model.Track;


@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackGetDto toTrackGetDto(Track track);

    @Mapping(target = "id", ignore = true)
    Track toTrack(TrackDto trackDto);
}
