-- ================================================================
-- RESET COMPLETO DE BASE DE DATOS — db_auth
-- ================================================================
-- NOTA IMPORTANTE: los hashes BCrypt usados aquí son los mismos del
-- seed original del repositorio (ya verificados, login confirmado
-- funcionando). No se generaron hashes nuevos porque este entorno
-- no tiene acceso a la librería bcrypt para producirlos de forma
-- confiable — usar hashes no verificados habría sido arriesgar un
-- login roto sin poder probarlo antes de entregarlo.
-- ================================================================
DROP DATABASE IF EXISTS db_auth;
CREATE DATABASE db_auth;
USE db_auth;

-- ================================================================
-- TABLA ROLES
-- ================================================================
CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- ================================================================
-- TABLA USUARIOS
-- ================================================================
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(100) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    id_doctor INT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_usuarios_roles
        FOREIGN KEY (id_rol)
        REFERENCES roles(id_rol)
);

-- ================================================================
-- SEED ROLES
-- ================================================================
INSERT INTO roles (nombre) VALUES
('ADMIN'),
('DOCTOR'),
('RECEPCIONISTA');

-- ================================================================
-- SEED USUARIOS
-- id_doctor 1 y 2 deben coincidir con los doctores sembrados en
-- db_doctor (Carlos Ramirez Soto y Lucia Mendoza Perez).
-- ================================================================
INSERT INTO usuarios (correo, clave, id_rol, id_doctor) VALUES
('admin@smarthealth.com', '$2a$12$m/tnrGEfdCpeewLy0BjNQezdxqk4s8TgeoUlVZSIlKiWLN8fqfRva', 1, NULL),
('carlos.ramirez1@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 1),
('lucia.mendoza2@mail.com', '$2a$12$KNVvWHg2Xel4GL9b/KplQusbeW65CYwPZT8WS54Kwb0/jEQOCSw5u', 2, 2),
('ines.lopez@gmail.com', '$2a$12$hS9DhZ2oEozcCg80qfIpGuq7DvodgDdTGcgogwFKSwhTAKs1Os6im', 3, NULL);

-- === claves en texto plano (solo referencia, no se guardan así) ===
-- admin@smarthealth.com         -> Admin.123
-- carlos.ramirez1@mail.com      -> Doctor.123
-- lucia.mendoza2@mail.com       -> Doctor.123
-- ines.lopez@gmail.com          -> Recepcionista.123
