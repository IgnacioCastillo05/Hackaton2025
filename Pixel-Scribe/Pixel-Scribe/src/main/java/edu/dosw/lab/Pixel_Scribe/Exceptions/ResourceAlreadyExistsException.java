package edu.dosw.lab.Pixel_Scribe.Exceptions;

/**
 * Excepción lanzada cuando hay un conflicto (ej: email ya existe).
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}



