package edu.dosw.lab.Pixel_Scribe.Service;

import edu.dosw.lab.Pixel_Scribe.dto.AuthResponse;
import edu.dosw.lab.Pixel_Scribe.dto.LoginRequest;
import edu.dosw.lab.Pixel_Scribe.dto.RegisterRequest;
import edu.dosw.lab.Pixel_Scribe.dto.UserInfoDTO;
import edu.dosw.lab.Pixel_Scribe.Exceptions.InvalidCredentialsException;
import edu.dosw.lab.Pixel_Scribe.Exceptions.ResourceAlreadyExistsException;
import edu.dosw.lab.Pixel_Scribe.model.Role;
import edu.dosw.lab.Pixel_Scribe.model.User;
import edu.dosw.lab.Pixel_Scribe.Repository.UserRepository;
import edu.dosw.lab.Pixel_Scribe.security.JwtProperties;
import edu.dosw.lab.Pixel_Scribe.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de autenticación que maneja login y registro de usuarios.
 * 
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Registrar nuevos usuarios</li>
 *   <li>Validar credenciales en login</li>
 *   <li>Generar tokens JWT</li>
 * </ul>
 */
@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final JwtProperties jwtProperties;

    public AuthService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder,
                      JwtTokenService jwtTokenService,
                      JwtProperties jwtProperties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.jwtProperties = jwtProperties;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param request Datos de registro
     * @return Respuesta con token JWT y datos del usuario
     * @throws ResourceAlreadyExistsException si el email ya está registrado
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        logger.info("Intento de registro para email: {}", request.email());

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(request.email())) {
            logger.warn("Intento de registro con email existente: {}", request.email());
            throw new ResourceAlreadyExistsException("El email ya está registrado");
        }

        // Crear nuevo usuario
        User user = new User(
            request.email(),
            passwordEncoder.encode(request.password()),
            Role.USER
        );

        user = userRepository.save(user);
        logger.info("Usuario registrado exitosamente: {}", user.getEmail());

        // Generar token JWT
        String token = jwtTokenService.generateToken(new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPasswordHash(),
            java.util.Collections.emptyList()
        ));

        // Calcular expiración en segundos
        long expiresIn = jwtProperties.getExpirationMinutes() * 60L;

        UserInfoDTO userInfo = new UserInfoDTO(user.getId(), user.getEmail(), user.getRol().name());

        return new AuthResponse(token, expiresIn, userInfo);
    }

    /**
     * Autentica un usuario existente.
     * 
     * @param request Credenciales de login
     * @return Respuesta con token JWT y datos del usuario
     * @throws InvalidCredentialsException si las credenciales son inválidas
     */
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        logger.info("Intento de login para email: {}", request.email());

        // Buscar usuario por email
        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> {
                logger.warn("Intento de login con email no existente: {}", request.email());
                return new InvalidCredentialsException("Email o contraseña incorrectos");
            });

        // Verificar contraseña
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            logger.warn("Intento de login con contraseña incorrecta para: {}", request.email());
            throw new InvalidCredentialsException("Email o contraseña incorrectos");
        }

        // Verificar si el usuario está activo
        if (!user.isActivo()) {
            logger.warn("Intento de login con usuario inactivo: {}", request.email());
            throw new InvalidCredentialsException("Usuario inactivo");
        }

        logger.info("Login exitoso para: {}", user.getEmail());

        // Generar token JWT
        String token = jwtTokenService.generateToken(new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPasswordHash(),
            java.util.Collections.emptyList()
        ));

        // Calcular expiración en segundos
        long expiresIn = jwtProperties.getExpirationMinutes() * 60L;

        UserInfoDTO userInfo = new UserInfoDTO(user.getId(), user.getEmail(), user.getRol().name());

        return new AuthResponse(token, expiresIn, userInfo);
    }
}



