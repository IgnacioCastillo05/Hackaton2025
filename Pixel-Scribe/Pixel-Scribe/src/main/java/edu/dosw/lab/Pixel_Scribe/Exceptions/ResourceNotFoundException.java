package edu.dosw.lab.Pixel_Scribe.Exceptions;

/**
 * Excepción lanzada cuando un recurso solicitado no existe en la base de datos.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}



