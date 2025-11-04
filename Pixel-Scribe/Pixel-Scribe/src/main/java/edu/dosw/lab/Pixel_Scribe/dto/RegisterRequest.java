package edu.dosw.lab.Pixel_Scribe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para solicitud de registro.
 * 
 * @param email Email del usuario
 * @param password Contraseña en texto plano (será hasheada)
 */
public record RegisterRequest(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    String email,
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String password
) {}



