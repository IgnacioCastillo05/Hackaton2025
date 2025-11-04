package edu.dosw.lab.Pixel_Scribe.dto;

import java.time.Instant;

/**
 * DTO para item de imagen en lista.
 * 
 * @param id ID de la imagen
 * @param title Título de la imagen
 * @param imageUrl URL pública de la imagen
 * @param thumbnailUrl URL de miniatura (opcional)
 * @param description Descripción generada por IA
 * @param createdAt Fecha de creación
 */
public record ImageListItemDTO(
    Long id,
    String title,
    String imageUrl,
    String thumbnailUrl,
    String description,
    Instant createdAt
) {}



