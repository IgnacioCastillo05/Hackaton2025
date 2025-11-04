# Hackaton 2025 - PixelScribe

Plataforma web para análisis de imágenes con IA usando Supabase S3 e integración con n8n.

## Proyecto: Pixel-Scribe Backend

El backend está construido en **Java 17** con **Spring Boot 3.5.7** y proporciona los siguientes endpoints:

### Endpoints principales

- `POST /api/images` - Subir una imagen a Supabase S3 y disparar análisis en n8n
- `GET /api/images` - Listar todas las imágenes almacenadas
- `GET /api/images/{id}` - Obtener detalles de una imagen específica
- `POST /api/webhooks/image-description` - Webhook para recibir descripciones desde n8n

### Flujo de funcionamiento

1. **Upload**: El cliente envía una imagen (multipart) a `/api/images`
2. **Storage**: La imagen se sube a Supabase S3 con un nombre único
3. **Webhook n8n**: Se notifica a n8n del nuevo archivo (`/webhook/process-image`)
4. **IA Analysis**: n8n procesa la imagen y genera una descripción
5. **Callback**: n8n envía la descripción a `/api/webhooks/image-description`
6. **Response**: El frontend puede recuperar la imagen con su descripción via `/api/images/{id}`

### Configuración y ejecución

#### Variables de entorno requeridas

Crear un archivo `.env` en la raíz del proyecto `Pixel-Scribe/Pixel-Scribe/`:

```env
SUPABASE_BUCKET=pixel-scribe
SUPABASE_REGION=us-east-2
SUPABASE_ENDPOINT=https://dflbwhbdpghrmeenzjgg.storage.supabase.co/storage/v1/s3
SUPABASE_ACCESS_KEY_ID=ef7bdcd3123223d26728171e657725e4
SUPABASE_SECRET_ACCESS_KEY=a33edb6e788dd288d71d4f60f1e4fd703c265f460ff4750168e12624aa17f5ec
N8N_WEBHOOK_URL=https://magia.app.n8n.cloud/webhook/process-image
```

#### Compilar y ejecutar

```bash
cd Pixel-Scribe/Pixel-Scribe

# Compilar
./mvnw clean compile

# Ejecutar (Linux/Mac)
./run.sh

# Ejecutar (Windows)
run.bat

# O directo con Maven
./mvnw spring-boot:run -DskipTests
```

### Estructura del proyecto

```
src/main/java/edu/dosw/lab/Pixel_Scribe/
├── storage/
│   ├── SupabaseStorageProperties.java    # Configuración S3
│   ├── SupabaseStorageConfig.java         # Bean S3Client
│   ├── SupabaseImageStorageService.java   # Servicio de upload
│   └── StoredImage.java                   # DTO de respuesta
├── images/
│   ├── ImageController.java               # REST endpoints
│   ├── ImageMetadata.java                 # Entidad en memoria
│   ├── ImageMetadataService.java          # Catálogo en memoria
│   ├── ImageResponse.java                 # DTO API
│   └── ImageDescriptionRequest.java       # DTO webhook
├── integrations/
│   └── N8nWebhookClient.java              # Cliente HTTP para n8n
└── security/
    ├── SecurityConfig.java                # Spring Security
    ├── JwtAuthFilter.java                 # Filtro JWT
    ├── JwtTokenService.java               # Generación tokens
    ├── JwtProperties.java                 # Config JWT
    └── UserPrincipal.java                 # UserDetails adapter
```

### Stack tecnológico

- **Framework**: Spring Boot 3.5.7
- **Java**: 17
- **Storage**: Supabase (S3-compatible)
- **SDK**: AWS SDK v2
- **Seguridad**: JWT + Spring Security
- **HTTP**: WebFlux (reactive client)
- **Build**: Maven 3.9.11

### Notas de seguridad

- ✅ Todas las credenciales están en variables de entorno (no hardcodeadas)
- ✅ Usa JWT para stateless authentication
- ✅ Spring Security protege endpoints excepto los públicos configurados
- ✅ Validación de entrada en DTOs con Jakarta Validation

### Próximos pasos (para producción)

- Integrar con base de datos real (PostgreSQL)
- Implementar procesamiento asíncrono con colas (RabbitMQ/Kafka)
- Añadir WebSockets para notificaciones en tiempo real
- Implementar almacenamiento persistente de metadatos
- Agregar caché (Redis) para búsquedas frecuentes
- Configurar CI/CD con GitHub Actions
- Desplegar en Railway, Vercel o similar
