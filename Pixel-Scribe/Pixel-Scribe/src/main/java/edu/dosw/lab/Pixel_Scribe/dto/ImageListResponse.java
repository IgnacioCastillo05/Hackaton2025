package edu.dosw.lab.Pixel_Scribe.dto;

import java.util.List;

/**
 * DTO para respuesta con lista de imágenes.
 * 
 * @param images Lista de imágenes
 * @param total Total de imágenes
 */
public record ImageListResponse(
    List<ImageListItemDTO> images,
    int total
) {}



