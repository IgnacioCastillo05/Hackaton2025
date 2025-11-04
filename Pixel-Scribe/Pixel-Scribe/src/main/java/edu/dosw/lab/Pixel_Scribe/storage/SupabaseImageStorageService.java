package edu.dosw.lab.Pixel_Scribe.storage;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * Handles uploads to the Supabase S3-compatible bucket.
 */
@Service
public class SupabaseImageStorageService {

    private static final Logger log = LoggerFactory.getLogger(SupabaseImageStorageService.class);

    private final S3Client s3Client;
    private final SupabaseStorageProperties properties;

    public SupabaseImageStorageService(S3Client s3Client, SupabaseStorageProperties properties) {
        this.s3Client = s3Client;
        this.properties = properties;
    }

    public StoredImage uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo subido está vacío");
        }

        String originalFilename = file.getOriginalFilename() != null
                ? file.getOriginalFilename()
                : "image";
        String objectKey = buildObjectKey(originalFilename);

    String contentType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";

    PutObjectRequest request = PutObjectRequest.builder()
                .bucket(properties.getBucket())
                .key(objectKey)
        .contentType(contentType)
                .build();

        try {
            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException ioException) {
            log.error("No se pudo leer el archivo para subir a Supabase", ioException);
            throw new IllegalStateException("Error al subir la imagen", ioException);
        }

        String publicUrl = buildPublicUrl(objectKey);
    return new StoredImage(objectKey, file.getSize(), contentType, originalFilename, publicUrl);
    }

    private String buildObjectKey(String originalFilename) {
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > -1 && dotIndex < originalFilename.length() - 1) {
            extension = originalFilename.substring(dotIndex).toLowerCase(Locale.ROOT);
        }
        return "images/" + UUID.randomUUID() + extension;
    }

    private String buildPublicUrl(String objectKey) {
        String baseEndpoint = properties.getEndpoint();
        String publicBase = baseEndpoint.endsWith("/s3")
                ? baseEndpoint.substring(0, baseEndpoint.length() - 3) + "object/public"
                : baseEndpoint + "/object/public";
        return publicBase + "/" + properties.getBucket() + "/" + urlEncodePath(objectKey);
    }

    private String urlEncodePath(String objectKey) {
        String[] segments = objectKey.split("/");
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < segments.length; i++) {
            if (i > 0) {
                encoded.append('/');
            }
            encoded.append(URLEncoder.encode(segments[i], StandardCharsets.UTF_8).replace("+", "%20"));
        }
        return encoded.toString();
    }
}
