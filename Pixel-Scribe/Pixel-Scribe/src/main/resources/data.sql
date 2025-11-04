-- Script de inicialización de datos para pruebas
-- Se ejecuta automáticamente al iniciar la aplicación

-- Usuario de prueba
-- Email: test@pixelscribe.com
-- Password: Test123456
-- Password hash generado con BCrypt para "Test123456"
INSERT INTO users (id, email, password_hash, role, activo, created_at, updated_at) 
VALUES (
    1, 
    'test@pixelscribe.com', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 
    'USER', 
    true, 
    CURRENT_TIMESTAMP(), 
    CURRENT_TIMESTAMP()
);

-- Administrador de prueba
-- Email: admin@pixelscribe.com
-- Password: Admin123456
-- Password hash generado con BCrypt para "Admin123456"
INSERT INTO users (id, email, password_hash, role, activo, created_at, updated_at) 
VALUES (
    2, 
    'admin@pixelscribe.com', 
    '$2a$10$F1vM7vN5qcvK9xvX2YqZeOJ4jY6YZXqH5pZ3qZ7qZ8qZ9qZaqZbqZ', 
    'ADMIN', 
    true, 
    CURRENT_TIMESTAMP(), 
    CURRENT_TIMESTAMP()
);
