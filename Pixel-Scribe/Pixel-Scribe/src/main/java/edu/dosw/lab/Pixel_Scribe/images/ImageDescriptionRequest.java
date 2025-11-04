package edu.dosw.lab.Pixel_Scribe.images;

import jakarta.validation.constraints.NotBlank;

/**
 * Payload delivered by n8n with the generated description.
 */
public record ImageDescriptionRequest(@NotBlank String imageKey,
                                      @NotBlank String description) {
}



