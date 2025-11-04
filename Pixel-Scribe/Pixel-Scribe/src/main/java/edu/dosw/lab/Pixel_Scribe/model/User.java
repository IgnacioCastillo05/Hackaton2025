package edu.dosw.lab.Pixel_Scribe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * Entidad Usuario para autenticación y gestión de usuarios.
 * 
 * <p>Representa un usuario registrado en el sistema con capacidad de:</p>
 * <ul>
 *   <li>Autenticarse con email y contraseña</li>
 *   <li>Subir y gestionar sus propias imágenes</li>
 *   <li>Tener un rol específico (USER o ADMIN)</li>
 * </ul>
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_email", columnList = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    /**
     * Constructor vacío requerido por JPA
     */
    public User() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.role = Role.USER;
    }

    /**
     * Constructor para crear un nuevo usuario
     * 
     * @param email Email del usuario
     * @param passwordHash Contraseña hasheada (BCrypt)
     * @param role Rol del usuario
     */
    public User(String email, String passwordHash, Role role) {
        this();
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    /**
     * Callback ejecutado antes de actualizar la entidad
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRol() {
        return role;
    }

    public void setRol(Role role) {
        this.role = role;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", activo=" + activo +
                '}';
    }
}