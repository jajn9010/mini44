package com.sparta.miniproject.controller;
import com.sparta.miniproject.dto.UserRequestDto;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    //로그인

    // 예외 처리
    @ExceptionHandler({IllegalArgumentException.class})
    public Map<String, String> handleException(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("errMsg", e.getMessage());
        return map;
    }



}
