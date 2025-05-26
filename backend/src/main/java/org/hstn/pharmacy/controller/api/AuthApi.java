package org.hstn.pharmacy.controller.api;

import jakarta.validation.Valid;
import org.hstn.pharmacy.security.dto.AuthRequest;
import org.hstn.pharmacy.security.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface AuthApi {

    @PostMapping
    ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request);


    @PostMapping("/header")
    ResponseEntity<AuthResponse> authenticateBasic(@RequestHeader(value = "Authorization") String authorizationHeader);
}
