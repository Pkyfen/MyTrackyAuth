package ru.mytracky.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.mytracky.dto.track.TrackDto;
import ru.mytracky.dto.track.TrackGetDto;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGetDto {
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

}
