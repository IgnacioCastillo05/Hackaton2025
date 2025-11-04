package edu.dosw.lab.Pixel_Scribe.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;

/**
 * Configuración de beans básicos de la aplicación.
 */
@Configuration
public class AppConfig {

    /**
     * Bean para codificar contraseñas con BCrypt.
     * 
     * @return PasswordEncoder configurado con BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para Clock (útil para testing y manejo de tiempo).
     * 
     * @return Clock del sistema con zona UTC
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
