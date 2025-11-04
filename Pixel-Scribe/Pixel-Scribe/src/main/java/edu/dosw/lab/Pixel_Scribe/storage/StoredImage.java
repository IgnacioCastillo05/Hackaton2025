package edu.dosw.lab.Pixel_Scribe.storage;

/**
 * Value object returned after uploading to Supabase storage.
 */
public record StoredImage(String objectKey,
                          long size,
                          String contentType,
                          String originalFilename,
                          String publicUrl) {
}
