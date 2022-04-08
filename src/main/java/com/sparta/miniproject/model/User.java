package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class User{
    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 반드시 값을 가지도록 합니다.
    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;


    public User(String userId, String nickname, String password, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }

    public User(UserRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.nickname = requestDto.getNickname();
        this.password = requestDto.getPassword();
        this.email = requestDto.getEmail();
    }
}
