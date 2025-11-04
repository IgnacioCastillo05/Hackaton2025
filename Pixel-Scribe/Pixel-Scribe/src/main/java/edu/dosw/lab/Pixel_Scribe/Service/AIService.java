package edu.dosw.lab.Pixel_Scribe.Service;

import edu.dosw.lab.pixelscribe.dto.AIAnalysisResponse;
import edu.dosw.lab.pixelscribe.exception.AIServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Servicio para analizar imágenes usando APIs de IA (OpenAI GPT-4 Vision o Google Gemini).
 * 
 * <p>Este servicio se conecta con APIs externas de IA para generar descripciones de imágenes.</p>
 * 
 * <p>APIs soportadas:</p>
 * <ul>
 *   <li>OpenAI GPT-4 Vision</li>
 *   <li>Google Gemini Vision</li>
 * </ul>
 */
@Service
public class AIService {

    private static final Logger logger = LoggerFactory.getLogger(AIService.class);

    @Value("${ai.api.key}")
    private String apiKey;

    @Value("${ai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    @Value("${ai.api.model:gpt-4-vision-preview}")
    private String model;

    @Value("${ai.api.timeout:30}")
    private int timeoutSeconds;

    private final WebClient webClient;

    public AIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    /**
     * Analiza una imagen usando IA y genera una descripción.
     * 
     * <p>Este método hace una llamada SÍNCRONA a la API de IA y espera la respuesta.</p>
     * 
     * @param imageUrl URL pública de la imagen a analizar
     * @return AIAnalysisResponse con la descripción generada
     * @throws AIServiceException si hay error al comunicarse con la API
     */
    public AIAnalysisResponse analyzeImage(String imageUrl) {
        logger.info("Iniciando análisis de imagen: {}", imageUrl);

        try {
            // Construir el request body para OpenAI
            Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(
                    Map.of(
                        "role", "user",
                        "content", List.of(
                            Map.of("type", "text", "text", "Describe esta imagen en español de forma detallada pero concisa (máximo 100 palabras)."),
                            Map.of("type", "image_url", "image_url", Map.of("url", imageUrl))
                        )
                    )
                ),
                "max_tokens", 300
            );

            // Hacer la llamada HTTP a la API de IA (SÍNCRONO)
            Map<String, Object> response = webClient.post()
                .uri(apiUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .block(); // ⏳ BLOQUEA hasta recibir respuesta (SÍNCRONO)

            // Extraer la descripción de la respuesta
            String description = extractDescription(response);
            
            logger.info("Análisis completado exitosamente para imagen: {}", imageUrl);
            return new AIAnalysisResponse(description);

        } catch (WebClientResponseException e) {
            logger.error("Error HTTP al llamar API de IA: {} - {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new AIServiceException("Error al comunicarse con el servicio de IA: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inesperado al analizar imagen: {}", e.getMessage(), e);
            throw new AIServiceException("Error al analizar la imagen: " + e.getMessage(), e);
        }
    }

    /**
     * Extrae la descripción del response de la API de IA.
     * 
     * @param response Response de la API
     * @return Descripción extraída
     */
    @SuppressWarnings("unchecked")
    private String extractDescription(Map<String, Object> response) {
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> firstChoice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                if (message != null) {
                    return (String) message.get("content");
                }
            }
            throw new AIServiceException("Formato de respuesta inesperado de la API de IA");
        } catch (ClassCastException e) {
            throw new AIServiceException("Error al parsear respuesta de la API de IA", e);
        }
    }

    /**
     * Verifica si la API de IA está disponible.
     * 
     * @return true si está configurada, false si no
     */
    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank() && 
               !apiKey.equals("your-api-key-here");
    }
}
