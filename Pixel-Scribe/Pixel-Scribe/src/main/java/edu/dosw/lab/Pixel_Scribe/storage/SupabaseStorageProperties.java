package edu.dosw.lab.Pixel_Scribe.storage;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration holder for the Supabase S3-compatible storage endpoint.
 *
 * <p>The credentials must be supplied via environment variables to avoid
 * hardcoding secrets in the code base.</p>
 */
@Validated
@ConfigurationProperties(prefix = "pixelscribe.supabase.storage")
public class SupabaseStorageProperties {

    /** Bucket name inside Supabase storage. */
    @NotBlank
    private String bucket;

    /** AWS-compatible region (required by the SDK even if Supabase ignores it). */
    @NotBlank
    private String region;

    /** Fully qualified Supabase S3 endpoint URL. */
    @NotBlank
    private String endpoint;

    /** Supabase access key id. */
    @NotBlank
    private String accessKeyId;

    /** Supabase secret access key. */
    @NotBlank
    private String secretAccessKey;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }
}



