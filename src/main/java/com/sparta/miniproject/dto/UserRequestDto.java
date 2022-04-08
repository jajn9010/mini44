package com.sparta.miniproject.dto;

import lombok.*;

@Setter
@Getter
public class UserRequestDto {
    private String userId;
    private String nickname;
    private String password;
    private String passwordCheck;
    private String email;

    public UserRequestDto(String userId, String nickname, String password,String passwordCheck, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.email = email;
    }
}

