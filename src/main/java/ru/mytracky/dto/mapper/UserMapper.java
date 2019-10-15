package ru.mytracky.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mytracky.dto.user.UserDto;
import ru.mytracky.dto.user.UserGetDto;
import ru.mytracky.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "tracks", qualifiedByName = "userToUserDto")
    UserDto toUserDto(User user);

    UserGetDto toUserGetDto(User user);
}
