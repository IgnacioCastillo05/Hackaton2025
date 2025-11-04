package edu.dosw.lab.Pixel_Scribe.Controller;


import edu.dosw.lab.pixelscribe.dto.AuthResponse;
import edu.dosw.lab.pixelscribe.dto.LoginRequest;
import edu.dosw.lab.pixelscribe.dto.RegisterRequest;
import edu.dosw.lab.pixelscribe.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para autenticación de usuarios.
 * 
 * <p>Endpoints disponibles:</p>
 * <ul>
 *   <li>POST /api/auth/register - Registrar nuevo usuario</li>
 *   <li>POST /api/auth/login - Iniciar sesión</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     * 
     * <p>Request body esperado:</p>
     * <pre>
     * {
     *   "email": "usuario@example.com",
     *   "password": "mipassword123"
     * }
     * </pre>
     * 
     * @param request Datos de registro
     * @return AuthResponse con token JWT y datos del usuario
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Solicitud de registro recibida para: {}", request.email());
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para iniciar sesión.
     * 
     * <p>Request body esperado:</p>
     * <pre>
     * {
     *   "email": "usuario@example.com",
     *   "password": "mipassword123"
     * }
     * </pre>
     * 
     * @param request Credenciales de login
     * @return AuthResponse con token JWT y datos del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Solicitud de login recibida para: {}", request.email());
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint de prueba para verificar que el servidor está funcionando.
     * 
     * @return Mensaje de saludo
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is running");
    }
}
