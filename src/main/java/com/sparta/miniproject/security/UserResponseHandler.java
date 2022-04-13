package com.sparta.miniproject.security;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserResponseHandler {

    public HttpServletResponse setResponse(HttpServletResponse httpServletResponse){
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        return httpServletResponse;
    }

    public Map<String,Object> setMessage(String message, HttpStatus httpStatus){

        String status;

        if ( httpStatus == HttpStatus.OK ){
            status = "true";
        } else{
            status = "false";
        }

        Map<String,Object> response = new HashMap<>();
        response.put("status", status);
        response.put("http", String.valueOf(httpStatus));
        response.put("message",message);
        return response;
    }
}
