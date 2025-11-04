package edu.dosw.lab.Pixel_Scribe.Repository;

import edu.dosw.lab.Pixel_Scribe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para operaciones de base de datos sobre usuarios.
 * 
 * <p>Proporciona métodos para:</p>
 * <ul>
 *   <li>Buscar usuarios por email</li>
 *   <li>Verificar existencia de usuarios</li>
 *   <li>Operaciones CRUD básicas heredadas de JpaRepository</li>
 * </ul>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su email.
     * 
     * @param email Email del usuario
     * @return Optional con el usuario si existe, Optional.empty() si no
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica si existe un usuario con el email dado.
     * 
     * @param email Email a verificar
     * @return true si existe, false si no
     */
    boolean existsByEmail(String email);
}
