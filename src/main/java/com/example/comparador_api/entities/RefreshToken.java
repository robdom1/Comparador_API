package com.example.comparador_api.entities;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Unwrapped;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("RefreshToken")
public class RefreshToken {

    @Id
    private String id;
    @DocumentReference
    private Usuario usuario;
    @Indexed(unique = true)
    @NotNull
    private String token;
    @NotNull
    private Instant expiryDate;

}
