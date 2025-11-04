package edu.dosw.lab.Pixel_Scribe.images;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Minimal in-memory representation of a stored image.
 */
public final class ImageMetadata {

    private final UUID id;
    private final String originalFilename;
    private final String objectKey;
    private final long size;
    private final String contentType;
    private final Instant uploadedAt;
    private final String publicUrl;
    private String description;

    public ImageMetadata(UUID id,
                         String originalFilename,
                         String objectKey,
                         long size,
                         String contentType,
                         Instant uploadedAt,
                         String publicUrl,
                         String description) {
        this.id = Objects.requireNonNull(id, "id");
        this.originalFilename = Objects.requireNonNull(originalFilename, "originalFilename");
        this.objectKey = Objects.requireNonNull(objectKey, "objectKey");
        this.size = size;
        this.contentType = contentType;
        this.uploadedAt = Objects.requireNonNull(uploadedAt, "uploadedAt");
        this.publicUrl = Objects.requireNonNull(publicUrl, "publicUrl");
        this.description = description;
    }

    public static ImageMetadata create(UUID id,
                                       String originalFilename,
                                       String objectKey,
                                       long size,
                                       String contentType,
                                       Instant uploadedAt,
                                       String publicUrl) {
        return new ImageMetadata(id, originalFilename, objectKey, size, contentType, uploadedAt, publicUrl, null);
    }

    public UUID getId() {
        return id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public long getSize() {
        return size;
    }

    public String getContentType() {
        return contentType;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public String getDescription() {
        return description;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
