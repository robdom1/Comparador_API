package com.example.comparador_api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("Usuarios")
public class Usuario {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;
    private String nombre;
    private String apellido;
    private String password;
    @Indexed(unique = true)
    private String username;
    @DocumentReference
    private Role role;
    @DocumentReference
    private Cart carrito;
}
