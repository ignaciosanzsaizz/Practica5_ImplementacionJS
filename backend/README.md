
# Práctica 3 – API REST con Persistencia y Capa de Servicios (Spring Boot)

## Descripción general

Este repositorio contiene la **Práctica 3** de la asignatura, que consiste en **extender la API REST desarrollada en la Práctica 2** incorporando:

- Persistencia de datos mediante **Spring Data JPA**
- Implementación de una **capa de servicios**
- Ampliación del modelo de dominio
- Manejo más preciso de **códigos de error HTTP**

La API modela un **sistema de carrito de compra** en un entorno de e‑commerce donde un usuario puede crear carritos y añadir artículos mediante líneas de carrito.

---

# Objetivo de la práctica

Los objetivos principales de esta práctica son:

- Introducir la **persistencia con JPA**
- Aplicar una arquitectura en capas (**Controller → Service → Repository**)
- Modelar relaciones entre entidades
- Manejar correctamente **códigos de estado HTTP**
- Extender la API para gestionar **líneas de carrito**

---

# Arquitectura del proyecto

La aplicación sigue la arquitectura:

Controller  
↓  
Service  
↓  
Repository (Spring Data JPA)  
↓  
Base de datos

A diferencia de la práctica anterior, **los datos se almacenan ahora en una base de datos persistente**.

---

# Modelo de dominio

La aplicación contiene **dos entidades principales**.

---

# Entidad: Carrito

Representa un carrito de compra asociado a un usuario.

| Campo | Tipo | Descripción |
|------|------|-------------|
| idCarrito | Long | Identificador único del carrito |
| idUsuario | String | Identificador del usuario propietario |
| correo | String | Correo electrónico del usuario |
| precioTotal | Double | Precio total acumulado del carrito |

Un **carrito puede contener múltiples líneas de carrito**.

---

# Entidad: LineaCarrito

Representa un artículo dentro de un carrito.

| Campo | Tipo | Descripción |
|------|------|-------------|
| idCarrito | Long | Identificador del carrito |
| idArticulo | Long | Identificador del artículo |
| precioUnitario | Double | Precio unitario del artículo |
| numeroUnidades | Integer | Número de unidades |
| costeLinea | Double | Coste total de la línea |



---

# Endpoints de la API

## Crear carrito

POST /api/carrito

Crea un nuevo carrito.

### Códigos de respuesta

- **201 CREATED** → carrito creado correctamente
- **409 CONFLICT** → ya existe un carrito con el mismo identificador

---

## Obtener carrito por id

GET /api/carrito/{idCarrito}

Devuelve un carrito concreto.

### Códigos de respuesta

- **200 OK** → carrito encontrado
- **404 NOT FOUND** → carrito con ese id no existe

---

## Obtener todos los carritos

GET /api/carrito

Devuelve la lista de todos los carritos.

### Códigos de respuesta

- **200 OK**

---

## Actualizar carrito

PUT /api/carrito/{idCarrito}

Actualiza un carrito existente.

### Códigos de respuesta

- **202 ACCEPTED** → carrito actualizado correctamente
- **404 NOT FOUND** → el carrito indicado no existe
- **400 BAD REQUEST** → el id de la URL y el del body no coinciden

---

## Eliminar carrito

DELETE /api/carrito/{idCarrito}

Elimina un carrito.

### Códigos de respuesta

- **204 NO CONTENT** → carrito eliminado correctamente
- **404 NOT FOUND** → el carrito no existe

---

# Añadir línea de carrito

POST /api/carrito/{idCarrito}/linea

Añade una línea de carrito a un carrito existente.

### Ejemplo de petición

{
"idArticulo": 15,
"precioUnitario": 20.0,
"numeroUnidades": 2
}

### Códigos de respuesta

- **201 CREATED** → línea añadida correctamente
- **404 NOT FOUND** → el carrito al que se intenta añadir la línea no existe

---

# Eliminar línea de carrito

DELETE /api/lineaCarrito/{idLineaCarrito}

Elimina una línea de carrito.

### Códigos de respuesta

- **204 NO CONTENT** → línea eliminada
- **404 NOT FOUND** → la línea de carrito no existe

---

# Manejo de errores

La API devuelve errores más específicos mediante `ResponseStatusException`:

| Código | Situación |
|------|------|
| 400 BAD REQUEST | Datos inconsistentes en la petición |
| 404 NOT FOUND | Recurso solicitado no existe |
| 409 CONFLICT | Intento de crear un recurso duplicado |

Esto permite que el cliente de la API identifique correctamente el tipo de error.

---

# Conclusión

Esta práctica amplía la API desarrollada previamente incorporando **persistencia de datos, arquitectura en capas y un modelo de dominio más realista**.

El resultado es una API REST más completa que permite gestionar **carritos y líneas de carrito de forma persistente**, con un manejo adecuado de **códigos de estado HTTP y errores**.
