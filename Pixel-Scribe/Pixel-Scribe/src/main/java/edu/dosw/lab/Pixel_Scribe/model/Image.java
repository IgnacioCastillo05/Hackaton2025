package edu.dosw.lab.Pixel_Scribe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

/**
 * Entidad Imagen que almacena información de imágenes subidas y analizadas por IA.
 * 
 * <p>Cada imagen contiene:</p>
 * <ul>
 *   <li>Título proporcionado por el usuario</li>
 *   <li>URL donde está almacenada la imagen</li>
 *   <li>Descripción generada por IA (después del análisis)</li>
 *   <li>Relación con el usuario propietario</li>
 * </ul>
 */
@Entity
@Table(name = "images", indexes = {
    @Index(name = "idx_image_user", columnList = "user_id"),
    @Index(name = "idx_image_created", columnList = "created_at")
})
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false, length = 255)
    private String title;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    @Column(nullable = false, length = 500)
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column
    private Instant analyzedAt;

    /**
     * Constructor vacío requerido por JPA
     */
    public Image() {
        this.createdAt = Instant.now();
    }

    /**
     * Constructor para crear una nueva imagen
     * 
     * @param title Título de la imagen
     * @param imageUrl URL donde está almacenada
     * @param user Usuario propietario
     */
    public Image(String title, String imageUrl, User user) {
        this();
        this.title = title;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    /**
     * Marca la imagen como analizada y guarda la descripción de IA
     * 
     * @param description Descripción generada por IA
     */
    public void setAnalysisResult(String description) {
        this.description = description;
        this.analyzedAt = Instant.now();
    }

    /**
     * Verifica si la imagen ya fue analizada por IA
     * 
     * @return true si ya tiene descripción, false si no
     */
    public boolean isAnalyzed() {
        return this.description != null && this.analyzedAt != null;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getAnalyzedAt() {
        return analyzedAt;
    }

    public void setAnalyzedAt(Instant analyzedAt) {
        this.analyzedAt = analyzedAt;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", analyzed=" + isAnalyzed() +
                ", userId=" + (user != null ? user.getId() : null) +
                '}';
    }
}






