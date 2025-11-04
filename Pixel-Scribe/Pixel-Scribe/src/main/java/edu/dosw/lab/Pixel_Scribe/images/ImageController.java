package edu.dosw.lab.Pixel_Scribe.images;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import edu.dosw.lab.Pixel_Scribe.integrations.N8nWebhookClient;
import edu.dosw.lab.Pixel_Scribe.storage.StoredImage;
import edu.dosw.lab.Pixel_Scribe.storage.SupabaseImageStorageService;
import jakarta.validation.Valid;

/**
 * REST API for uploading images and receiving the n8n description.
 */
@RestController
@RequestMapping("/api")
@Validated
public class ImageController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    private final SupabaseImageStorageService storageService;
    private final ImageMetadataService metadataService;
    private final N8nWebhookClient n8nWebhookClient;

    public ImageController(SupabaseImageStorageService storageService,
                           ImageMetadataService metadataService,
                           N8nWebhookClient n8nWebhookClient) {
        this.storageService = storageService;
        this.metadataService = metadataService;
        this.n8nWebhookClient = n8nWebhookClient;
    }

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        StoredImage storedImage = storageService.uploadImage(file);
        ImageMetadata metadata = ImageMetadata.create(
                UUID.randomUUID(),
                storedImage.originalFilename(),
                storedImage.objectKey(),
                storedImage.size(),
                storedImage.contentType(),
                Instant.now(),
                storedImage.publicUrl());

        metadataService.save(metadata);
        try {
            n8nWebhookClient.notifyImageUploaded(storedImage.objectKey());
        } catch (Exception ex) {
            log.warn("El webhook de n8n falló, pero la imagen quedó almacenada: {}", ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ImageResponse.from(metadata));
    }

    @GetMapping("/images")
    public List<ImageResponse> listImages() {
        return metadataService.findAll().stream()
                .map(ImageResponse::from)
                .toList();
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<ImageResponse> getImage(@PathVariable UUID id) {
        return metadataService.findById(id)
                .map(ImageResponse::from)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagen no encontrada"));
    }

    @PostMapping(path = "/webhooks/image-description", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> receiveDescription(@Valid @RequestBody ImageDescriptionRequest request) {
        boolean updated = metadataService.updateDescription(request.imageKey(), request.description()).isPresent();
        if (!updated) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagen no encontrada para actualizar la descripción");
        }
        return ResponseEntity.accepted().build();
    }
}
