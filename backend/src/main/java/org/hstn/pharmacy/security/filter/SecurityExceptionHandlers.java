package org.hstn.pharmacy.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.hstn.pharmacy.dto.dtoUser.StandardResponseDto;
import org.hstn.pharmacy.exceptions.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

public class SecurityExceptionHandlers {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(SecurityExceptionHandlers.class);

    // Приватний конструктор, щоб запобігти інстанціюванню класу
    private SecurityExceptionHandlers() {
        throw new IllegalStateException("Utility class");
    }

    public static final AuthenticationEntryPoint ENTRY_POINT = (request, response, authException) -> {
        String message = "User unauthorized";
        if (authException instanceof InvalidJwtException) {
            message = authException.getMessage();
        }
        fillResponse(response, HttpStatus.UNAUTHORIZED, message);
    };

    public static final AccessDeniedHandler ACCESS_DENIED_HANDLER = (request, response, accessDeniedException) -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = (authentication != null && authentication.getPrincipal() != null)
                ? (authentication.getPrincipal() instanceof UserDetails
                ? ((UserDetails) authentication.getPrincipal()).getUsername()
                : authentication.getName())
                : "unknown";
        String message = "Access denied for user " + user;
        fillResponse(response, HttpStatus.FORBIDDEN, message);
    };

    private static void fillResponse(HttpServletResponse response, HttpStatus status, String message) {
        try {
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            StandardResponseDto responseDto = new StandardResponseDto(message + ": " + status.value());
            String body = objectMapper.writeValueAsString(responseDto);

            response.getWriter().write(body);
        } catch (java.io.IOException e) {
            logger.error("Failed to write response: ", e);
            throw new IllegalStateException("Failed to write response", e);
        } catch (Exception e) {
            logger.error("Unexpected error while writing response: ", e);
            throw new IllegalStateException("Unexpected error while writing response", e);
        }
    }
}