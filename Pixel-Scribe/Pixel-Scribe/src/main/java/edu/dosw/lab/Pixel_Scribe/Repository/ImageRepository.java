package edu.dosw.lab.Pixel_Scribe.Repository;

import edu.dosw.lab.Pixel_Scribe.model.Image;
import edu.dosw.lab.Pixel_Scribe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para operaciones de base de datos sobre imágenes.
 * 
 * <p>Proporciona métodos para:</p>
 * <ul>
 *   <li>Buscar imágenes por usuario</li>
 *   <li>Listar imágenes ordenadas por fecha</li>
 *   <li>Operaciones CRUD básicas heredadas de JpaRepository</li>
 * </ul>
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * Busca todas las imágenes de un usuario específico, ordenadas por fecha de creación descendente.
     * 
     * @param user Usuario propietario
     * @return Lista de imágenes del usuario (más recientes primero)
     */
    List<Image> findByUserOrderByCreatedAtDesc(User user);

    /**
     * Busca todas las imágenes de un usuario por su ID, ordenadas por fecha de creación descendente.
     * 
     * @param userId ID del usuario propietario
     * @return Lista de imágenes del usuario (más recientes primero)
     */
    List<Image> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Cuenta cuántas imágenes tiene un usuario.
     * 
     * @param user Usuario propietario
     * @return Número de imágenes del usuario
     */
    long countByUser(User user);
}










