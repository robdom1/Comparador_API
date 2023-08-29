package com.example.comparador_api.Utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorMessage {
    int status;
    Date date;
    int errorQty;
    List<String> message;
    String description;
}