package com.example.comparador_api.Utils.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "Email obligatorio")
    @Size(max = 50, message = "El email debe tener menos de 50 caracteres")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Nombre de usuario obligatorio")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
    private String username;

    @NotBlank(message = "Nombre obligatorio")
    private String nombre;

    @NotBlank(message = "Apellido obligatorio")
    private String apellido;

    @NotBlank(message = "Contraseña obligatoria")
    @Size(min = 6, message = "La contraseña debe tener más de 6 caracteres")
    private String password;

}
