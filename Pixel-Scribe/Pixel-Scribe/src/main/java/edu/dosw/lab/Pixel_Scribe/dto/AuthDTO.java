package edu.dosw.lab.Pixel_Scribe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

/**
 * DTO para respuesta de autenticación exitosa.
 * 
 * @param token Token JWT generado
 * @param tokenType Tipo de token (siempre "Bearer")
 * @param expiresIn Tiempo de expiración en segundos
 * @param user Información básica del usuario
 */
public record AuthResponse(
    String token,
    String tokenType,
    Long expiresIn,
    UserInfoDTO user
) {
    /**
     * Constructor simplificado que establece tokenType como "Bearer"
     */
    public AuthResponse(String token, Long expiresIn, UserInfoDTO user) {
        this(token, "Bearer", expiresIn, user);
    }
}

/**
 * DTO con información básica del usuario.
 * 
 * @param id ID del usuario
 * @param email Email del usuario
 * @param role Rol del usuario
 */
public record UserInfoDTO(
    Long id,
    String email,
    String role
) {}

/**
 * DTO para respuesta de error.
 * 
 * @param message Mensaje de error
 * @param status Código de estado HTTP
 * @param timestamp Timestamp del error
 */
public record ErrorResponse(
    String message,
    int status,
    String timestamp
) {
    public ErrorResponse(String message, int status) {
        this(message, status, java.time.Instant.now().toString());
    }
}
