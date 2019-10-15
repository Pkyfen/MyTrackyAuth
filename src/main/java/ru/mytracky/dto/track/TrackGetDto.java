package ru.mytracky.dto.track;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.mytracky.dto.user.UserGetDto;

import javax.validation.constraints.NotBlank;

import static ru.mytracky.model.EntityConstrainConstants.NOT_EMPTY;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrackGetDto {

    private Long id;

    private String name;

    private boolean personal;

//    @JsonIgnoreProperties("tracks")
//    private UserGetDto user;

}
