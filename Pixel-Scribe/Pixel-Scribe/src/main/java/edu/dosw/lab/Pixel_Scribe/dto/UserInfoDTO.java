package edu.dosw.lab.Pixel_Scribe.dto;

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



