package com.example.comparador_api.Utils.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Nombre de usuario obligatorio")
    private String username;

    @NotBlank(message = "Contraseña obligatoria")
    private String password;
}
