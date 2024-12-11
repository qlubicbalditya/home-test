package com.nutech.hometest.config;

import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutech.hometest.supports.BasicResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService service;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = getJwtFromRequest(request);
        log.info("servlet path : {}",request.getServletPath());
        log.info("token : {}",token);

        if (isAuthPath(request.getServletPath())) {
            setAuthenticationFromToken(token);
        } else {
            if (token != null && service.isTokenValid(token)) {
                setAuthenticationFromToken(token);
            } else {
                sendUnauthorizedResponse(response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private String getUsernameFromToken(String token) {
        log.info(token);
        return service.decodeJWT(token).get("email").toString();
    }

    private boolean isAuthPath(String path) {
        return "/login".equals(path) || "/registration".equals(path) 
            || "/banner".equals(path) || "/swagger-ui.html".equals(path);
    }

    private void setAuthenticationFromToken(String token) {
        if (token != null) {
            String username = getUsernameFromToken(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401 Unauthorized
        BasicResponse<Object> basicResponse = new BasicResponse<>(108, "Token tidak valid atau kadaluwarsa");
        String jsonResponse = new ObjectMapper().writeValueAsString(basicResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonResponse);
    }
}
