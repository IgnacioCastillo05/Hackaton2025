package edu.dosw.lab.Pixel_Scribe.integrations;

import java.time.Duration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Sends notifications to the configured n8n workflow.
 */
@Component
public class N8nWebhookClient {

    private static final Logger log = LoggerFactory.getLogger(N8nWebhookClient.class);

    private final WebClient webClient;
    private final String webhookUrl;

    public N8nWebhookClient(WebClient webClient,
                            @Value("${pixelscribe.integrations.n8n.webhook-url}") String webhookUrl) {
        this.webClient = webClient;
        this.webhookUrl = webhookUrl;
    }

    public void notifyImageUploaded(String objectKey) {
        webClient.post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("imageKey", objectKey))
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(10))
                .doOnError(error -> log.warn("n8n webhook respondió con error: {}", error.getMessage()))
                .onErrorResume(error -> {
                    // We do not want to break the upload flow when the webhook fails.
                    return reactor.core.publisher.Mono.empty();
                })
                .blockOptional();
    }
}



