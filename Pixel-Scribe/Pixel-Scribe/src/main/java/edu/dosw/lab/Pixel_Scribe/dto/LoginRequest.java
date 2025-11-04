package edu.dosw.lab.Pixel_Scribe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para solicitud de login.
 * 
 * @param email Email del usuario
 * @param password Contraseña en texto plano (será hasheada)
 */
public record LoginRequest(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    String email,
    
    @NotBlank(message = "La contraseña es obligatoria")
    String password
) {}



