package edu.dosw.lab.Pixel_Scribe.images;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/**
 * Simple in-memory catalog for uploaded images.
 */
@Service
public class ImageMetadataService {

    private final ConcurrentHashMap<UUID, ImageMetadata> images = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, UUID> keyIndex = new ConcurrentHashMap<>();

    public ImageMetadata save(ImageMetadata metadata) {
        images.put(metadata.getId(), metadata);
        keyIndex.put(metadata.getObjectKey(), metadata.getId());
        return metadata;
    }

    public List<ImageMetadata> findAll() {
        List<ImageMetadata> snapshot = new ArrayList<>(images.values());
        snapshot.sort((left, right) -> right.getUploadedAt().compareTo(left.getUploadedAt()));
        return snapshot;
    }

    public Optional<ImageMetadata> findById(UUID id) {
        return Optional.ofNullable(images.get(id));
    }

    public Optional<ImageMetadata> findByKey(String objectKey) {
        UUID id = keyIndex.get(objectKey);
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(images.get(id));
    }

    public Optional<ImageMetadata> updateDescription(String objectKey, String description) {
        UUID id = keyIndex.get(objectKey);
        if (id == null) {
            return Optional.empty();
        }
        ImageMetadata metadata = images.get(id);
        if (metadata == null) {
            return Optional.empty();
        }
        metadata.updateDescription(description);
        return Optional.of(metadata);
    }
}
