package com.sparta.miniproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Component
public class RestLogoutSucccessHandler implements LogoutSuccessHandler {

    @Autowired
    UserResponseHandler userResponseHandlerHandler;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException, ServletException {

        httpServletResponse = userResponseHandlerHandler.setResponse(httpServletResponse);
        Map<String,Object> response = userResponseHandlerHandler.setMessage("로그아웃이 완료되었습니다.", HttpStatus.OK);

        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, response);
        out.flush();
    };
}
