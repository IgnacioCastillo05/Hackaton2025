package edu.dosw.lab.Pixel_Scribe.Exceptions;

import edu.dosw.lab.Pixel_Scribe.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para toda la aplicación.
 * 
 * <p>Captura excepciones y las convierte en respuestas HTTP apropiadas con mensajes claros.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja excepciones de recurso no encontrado (404)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        logger.warn("Recurso no encontrado: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja excepciones de recurso ya existente (409)
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
        logger.warn("Recurso ya existe: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Maneja excepciones de credenciales inválidas (401)
     */
    @ExceptionHandler({InvalidCredentialsException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(RuntimeException ex) {
        logger.warn("Credenciales inválidas: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("Email o contraseña incorrectos", HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    /**
     * Maneja excepciones de validación de datos (400)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logger.warn("Errores de validación: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Maneja excepciones de archivo demasiado grande (413)
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
        logger.warn("Archivo demasiado grande: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
            "El archivo es demasiado grande. Tamaño máximo permitido: 10MB",
            HttpStatus.PAYLOAD_TOO_LARGE.value()
        );
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(error);
    }

    /**
     * Maneja excepciones de procesamiento de archivos (500)
     */
    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ErrorResponse> handleFileProcessing(FileProcessingException ex) {
        logger.error("Error procesando archivo: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Maneja excepciones del servicio de IA (503)
     */
    @ExceptionHandler(AIServiceException.class)
    public ResponseEntity<ErrorResponse> handleAIService(AIServiceException ex) {
        logger.error("Error en servicio de IA: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
            "Error al analizar la imagen. Por favor, intenta de nuevo.",
            HttpStatus.SERVICE_UNAVAILABLE.value()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    /**
     * Maneja cualquier otra excepción no capturada (500)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Error inesperado: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
            "Ha ocurrido un error inesperado. Por favor, contacta al administrador.",
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}



