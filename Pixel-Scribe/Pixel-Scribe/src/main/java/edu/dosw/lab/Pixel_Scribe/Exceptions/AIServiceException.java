package edu.dosw.lab.Pixel_Scribe.Exceptions;

/**
 * Excepción lanzada cuando hay un error con la API de IA.
 */
public class AIServiceException extends RuntimeException {
    public AIServiceException(String message) {
        super(message);
    }
    
    public AIServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}



