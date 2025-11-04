package edu.dosw.lab.Pixel_Scribe.Exceptions;

/**
 * Excepción lanzada cuando las credenciales son inválidas.
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}



