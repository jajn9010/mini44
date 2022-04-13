package com.sparta.miniproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String is_login = "true";
    private String username;
    private String nickname;


}
