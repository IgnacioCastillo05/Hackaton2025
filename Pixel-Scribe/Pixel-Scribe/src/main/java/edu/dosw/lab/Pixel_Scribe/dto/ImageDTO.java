package edu.dosw.lab.Pixel_Scribe.dto;


import java.time.Instant;

/**
 * DTO para respuesta de subida de imagen exitosa.
 * 
 * @param id ID de la imagen en la base de datos
 * @param title Título de la imagen
 * @param imageUrl URL donde está almacenada la imagen
 * @param description Descripción generada por IA
 * @param analyzedAt Timestamp del análisis
 * @param createdAt Timestamp de creación
 */
public record ImageUploadResponse(
    Long id,
    String title,
    String imageUrl,
    String description,
    Instant analyzedAt,
    Instant createdAt
) {}

/**
 * DTO para listar imágenes en la galería.
 * 
 * @param id ID de la imagen
 * @param title Título de la imagen
 * @param imageUrl URL de la imagen
 * @param description Descripción generada por IA
 * @param createdAt Timestamp de creación
 * @param analyzedAt Timestamp del análisis (null si aún no se analizó)
 */
public record ImageListItemDTO(
    Long id,
    String title,
    String imageUrl,
    String description,
    Instant createdAt,
    Instant analyzedAt
) {}

/**
 * DTO para respuesta con lista de imágenes.
 * 
 * @param images Lista de imágenes
 * @param total Cantidad total de imágenes
 */
public record ImageListResponse(
    java.util.List<ImageListItemDTO> images,
    long total
) {}

/**
 * DTO para la respuesta de análisis de IA.
 * 
 * @param description Descripción generada por la IA
 * @param success Indica si el análisis fue exitoso
 * @param error Mensaje de error si hubo problemas (null si success=true)
 */
public record AIAnalysisResponse(
    String description,
    boolean success,
    String error
) {
    /**
     * Constructor para análisis exitoso
     */
    public AIAnalysisResponse(String description) {
        this(description, true, null);
    }
    
    /**
     * Constructor para análisis fallido
     */
    public static AIAnalysisResponse error(String errorMessage) {
        return new AIAnalysisResponse(null, false, errorMessage);
    }
}