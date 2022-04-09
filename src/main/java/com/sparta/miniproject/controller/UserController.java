package com.sparta.miniproject.controller;
import com.sparta.miniproject.dto.UserRequestDto;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Autowired
    AuthenticationManager authenticationManager;
    //로그인요청
//    @PostMapping("/api/login")
//
    //로그인여부확인
//    @GetMapping("/api/login")
//

    // 예외 처리
    @ExceptionHandler({IllegalArgumentException.class})
    public Map<String, String> handleException(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("errMsg", e.getMessage());
        return map;
    }



}
