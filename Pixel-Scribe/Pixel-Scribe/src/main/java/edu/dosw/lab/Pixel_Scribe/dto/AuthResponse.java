package edu.dosw.lab.Pixel_Scribe.dto;

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



