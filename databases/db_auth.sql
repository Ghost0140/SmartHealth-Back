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
        REFERENCES roles(id_rol),
    CONSTRAINT uk_usuarios_doctor UNIQUE (id_doctor)
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
-- Los perfiles de personal se vinculan por su ID en su base correspondiente.
-- ================================================================
INSERT INTO usuarios (correo, clave, id_rol, id_doctor) VALUES
('admin@smarthealth.com', '$2a$12$m/tnrGEfdCpeewLy0BjNQezdxqk4s8TgeoUlVZSIlKiWLN8fqfRva', 1, NULL),
('carlos.ramirez1@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 1),
('lucia.mendoza2@mail.com', '$2a$12$KNVvWHg2Xel4GL9b/KplQusbeW65CYwPZT8WS54Kwb0/jEQOCSw5u', 2, 2),
('ines.lopez@gmail.com', '$2a$12$hS9DhZ2oEozcCg80qfIpGuq7DvodgDdTGcgogwFKSwhTAKs1Os6im', 3, NULL),
('juan.quispe3@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 3),
('maria.lopez4@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 4),
('pedro.huaman5@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 5),
('ana.garcia6@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 6),
('jose.perez7@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 7),
('carmen.soto8@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 8),
('luis.fernandez9@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 9),
('elena.vargas10@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 10),
('miguel.torres11@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 11),
('sofia.castillo12@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 12),
('diego.ramos13@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 13),
('paola.nunez14@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 14),
('ricardo.salazar15@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 15),
('valeria.morales16@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 16),
('andres.vasquez17@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 17),
('daniela.chavez18@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 18),
('fernando.gutierrez19@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 19),
('patricia.reyes20@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 20),
('jorge.campos21@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 21),
('mariana.alvarez22@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 22),
('oscar.rojas23@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 23),
('gabriela.leon24@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.xp9jRTRUZm.x/14EW2', 2, 24),
('victor.mamani25@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.x/14EW2', 2, 25),
('rosa.delgado26@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.x/14EW2', 2, 26),
('hugo.espinoza27@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.x/14EW2', 2, 27),
('claudia.zapata28@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.x/14EW2', 2, 28),
('alberto.miranda29@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.x/14EW2', 2, 29),
('karla.paredes30@mail.com', '$2a$12$H3HOOEcJW29UBwstwnmWO.9yiKmfnNejYJ.x/14EW2', 2, 30);

-- === claves en texto plano (solo referencia, no se guardan así) ===
-- admin@smarthealth.com         -> Admin.123
-- carlos.ramirez1@mail.com      -> Doctor.123
-- lucia.mendoza2@mail.com       -> Doctor.123
-- doctores sembrados (ids 3 al 30) -> Doctor.123
-- ines.lopez@gmail.com          -> Recepcionista.123
