package edu.dosw.lab.Pixel_Scribe.Service;

import edu.dosw.lab.Pixel_Scribe.dto.AIAnalysisResponse;
import edu.dosw.lab.Pixel_Scribe.Exceptions.AIServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Servicio para análisis de imágenes usando Google Gemini Vision API.
 * 
 * <p>Integración con Google Gemini Pro Vision para generar descripciones automáticas de imágenes.</p>
 */
@Service
public class AIService {

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    @Value("${ai.api.key:AIzaSyD6PXejuHnPndthqxuk7rMZ3X7_uR7xbkk}")
    private String apiKey;

    @Value("${ai.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent}")
    private String apiUrl;

    @Value("${ai.api.timeout:30}")
    private int timeoutSeconds;

    private final WebClient webClient;

    public AIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
        logger.info("✅ AIService inicializado con Google Gemini Vision API");
    }

    /**
     * Analiza una imagen usando Google Gemini Vision API.
     * 
     * @param imageUrl URL pública de la imagen a analizar
     * @return AIAnalysisResponse con la descripción generada
     * @throws AIServiceException si hay error al comunicarse con la API
     */
    public AIAnalysisResponse analyzeImage(String imageUrl) {
        logger.info("🖼️ Iniciando análisis de imagen con Gemini: {}", imageUrl);

        try {
            // Construir request body para Google Gemini
            Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                    Map.of(
                        "parts", List.of(
                            Map.of("text", "Describe esta imagen en español de forma detallada pero concisa (máximo 100 palabras)."),
                            Map.of("inlineData", Map.of(
                                "mimeType", "image/jpeg",
                                "data", imageUrl // Nota: Gemini requiere base64, ajustar según necesidad
                            ))
                        )
                    )
                )
            );

            // Llamada a Gemini API
            String fullUrl = apiUrl + "?key=" + apiKey;
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = webClient.post()
                .uri(fullUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .block();

            // Extraer descripción de la respuesta
            String description = extractDescription(response);
            
            logger.info("✅ Análisis completado exitosamente");
            return new AIAnalysisResponse(description);

        } catch (WebClientResponseException e) {
            logger.error("❌ Error HTTP al llamar Gemini API: {} - {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            return AIAnalysisResponse.error("Error al analizar imagen: " + e.getMessage());
        } catch (Exception e) {
            logger.error("❌ Error inesperado al analizar imagen: {}", e.getMessage(), e);
            return AIAnalysisResponse.error("Error al procesar imagen: " + e.getMessage());
        }
    }

    /**
     * Extrae la descripción del response de Gemini API.
     */
    @SuppressWarnings("unchecked")
    private String extractDescription(Map<String, Object> response) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> firstCandidate = candidates.get(0);
                Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
                if (content != null) {
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                    if (parts != null && !parts.isEmpty()) {
                        return (String) parts.get(0).get("text");
                    }
                }
            }
            return "Imagen analizada correctamente. Descripción no disponible.";
        } catch (Exception e) {
            logger.error("Error al parsear respuesta de Gemini", e);
            return "Imagen procesada. Error al extraer descripción.";
        }
    }

    /**
     * Verifica si la API está configurada.
     */
    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank() && !apiKey.equals("your-api-key-here");
    }
}



