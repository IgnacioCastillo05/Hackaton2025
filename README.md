# Hackaton 2025 - PixelScribe
Plataforma web para análisis de imágenes con IA usando Supabase S3 e integración con n8n.

## 📋 Descripción del Proyecto
PixelScribe es una aplicación completa que permite a los usuarios:
- 🔐 Registrarse e iniciar sesión con autenticación JWT
- 📤 Subir imágenes con almacenamiento en Supabase S3
- 🤖 Analizar imágenes automáticamente usando IA (OpenAI GPT-4 Vision/Gemini)
- 🖼️ Ver galería personal de imágenes con descripciones generadas
- 🔄 Integración con n8n para workflows avanzados

**Categoría:** Junior (Procesamiento síncrono)

---

## 🏗️ Arquitectura del Sistema

```
┌─────────────┐      ┌──────────────┐      ┌─────────────┐
│   Frontend  │─────▶│   Backend    │─────▶│  Supabase   │
│  (React/Vue)│◀─────│  Spring Boot │◀─────│     S3      │
└─────────────┘      └──────────────┘      └─────────────┘
                            │
                            ├─────▶ API IA (OpenAI/Gemini)
                            │
                            └─────▶ n8n Webhook (opcional)
                            
┌─────────────┐
│  SQL Server │◀──── JPA ───┐
│  Database   │              │
└─────────────┘              │
                    ┌────────▼────────┐
                    │  Spring Security │
                    │  + JWT Auth      │
                    └──────────────────┘
```

---

## 🚀 Tecnologías Utilizadas

### Backend
- **Framework:** Spring Boot 3.5.7
- **Java:** 21
- **Base de Datos:** Microsoft SQL Server (JPA)
- **Storage:** Supabase S3 (AWS SDK v2)
- **Seguridad:** Spring Security + JWT
- **API IA:** OpenAI GPT-4 Vision / Google Gemini
- **HTTP Client:** WebFlux (reactive)
- **Build:** Maven 3.9.11

### Integraciones
- **n8n:** Workflows y automatización
- **Supabase:** Storage S3-compatible
- **OpenAI/Gemini:** Análisis de imágenes con IA

---

## 📂 Estructura del Proyecto

```
src/main/java/edu/dosw/lab/pixelscribe/
├── config/
│   ├── AppConfig.java                      # PasswordEncoder, Clock
│   ├── CorsConfig.java                     # Configuración CORS
│   └── SupabaseStorageConfig.java          # Bean S3Client para Supabase
│
├── controller/
│   ├── AuthController.java                 # Endpoints autenticación (login/register)
│   ├── ImageController.java                # Endpoints imágenes (upload/list/delete)
│   └── WebhookController.java              # Webhooks n8n (opcional)
│
├── dto/
│   ├── AuthDTOs.java                       # LoginRequest, RegisterRequest, AuthResponse
│   ├── ImageDTOs.java                      # ImageUploadResponse, ImageListResponse
│   ├── ImageResponse.java                  # DTO API n8n
│   └── ImageDescriptionRequest.java        # DTO webhook n8n
│
├── exception/
│   ├── CustomExceptions.java               # Excepciones personalizadas
│   └── GlobalExceptionHandler.java         # Manejo centralizado de errores
│
├── model/
│   ├── Role.java                           # Enum roles (USER, ADMIN)
│   ├── User.java                           # Entidad Usuario (JPA)
│   ├── Image.java                          # Entidad Imagen (JPA)
│   └── ImageMetadata.java                  # Metadatos en memoria (n8n)
│
├── repository/
│   ├── UserRepository.java                 # JPA Repository usuarios
│   └── ImageRepository.java                # JPA Repository imágenes
│
├── security/
│   ├── SecurityConfig.java                 # Spring Security config
│   ├── JwtAuthFilter.java                  # Filtro JWT
│   ├── JwtTokenService.java               # Generación tokens
│   ├── JwtProperties.java                  # Propiedades JWT
│   └── UserPrincipal.java                  # UserDetails adapter
│
├── service/
│   ├── AuthService.java                    # Lógica autenticación
│   ├── ImageService.java                   # Upload y gestión imágenes
│   ├── AIService.java                      # Integración API IA
│   ├── SupabaseImageStorageService.java    # Upload a Supabase S3
│   └── ImageMetadataService.java           # Catálogo en memoria
│
├── storage/
│   ├── SupabaseStorageProperties.java      # Props S3
│   └── StoredImage.java                    # DTO respuesta
│
└── integrations/
    └── N8nWebhookClient.java               # Cliente HTTP n8n

src/main/resources/
├── application.yml                         # Configuración Spring Boot
└── static/                                 # Recursos estáticos

uploads/                                    # Imágenes locales (desarrollo)
.env                                        # Variables de entorno (NO subir a Git)
.env.example                                # Template variables de entorno
pom.xml                                     # Dependencias Maven
```

---

## 🎯 Endpoints de la API

### 🔐 Autenticación (`/api/auth`)

#### `POST /api/auth/register`
Registra un nuevo usuario en el sistema.

**Request:**
```json
{
  "email": "usuario@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 1,
    "email": "usuario@example.com",
    "role": "USER"
  }
}
```

#### `POST /api/auth/login`
Inicia sesión con credenciales existentes.

**Request:**
```json
{
  "email": "usuario@example.com",
  "password": "password123"
}
```

**Response:** (igual que register)

---

### 🖼️ Imágenes (`/api/images`) - Requiere autenticación

#### `POST /api/images/upload`
Sube una imagen y la analiza con IA de forma **SÍNCRONA**.

**Request:** (multipart/form-data)
- `file`: Archivo de imagen (JPG, PNG, GIF, WEBP)
- `title`: Título de la imagen

**Headers:**
```
Authorization: Bearer <token>
```

**Response:**
```json
{
  "id": 1,
  "title": "Mi primera imagen",
  "imageUrl": "https://dflbwhbdpghrmeenzjgg.storage.supabase.co/...",
  "description": "Una hermosa puesta de sol sobre el océano...",
  "analyzedAt": "2025-11-04T21:30:00Z",
  "createdAt": "2025-11-04T21:29:45Z"
}
```

**NOTA:** Este endpoint BLOQUEA hasta que la IA complete el análisis (5-10 segundos).

#### `GET /api/images`
Lista todas las imágenes del usuario autenticado.

**Headers:**
```
Authorization: Bearer <token>
```

**Response:**
```json
{
  "images": [
    {
      "id": 1,
      "title": "Mi primera imagen",
      "imageUrl": "https://...",
      "description": "Una hermosa puesta de sol...",
      "createdAt": "2025-11-04T21:29:45Z",
      "analyzedAt": "2025-11-04T21:30:00Z"
    }
  ],
  "total": 1
}
```

#### `DELETE /api/images/{id}`
Elimina una imagen del usuario.

**Headers:**
```
Authorization: Bearer <token>
```

**Response:** `204 No Content`

---

### 🔗 Webhooks n8n (`/api/webhooks`) - Público

#### `POST /api/webhooks/image-description`
Recibe descripciones de imágenes procesadas por n8n (opcional).

**Request:**
```json
{
  "imageId": "uuid-here",
  "description": "Descripción generada por n8n"
}
```

**Response:** `200 OK`

---

## ⚙️ Configuración y Ejecución

### 1. Requisitos Previos

- **Java:** 21 o superior
- **Maven:** 3.9.11 o superior
- **SQL Server:** Local o Azure
- **API Key:** OpenAI o Google Gemini
- **Supabase:** Cuenta y bucket S3 configurado (opcional)
- **n8n:** Instancia y webhook configurado (opcional)

### 2. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/pixel-scribe.git
cd pixel-scribe
```

### 3. Configurar Variables de Entorno

Crear un archivo `.env` en la raíz del proyecto:

```bash
cp .env.example .env
```

Editar `.env` con tus valores reales:

```env
# ===================================================================
# BASE DE DATOS - SQL SERVER
# ===================================================================
DB_HOST=localhost
DB_PORT=1433
DB_NAME=pixelscribe
DB_USERNAME=sa
DB_PASSWORD=TuPasswordSeguro123!

# ===================================================================
# JWT - SEGURIDAD
# ===================================================================
# Genera un secret seguro: echo -n "mi-secret-largo" | base64
JWT_SECRET=c2lyaGEtZGVmYXVsdC1zZWNyZXQta2V5LWZvci1kZXZlbG9wbWVudC1vbmx5

# ===================================================================
# API DE IA - OpenAI / Gemini
# ===================================================================
AI_API_KEY=sk-proj-tu-api-key-aqui
AI_API_URL=https://api.openai.com/v1/chat/completions
AI_MODEL=gpt-4-vision-preview

# O para Google Gemini:
# AI_API_KEY=AIzaSy-tu-api-key-aqui
# AI_API_URL=https://generativelanguage.googleapis.com/v1beta/models/gemini-pro-vision:generateContent
# AI_MODEL=gemini-pro-vision

# ===================================================================
# SUPABASE S3 (Opcional)
# ===================================================================
SUPABASE_BUCKET=pixel-scribe
SUPABASE_REGION=us-east-2
SUPABASE_ENDPOINT=https://dflbwhbdpghrmeenzjgg.storage.supabase.co/storage/v1/s3
SUPABASE_ACCESS_KEY_ID=tu-access-key
SUPABASE_SECRET_ACCESS_KEY=tu-secret-key

# ===================================================================
# N8N WEBHOOK (Opcional)
# ===================================================================
N8N_WEBHOOK_URL=https://magia.app.n8n.cloud/webhook/process-image

# ===================================================================
# APLICACIÓN
# ===================================================================
BASE_URL=http://localhost:8080
PORT=8080
ALLOWED_ORIGINS=http://localhost:3000,http://localhost:5173
SPRING_PROFILES_ACTIVE=dev
```

### 4. Configurar Base de Datos

#### Opción A: SQL Server con Docker

```bash
docker run -e "ACCEPT_EULA=Y" \
  -e "SA_PASSWORD=TuPasswordSeguro123!" \
  -p 1433:1433 \
  --name sqlserver \
  -d mcr.microsoft.com/mssql/server:2022-latest
```

#### Opción B: SQL Server en Azure

1. Crear Azure SQL Database
2. Obtener connection string
3. Actualizar variables en `.env`

**Crear base de datos:**
```sql
CREATE DATABASE pixelscribe;
```

**NOTA:** Las tablas se crean automáticamente con `spring.jpa.hibernate.ddl-auto=update`

### 5. Compilar el Proyecto

```bash
# Limpiar y compilar
./mvnw clean compile

# O con Maven instalado
mvn clean compile
```

### 6. Ejecutar la Aplicación

#### Linux/Mac:
```bash
./run.sh
```

#### Windows:
```bash
run.bat
```

#### O directamente con Maven:
```bash
./mvnw spring-boot:run -DskipTests

# Con perfil específico
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 🧪 Probar la API

### 1. Verificar que el servidor está corriendo

```bash
curl http://localhost:8080/api/auth/health
# Respuesta: "Auth service is running"
```

### 2. Registrar un usuario

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

### 3. Iniciar sesión

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

Guarda el `token` de la respuesta.

### 4. Subir una imagen

```bash
TOKEN="tu-token-aqui"

curl -X POST http://localhost:8080/api/images/upload \
  -H "Authorization: Bearer $TOKEN" \
  -F "file=@/ruta/a/tu/imagen.jpg" \
  -F "title=Mi primera imagen"
```

**NOTA:** Este comando tardará 5-10 segundos porque espera el análisis de IA.

### 5. Listar imágenes

```bash
curl -X GET http://localhost:8080/api/images \
  -H "Authorization: Bearer $TOKEN"
```

### 6. Eliminar una imagen

```bash
curl -X DELETE http://localhost:8080/api/images/1 \
  -H "Authorization: Bearer $TOKEN"
```

---

## 🔄 Flujo Completo del Sistema

### Flujo Principal (con IA directa)

```
1. Usuario se registra/login
   POST /api/auth/register → Recibe JWT token

2. Usuario sube imagen
   POST /api/images/upload (con token)
   ↓
3. Backend guarda imagen en Supabase S3
   ↓
4. Backend crea registro en BD (sin descripción)
   ↓
5. Backend llama API de IA (OpenAI/Gemini) ⏳ ESPERA
   ↓
6. IA retorna descripción
   ↓
7. Backend actualiza registro con descripción
   ↓
8. Backend retorna respuesta completa al frontend

9. Usuario ve imagen con descripción en galería
   GET /api/images → Lista todas sus imágenes
```

### Flujo Alternativo (con n8n)

```
1. Usuario sube imagen
   POST /api/images
   ↓
2. Backend guarda en Supabase S3
   ↓
3. Backend notifica a n8n
   Webhook: /webhook/process-image
   ↓
4. n8n procesa imagen con IA
   ↓
5. n8n envía callback
   POST /api/webhooks/image-description
   ↓
6. Backend actualiza BD con descripción
   ↓
7. Frontend consulta resultado
   GET /api/images/{id}
```

---

## 📊 Modelo de Datos

### Tabla: `users`
```sql
id              BIGINT PRIMARY KEY AUTO_INCREMENT
email           VARCHAR(255) UNIQUE NOT NULL
password_hash   VARCHAR(255) NOT NULL
role            VARCHAR(20) NOT NULL DEFAULT 'USER'
activo          BOOLEAN NOT NULL DEFAULT TRUE
created_at      DATETIME NOT NULL
updated_at      DATETIME
```

### Tabla: `images`
```sql
id              BIGINT PRIMARY KEY AUTO_INCREMENT
title           VARCHAR(255) NOT NULL
image_url       VARCHAR(500) NOT NULL
description     TEXT
user_id         BIGINT NOT NULL
created_at      DATETIME NOT NULL
analyzed_at     DATETIME

FOREIGN KEY (user_id) REFERENCES users(id)
INDEX idx_image_user (user_id)
INDEX idx_image_created (created_at)
```

---

## 🔐 Seguridad

### Características Implementadas

- ✅ **Contraseñas hasheadas:** BCrypt con salt
- ✅ **JWT Stateless:** Tokens con expiración configurable
- ✅ **Variables de entorno:** Credenciales NO hardcodeadas
- ✅ **CORS configurado:** Orígenes permitidos específicos
- ✅ **Validación de entrada:** Jakarta Validation en DTOs
- ✅ **Manejo de errores:** Respuestas apropiadas sin exponer detalles

### Endpoints Públicos (sin autenticación)

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/health`
- `POST /api/webhooks/image-description` (n8n)

### Endpoints Protegidos (requieren JWT)

- `POST /api/images/upload`
- `GET /api/images`
- `GET /api/images/{id}`
- `DELETE /api/images/{id}`

---

## 🐛 Troubleshooting

### Error: "Could not find or load main class"
```bash
mvn clean install -U
```

### Error: "Failed to configure a DataSource"
- Verifica que SQL Server esté corriendo
- Verifica credenciales en `.env`
- Verifica que la base de datos `pixelscribe` exista

### Error: "AI API key is invalid"
- Verifica que la API key sea válida
- Verifica que tengas saldo (OpenAI)
- Verifica que la URL sea correcta

### Error: "Access Denied" en endpoints
- Verifica que el token JWT esté en el header
- Formato: `Authorization: Bearer <token>`
- Verifica que el token no haya expirado

### La imagen no se analiza
- Verifica la configuración de `AI_API_KEY`
- Verifica logs del servidor para errores de IA
- Verifica que la imagen sea válida (JPG, PNG, GIF, WEBP)

---

## 🚀 Despliegue

### Backend (Railway)

1. Crear cuenta en https://railway.app
2. Crear nuevo proyecto desde GitHub
3. Agregar servicio de SQL Server
4. Configurar variables de entorno:
   ```
   DB_HOST=<railway-sql-host>
   DB_PORT=1433
   DB_NAME=pixelscribe
   DB_USERNAME=<railway-user>
   DB_PASSWORD=<railway-password>
   JWT_SECRET=<base64-secret>
   AI_API_KEY=<openai-key>
   BASE_URL=https://tu-app.railway.app
   ```
5. Deploy automático desde main branch

### Frontend (Vercel)

1. Crear cuenta en https://vercel.com
2. Importar repositorio de frontend
3. Configurar variable de entorno:
   ```
   VITE_API_URL=https://tu-backend.railway.app
   ```
4. Deploy automático

---

## 📈 Métricas y Monitoreo

### Actuator Endpoints (disponibles)

- `/actuator/health` - Estado de la aplicación
- `/actuator/info` - Información del build
- `/actuator/metrics` - Métricas de rendimiento

### Logs

Los logs se escriben en consola con niveles:
- `DEBUG` - Información detallada (desarrollo)
- `INFO` - Eventos importantes
- `WARN` - Advertencias
- `ERROR` - Errores

---

## 🔮 Próximos Pasos

### Para Producción
- [ ] Migrar de almacenamiento local a Supabase S3 completo
- [ ] Implementar procesamiento asíncrono con colas (RabbitMQ)
- [ ] Añadir WebSockets para notificaciones en tiempo real
- [ ] Implementar caché con Redis
- [ ] Agregar rate limiting
- [ ] Configurar CI/CD con GitHub Actions
- [ ] Agregar tests unitarios y de integración
- [ ] Implementar logging centralizado (ELK Stack)
- [ ] Configurar monitoring (Prometheus + Grafana)

### Funcionalidades Adicionales
- [ ] Búsqueda de imágenes por descripción
- [ ] Filtrado por tags generados por IA
- [ ] Compartir imágenes con otros usuarios
- [ ] Exportar galería completa
- [ ] Edición de descripciones
- [ ] Múltiples formatos de análisis (OCR, detección de objetos)

---

## 👥 Equipo

**Hackaton 2025 - Categoría Junior**

- [Nombre 1] - Backend + Integración Supabase/n8n
- [Nombre 2] - Backend + Autenticación + IA
- [Nombre 3] - Frontend
- [Nombre 4] - UX/UI

---

## 📄 Licencia

Este proyecto fue desarrollado para el Hackaton 2025.

---

## 🙏 Agradecimientos

- **Anthropic** - Claude AI para asistencia en desarrollo
- **OpenAI** - GPT-4 Vision API
- **Supabase** - Storage S3
- **n8n** - Workflow automation
- **Spring Boot** - Framework backend

---

## 📞 Contacto

Para dudas o sugerencias sobre el proyecto:
- Email: [tu-email@example.com]
- GitHub: [tu-usuario]

---

**¡Hecho con ❤️ en el Hackaton 2025!** 🚀






