package edu.dosw.lab.Pixel_Scribe.security;

/**
 * Propiedades de configuración para JWT (JSON Web Token).
 * 
 * <p>Lee las propiedades del archivo application.properties con el prefijo
 * "pixelscribe.security.jwt".</p>
 * 
 * <p>Propiedades disponibles:</p>
 * <ul>
 *   <li>issuer: Identificador del emisor del token (default: "pixelscribe")</li>
 *   <li>expirationMinutes: Tiempo de vida del token en minutos (default: 60)</li>
 *   <li>secret: Clave secreta para firmar tokens (debe estar en variables de entorno)</li>
 * </ul>
 * 
 * <p>Ejemplo de configuración en application.properties:</p>
 * <pre>
 * pixelscribe.security.jwt.issuer=pixelscribe
 * pixelscribe.security.jwt.expirationMinutes=60
 * pixelscribe.security.jwt.secret=${JWT_SECRET}
 * </pre>
 * 
 * @see JwtTokenService
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "pixelscribe.security.jwt")
public class JwtProperties {

    /**
     * Identificador del emisor del token JWT.
     * Aparece en el claim "iss" del token.
     */
    private String issuer = "pixelscribe";
    
    /**
     * Tiempo de vida del token en minutos.
     * Después de este tiempo, el token expira y debe renovarse.
     */
    private int expirationMinutes = 60;
    
    /**
     * Clave secreta para firmar y validar tokens JWT.
     * DEBE configurarse mediante variable de entorno por seguridad.
     */
    private String secret;
}
