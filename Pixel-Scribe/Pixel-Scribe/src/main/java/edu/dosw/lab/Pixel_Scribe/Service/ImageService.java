package edu.dosw.lab.Pixel_Scribe.Service;


import edu.dosw.lab.Pixel_Scribe.dto.AIAnalysisResponse;
import edu.dosw.lab.Pixel_Scribe.dto.ImageListItemDTO;
import edu.dosw.lab.Pixel_Scribe.dto.ImageListResponse;
import edu.dosw.lab.Pixel_Scribe.dto.ImageUploadResponse;
import edu.dosw.lab.Pixel_Scribe.Exceptions.FileProcessingException;
import edu.dosw.lab.Pixel_Scribe.Exceptions.ResourceNotFoundException;
import edu.dosw.lab.Pixel_Scribe.model.Image;
import edu.dosw.lab.Pixel_Scribe.model.User;
import edu.dosw.lab.Pixel_Scribe.Repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar subida y análisis de imágenes.
 * 
 * <p>Flujo SÍNCRONO (Categoría Junior):</p>
 * <ol>
 *   <li>Guardar archivo en disco</li>
 *   <li>Crear registro en BD sin descripción</li>
 *   <li>Llamar a IA para analizar (ESPERA aquí) ⏳</li>
 *   <li>Actualizar registro con descripción</li>
 *   <li>Retornar resultado completo</li>
 * </ol>
 */
@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.base.url:http://localhost:8080}")
    private String baseUrl;

    private final ImageRepository imageRepository;
    private final AIService aiService;

    public ImageService(ImageRepository imageRepository, AIService aiService) {
        this.imageRepository = imageRepository;
        this.aiService = aiService;
    }

    /**
     * Procesa la subida de una imagen de forma SÍNCRONA.
     * 
     * <p>IMPORTANTE: Este método BLOQUEA hasta que la IA termine de analizar la imagen.</p>
     * 
     * @param file Archivo de imagen subido
     * @param title Título de la imagen
     * @param user Usuario propietario
     * @return ImageUploadResponse con la imagen y su descripción
     * @throws FileProcessingException si hay error al procesar el archivo
     */
    @Transactional
    public ImageUploadResponse uploadAndAnalyze(MultipartFile file, String title, User user) {
        logger.info("Procesando subida de imagen '{}' para usuario {}", title, user.getEmail());

        // 1. Validar archivo
        validateFile(file);

        // 2. Guardar archivo en disco
        String imageUrl = saveFile(file);
        logger.info("Archivo guardado en: {}", imageUrl);

        // 3. Crear registro en BD (sin descripción todavía)
        Image image = new Image(title, imageUrl, user);
        image = imageRepository.save(image);
        logger.info("Registro de imagen creado con ID: {}", image.getId());

        // 4. Analizar con IA de forma SÍNCRONA ⏳ (ESPERA AQUÍ)
        try {
            logger.info("Iniciando análisis de IA para imagen {}", image.getId());
            AIAnalysisResponse analysis = aiService.analyzeImage(imageUrl);
            
            if (analysis.success()) {
                // 5. Actualizar registro con descripción
                image.setAnalysisResult(analysis.description());
                image = imageRepository.save(image);
                logger.info("Análisis completado para imagen {}: {}", image.getId(), analysis.description());
            } else {
                logger.warn("Análisis de IA falló para imagen {}: {}", image.getId(), analysis.error());
                image.setDescription("No se pudo analizar la imagen");
                image = imageRepository.save(image);
            }
        } catch (Exception e) {
            logger.error("Error durante análisis de IA para imagen {}", image.getId(), e);
            // Guardar con descripción de error
            image.setDescription("Error al analizar la imagen");
            image = imageRepository.save(image);
        }

        // 6. Retornar respuesta completa
        return new ImageUploadResponse(
            image.getId(),
            image.getTitle(),
            image.getImageUrl(),
            image.getDescription(),
            image.getCreatedAt()
        );
    }

    /**
     * Obtiene todas las imágenes de un usuario.
     * 
     * @param user Usuario propietario
     * @return ImageListResponse con lista de imágenes
     */
    @Transactional(readOnly = true)
    public ImageListResponse getUserImages(User user) {
        logger.info("Obteniendo imágenes para usuario {}", user.getEmail());
        
        List<Image> images = imageRepository.findByUserOrderByCreatedAtDesc(user);
        
        List<ImageListItemDTO> imageDTOs = images.stream()
            .map(img -> new ImageListItemDTO(
                img.getId(),
                img.getTitle(),
                img.getImageUrl(),
                null, // thumbnailUrl - no implementado aún
                img.getDescription(),
                img.getCreatedAt()
            ))
            .collect(Collectors.toList());

        return new ImageListResponse(imageDTOs, imageDTOs.size());
    }

    /**
     * Obtiene una imagen por su ID.
     * 
     * @param imageId ID de la imagen
     * @param user Usuario que solicita (debe ser el propietario)
     * @return Image encontrada
     * @throws ResourceNotFoundException si no existe o no pertenece al usuario
     */
    @Transactional(readOnly = true)
    public Image getImageById(Long imageId, User user) {
        Image image = imageRepository.findById(imageId)
            .orElseThrow(() -> new ResourceNotFoundException("Imagen no encontrada"));

        // Verificar que la imagen pertenezca al usuario
        if (!image.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Imagen no encontrada");
        }

        return image;
    }

    /**
     * Elimina una imagen.
     * 
     * @param imageId ID de la imagen
     * @param user Usuario que solicita (debe ser el propietario)
     * @throws ResourceNotFoundException si no existe o no pertenece al usuario
     */
    @Transactional
    public void deleteImage(Long imageId, User user) {
        Image image = getImageById(imageId, user);
        
        // Eliminar archivo físico
        try {
            Path filePath = Paths.get(image.getImageUrl().replace(baseUrl + "/", ""));
            Files.deleteIfExists(filePath);
            logger.info("Archivo eliminado: {}", filePath);
        } catch (IOException e) {
            logger.warn("No se pudo eliminar archivo físico: {}", e.getMessage());
        }

        // Eliminar registro de BD
        imageRepository.delete(image);
        logger.info("Imagen {} eliminada exitosamente", imageId);
    }

    /**
     * Valida que el archivo sea una imagen válida.
     * 
     * @param file Archivo a validar
     * @throws FileProcessingException si el archivo es inválido
     */
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileProcessingException("El archivo está vacío");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new FileProcessingException("El archivo debe ser una imagen");
        }

        // Validar extensión
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !isValidImageExtension(originalFilename)) {
            throw new FileProcessingException("Tipo de archivo no permitido");
        }
    }

    /**
     * Verifica si la extensión del archivo es válida.
     * 
     * @param filename Nombre del archivo
     * @return true si es válida, false si no
     */
    private boolean isValidImageExtension(String filename) {
        String[] validExtensions = {".jpg", ".jpeg", ".png", ".gif", ".webp"};
        String lowerFilename = filename.toLowerCase();
        for (String ext : validExtensions) {
            if (lowerFilename.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Guarda el archivo en disco y retorna su URL.
     * 
     * @param file Archivo a guardar
     * @return URL pública del archivo
     * @throws FileProcessingException si hay error al guardar
     */
    private String saveFile(MultipartFile file) {
        try {
            // Crear directorio si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar nombre único para el archivo
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? 
                originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
            String filename = UUID.randomUUID().toString() + extension;

            // Guardar archivo
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Retornar URL pública
            return baseUrl + "/" + uploadDir + "/" + filename;

        } catch (IOException e) {
            logger.error("Error al guardar archivo: {}", e.getMessage(), e);
            throw new FileProcessingException("Error al guardar el archivo", e);
        }
    }
}


