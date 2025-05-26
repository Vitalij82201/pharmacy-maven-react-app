package org.hstn.pharmacy.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hstn.pharmacy.security.dto.AuthRequest;
import org.hstn.pharmacy.security.dto.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public AuthResponse authenticate(AuthRequest request) {
        log.debug("Authenticating user: {}", request.getEmail());
        return authenticateUser(request.getEmail(), request.getPassword());
    }

    public AuthResponse authenticateBasic(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            throw new IllegalArgumentException("Invalid Basic authentication token");
        }

        String[] credentials = decodingBasicAuth(authHeader);
        return authenticateUser(credentials[0], credentials[1]);
    }

    private String[] decodingBasicAuth(String authHeader) {
        String base64Credentials = authHeader.substring("Basic ".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded);
        String[] values = credentials.split(":", 2);

        if (values.length != 2) {
            throw new IllegalArgumentException("Invalid Basic authentication token");
        }
        return values;
    }

    private AuthResponse authenticateUser(String username, String password) {
        log.info("Authenticating user: {}", username);

        try {
            // 1. Аутентифікація
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2. Отримання ролей у правильному форматі
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            log.debug("User roles: {}", roles);

            // 3. Генерація токена
            String jwt = tokenProvider.createToken(username, roles);

            return new AuthResponse(jwt);
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", username, e);
            throw e;
        }
    }
}