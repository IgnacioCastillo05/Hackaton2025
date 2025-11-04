package edu.dosw.lab.Pixel_Scribe.dto;

import java.time.Instant;

/**
 * DTO para respuesta de subida de imagen.
 * 
 * @param id ID de la imagen
 * @param title Título de la imagen
 * @param imageUrl URL pública de la imagen
 * @param description Descripción generada por IA
 * @param createdAt Fecha de creación
 */
public record ImageUploadResponse(
    Long id,
    String title,
    String imageUrl,
    String description,
    Instant createdAt
) {}



