package edu.dosw.lab.Pixel_Scribe.dto;

import java.time.LocalDateTime;

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
        this(message, status, LocalDateTime.now().toString());
    }
}



