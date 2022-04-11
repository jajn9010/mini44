package com.sparta.miniproject.controller;

import com.sparta.miniproject.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    //테스트용
//    @GetMapping("/api/login")
//    public  @ResponseBody String login(){
//        return "index";}
}