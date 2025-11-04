package edu.dosw.lab.Pixel_Scribe.images;

import java.time.Instant;
import java.util.UUID;

/**
 * Serializable DTO for API responses.
 */
public record ImageResponse(UUID id,
                            String objectKey,
                            String originalFilename,
                            long size,
                            String contentType,
                            Instant uploadedAt,
                            String publicUrl,
                            String description) {

    public static ImageResponse from(ImageMetadata metadata) {
        return new ImageResponse(
                metadata.getId(),
                metadata.getObjectKey(),
                metadata.getOriginalFilename(),
                metadata.getSize(),
                metadata.getContentType(),
                metadata.getUploadedAt(),
                metadata.getPublicUrl(),
                metadata.getDescription());
    }
}



