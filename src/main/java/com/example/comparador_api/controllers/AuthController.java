package com.example.comparador_api.controllers;

import com.example.comparador_api.Utils.payload.request.LoginRequest;
import com.example.comparador_api.Utils.payload.request.SignupRequest;
import com.example.comparador_api.Utils.payload.request.TokenRefreshRequest;
import com.example.comparador_api.Utils.payload.response.JwtResponse;
import com.example.comparador_api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request){
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest request){
        return authService.authenticate(request);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return authService.refreshtoken(requestRefreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        authService.logout(requestRefreshToken);
        return ResponseEntity.ok().build();
    }

}
