package com.example.comparador_api.services;

import com.example.comparador_api.Utils.payload.request.LoginRequest;
import com.example.comparador_api.Utils.payload.request.SignupRequest;
import com.example.comparador_api.Utils.payload.response.JwtResponse;
import com.example.comparador_api.Utils.payload.response.MessageResponse;
import com.example.comparador_api.Utils.payload.response.TokenRefreshResponse;
import com.example.comparador_api.config.services.UserDetailsImpl;
import com.example.comparador_api.entities.*;
import com.example.comparador_api.exceptions.LoginValidationException;
import com.example.comparador_api.exceptions.RegisterValidationException;
import com.example.comparador_api.exceptions.TokenRefreshException;
import com.example.comparador_api.repositories.CartRepo;
import com.example.comparador_api.repositories.RoleRepo;
import com.example.comparador_api.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepo roleRepository;
    private final RefreshTokenService refreshTokenService;
    private final CartRepo cartRepo;

    public ResponseEntity<?> register(SignupRequest request) {

        if(userRepo.existsByUsername(request.getUsername())){
            throw new RegisterValidationException("Este nombre de usuario ya está en uso!");
        }

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new RegisterValidationException("Este correo ya está en uso!");
        }


        Usuario user = Usuario.builder()
                .nombre(request.getNombre())
                .username(request.getUsername())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
        user.setRole(userRole);

        userRepo.insert(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(authentication);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(
            JwtResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .type("Bearer")
                .role(user.getRole().getName().name())
                .build()
        );



    }

    public ResponseEntity<?> authenticate(LoginRequest request) {

        if(!userRepo.existsByUsername(request.getUsername())){
            throw new LoginValidationException("Este nombre de usuario no existe");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtService.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(
                JwtResponse.builder()
                        .token(jwtToken)
                        .refreshToken(refreshToken.getToken())
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail())
                        .type("Bearer")
                        .role(roles.get(0))
                        .build()
        );
    }

    public ResponseEntity<?> refreshtoken(String requestRefreshToken) {
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUsuario)
                .map(user -> {
                    String token = jwtService.generateToken(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    public void logout(String requestRefreshToken) {
        RefreshToken userToken = refreshTokenService.findByToken(requestRefreshToken)
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));

        String userId = userToken.getUsuario().getId();
        refreshTokenService.deleteByUserId(userId);

    }
}
