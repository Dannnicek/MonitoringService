package com.example.endpoint_task.security;

import com.example.endpoint_task.entity.User;
import com.example.endpoint_task.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.UUID;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestPath = request.getRequestURI();

        // Only apply authentication and authorization for specific paths
        if (!Objects.equals(requestPath, "/results") || !Objects.equals(requestPath, "/endpoint")) {
            return true; // Allow access to other paths without authentication
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7); // Remove "Bearer " prefix
            // Validate the access token here
            User user = userService.getUserByAccessToken(UUID.fromString(accessToken));
            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid access token");
                return false;
            }

            // Store the authenticated user in request attribute for later use
            request.setAttribute("authenticatedUser", user);

            return true;
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid access token");
        return false;
    }
}
