package com.sparta.miniproject.controller;
import com.sparta.miniproject.dto.LoginResponseDto;
import com.sparta.miniproject.dto.UserRequestDto;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;


    //회원가입
    @PostMapping("/api/signup")
    public User createUser(@RequestBody UserRequestDto requestDto) {

        return userService.registerUser(requestDto);
    }

    // 예외 처리
    @ExceptionHandler({IllegalArgumentException.class})
    public Map<String, String> handleException(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("errMsg", e.getMessage());
        return map;
    }

    //로그인후 유저정보 가져오기
    @GetMapping("/user/loginCheck")
    public LoginResponseDto UserInfo( @AuthenticationPrincipal UserDetailsImpl userDetails){

            String is_login = "true";
            String username = userDetails.getUser().getUsername();
            String nickname = userDetails.getUser().getNickname();
            return new LoginResponseDto(is_login,username, nickname);
        }


}
