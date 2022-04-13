package com.sparta.miniproject.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthFailueHandler implements AuthenticationFailureHandler {
    private final String DEFAULT_FAILRUE_URL = "/api/login?error=true";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = null;
        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            errorMessage = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주세요";
        }
        else{
            errorMessage = "알수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요";
        }
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher(DEFAULT_FAILRUE_URL).forward(request, response);
    }
}

