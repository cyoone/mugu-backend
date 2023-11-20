package com.example.mugu.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntrypoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 로그를 남깁니다.
        System.out.println("Authentication error: " + authException.getMessage());
        // 오류 페이지로 리다이렉트합니다.
        response.sendRedirect("/error");
    }
}
