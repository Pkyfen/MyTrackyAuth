package ru.mytracky.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String username;
    private String password;

    public AuthenticationRequestDto(String username , String password) {
        this.username = username;
        this.password = password;
    }
}
