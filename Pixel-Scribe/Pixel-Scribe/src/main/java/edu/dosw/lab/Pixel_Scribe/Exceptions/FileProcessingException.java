package edu.dosw.lab.Pixel_Scribe.Exceptions;

/**
 * Excepción lanzada cuando hay un error al procesar un archivo.
 */
public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message) {
        super(message);
    }
    
    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
