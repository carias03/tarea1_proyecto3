# Tarea 1 — Proyecto 3

API REST de autenticación con JWT y CRUD de Productos y Categorías, construida con Spring Boot 3.2.5 y PostgreSQL.

---

## Requisitos previos

- **Java 21** (JDK)
- **Maven 3.9+** (o usar el IDE con Maven integrado)
- **PostgreSQL 16** (local o via Docker)
- **Postman** (para importar la colección de pruebas)

---

## Configuración de la base de datos

### Opción A — Con Docker (recomendado)

El proyecto incluye un `docker-compose.yml` que levanta PostgreSQL automáticamente:

```bash
docker-compose up -d
```

Esto crea un contenedor con PostgreSQL 16 en el puerto `5432`, usuario `postgres`, password `postgres` y base de datos `postgres`.

### Opción B — PostgreSQL local

Si ya tiene PostgreSQL instalado, verifique que exista la base de datos `postgres` y que el usuario y contraseña coincidan con los del archivo `application.properties`.

---

## Configuración del proyecto

1. **Clonar el repositorio:**

```bash
git clone https://github.com/carias03/tarea1_proyecto3.git
cd tarea1_proyecto3
```

2. **Verificar la configuración de base de datos** en `src/main/resources/application.properties`. Los valores por defecto son:

| Propiedad | Valor por defecto |
|-----------|-------------------|
| URL de la base de datos | `jdbc:postgresql://localhost:5432/postgres` |
| Usuario | `postgres` |
| Contraseña | `postgres` |

Si su entorno local utiliza valores distintos, puede sobreescribirlos mediante variables de entorno:

```
DB_URL=jdbc:postgresql://localhost:5432/postgres
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=UBsT/+rOGl9YC2epHTh39jzayRn7A4nBf6PGTnAAk9s5wvF/J4ctbQvbsMUxqo/jSZL94cykMR232pqny/MwWg==
JWT_EXPIRATION=3600000
```

3. **Compilar y ejecutar:** desde el IDE: ejecutar la clase `Tarea1Application.java`.

4. La aplicación arranca en **http://localhost:8080**.

---

## Datos sembrados automáticamente

Al arrancar la aplicación se crean automáticamente los siguientes datos. No es necesario insertar nada manualmente.

### Roles

| Rol | Descripción |
|-----|-------------|
| SUPER-ADMIN-ROLE | Super administrador del sistema |
| USER | Usuario regular del sistema |

### Usuarios

| Email | Contraseña | Rol |
|-------|------------|-----|
| super.admin@example.com | superadmin123 | SUPER-ADMIN-ROLE |
| user@example.com | user123 | USER |

Las contraseñas se almacenan encriptadas con BCrypt en la base de datos. No se crean categorías ni productos previamente.

---

## Endpoints de la API

### Autenticación

| Método | Endpoint | Acceso | Descripción |
|--------|----------|--------|-------------|
| POST | `/api/auth/login` | Público | Iniciar sesión, devuelve JWT |
| GET | `/api/auth/me` | Autenticado | Obtener datos del usuario autenticado |

### Categorías

| Método | Endpoint | Acceso | Descripción |
|--------|----------|--------|-------------|
| GET | `/api/categorias` | Autenticado | Listar todas las categorías |
| GET | `/api/categorias/{id}` | Autenticado | Obtener categoría por ID |
| POST | `/api/categorias` | SUPER-ADMIN-ROLE | Crear categoría |
| PUT | `/api/categorias/{id}` | SUPER-ADMIN-ROLE | Actualizar categoría |
| DELETE | `/api/categorias/{id}` | SUPER-ADMIN-ROLE | Eliminar categoría |

### Productos

| Método | Endpoint | Acceso | Descripción |
|--------|----------|--------|-------------|
| GET | `/api/productos` | Autenticado | Listar todos los productos |
| GET | `/api/productos/{id}` | Autenticado | Obtener producto por ID |
| POST | `/api/productos` | SUPER-ADMIN-ROLE | Crear producto |
| PUT | `/api/productos/{id}` | SUPER-ADMIN-ROLE | Actualizar producto |
| DELETE | `/api/productos/{id}` | SUPER-ADMIN-ROLE | Eliminar producto |
| PATCH | `/api/productos/{id}/categoria/{categoriaId}` | SUPER-ADMIN-ROLE | Asignar categoría a producto |

---

## Colección de pruebas (Postman)

El archivo `tarea1_api_postman_collection.json` en la raíz del repositorio contiene todos los endpoints listos para probar.

### Cómo importar

1. Abrir Postman
2. Hacer clic en **Import**
3. Arrastrar o seleccionar el archivo `tarea1_api_postman_collection.json`

### Flujo de prueba recomendado

1. Ejecutar **Login - Super Admin** (el token se guarda automáticamente)
2. Crear una categoría con **Crear categoría**
3. Crear un producto con **Crear producto**
4. Asignar la categoría al producto con **Asignar categoría a producto**
5. Consultar productos y categorías con los endpoints GET
6. Actualizar y eliminar productos y categorías
7. Ejecutar **Login - User** para cambiar al usuario regular
8. Verificar que el usuario regular puede consultar (GET devuelve 200)
9. Verificar que el usuario regular no puede crear, editar ni eliminar (devuelve 403)

