package edu.dosw.lab.Pixel_Scribe.Controller;


import edu.dosw.lab.pixelscribe.dto.ImageListResponse;
import edu.dosw.lab.pixelscribe.dto.ImageUploadResponse;
import edu.dosw.lab.pixelscribe.model.User;
import edu.dosw.lab.pixelscribe.security.UserPrincipal;
import edu.dosw.lab.pixelscribe.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador REST para gestión de imágenes.
 * 
 * <p>Endpoints disponibles (requieren autenticación):</p>
 * <ul>
 *   <li>POST /api/images/upload - Subir y analizar imagen</li>
 *   <li>GET /api/images - Listar imágenes del usuario</li>
 *   <li>DELETE /api/images/{id} - Eliminar imagen</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Endpoint para subir y analizar una imagen de forma SÍNCRONA.
     * 
     * <p>IMPORTANTE: Este endpoint BLOQUEA hasta que la IA termine el análisis.</p>
     * <p>El cliente debe mostrar un spinner/loading durante la espera.</p>
     * 
     * <p>Ejemplo de request (multipart/form-data):</p>
     * <ul>
     *   <li>file: [archivo de imagen]</li>
     *   <li>title: "Mi primera imagen"</li>
     * </ul>
     * 
     * @param file Archivo de imagen (jpg, png, gif, webp)
     * @param title Título de la imagen
     * @param userPrincipal Usuario autenticado
     * @return ImageUploadResponse con la imagen y su descripción generada por IA
     */
    @PostMapping("/upload")
    public ResponseEntity<ImageUploadResponse> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        
        User user = userPrincipal.getUser();
        logger.info("Usuario {} subiendo imagen: {}", user.getEmail(), title);

        // Este método BLOQUEA hasta que la IA termine ⏳
        ImageUploadResponse response = imageService.uploadAndAnalyze(file, title, user);
        
        logger.info("Imagen {} subida y analizada exitosamente", response.id());
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para obtener todas las imágenes del usuario autenticado.
     * 
     * @param userPrincipal Usuario autenticado
     * @return ImageListResponse con lista de imágenes
     */
    @GetMapping
    public ResponseEntity<ImageListResponse> getUserImages(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        
        User user = userPrincipal.getUser();
        logger.info("Usuario {} solicitando sus imágenes", user.getEmail());

        ImageListResponse response = imageService.getUserImages(user);
        
        logger.info("Retornando {} imágenes para usuario {}", response.total(), user.getEmail());
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para eliminar una imagen del usuario.
     * 
     * @param imageId ID de la imagen a eliminar
     * @param userPrincipal Usuario autenticado
     * @return ResponseEntity sin contenido (204 No Content)
     */
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long imageId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        
        User user = userPrincipal.getUser();
        logger.info("Usuario {} eliminando imagen {}", user.getEmail(), imageId);

        imageService.deleteImage(imageId, user);
        
        logger.info("Imagen {} eliminada exitosamente", imageId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint de prueba para verificar que el servicio de imágenes está funcionando.
     * 
     * @return Mensaje de saludo
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Image service is running");
    }
}
