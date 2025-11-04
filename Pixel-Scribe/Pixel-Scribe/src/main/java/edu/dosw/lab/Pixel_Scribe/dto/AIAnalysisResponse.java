package edu.dosw.lab.Pixel_Scribe.dto;

/**
 * DTO para respuesta de análisis de IA.
 * 
 * @param success Indica si el análisis fue exitoso
 * @param description Descripción generada por la IA
 * @param error Mensaje de error (si aplica)
 */
public record AIAnalysisResponse(
    boolean success,
    String description,
    String error
) {
    /**
     * Constructor para respuesta exitosa
     */
    public AIAnalysisResponse(String description) {
        this(true, description, null);
    }
    
    /**
     * Constructor para respuesta de error
     */
    public static AIAnalysisResponse error(String error) {
        return new AIAnalysisResponse(false, null, error);
    }
}



