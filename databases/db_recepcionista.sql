-- ================================================================
-- RESET COMPLETO DE BASE DE DATOS — db_recepcionista
-- ================================================================
-- ADVERTENCIA: no existe un archivo .sql oficial de este servicio
-- en el repositorio al momento de generar este script. La estructura
-- de abajo se reconstruyó a partir de RecepcionistaEntity.java y los
-- DTOs reales (RecepcionistaCreateDto, RecepcionistaResponseDto),
-- replicando el mismo patrón de constraints que pacientes y doctores.
-- VERIFICAR contra el .sql real del servicio (si existe) antes de
-- ejecutar en un ambiente compartido con el equipo.
-- ================================================================
DROP DATABASE IF EXISTS db_recepcionista;
CREATE DATABASE db_recepcionista;
USE db_recepcionista;

-- ================================================================
-- TABLA RECEPCIONISTA
-- ================================================================
CREATE TABLE recepcionista (
    id_recepcionista INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni CHAR(8) NOT NULL UNIQUE,
    telefono CHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_recepcionista_dni
        CHECK (dni REGEXP '^[0-9]{8}$'),
    CONSTRAINT chk_recepcionista_telefono
        CHECK (telefono REGEXP '^[0-9]{9}$'),
    CONSTRAINT chk_recepcionista_email
        CHECK (email LIKE '%_@_%._%')
);

-- ================================================================
-- SEED RECEPCIONISTA
-- La primera fila (Inés López) corresponde a la cuenta de login ya
-- existente en db_auth (ines.lopez@gmail.com), aunque hoy no hay
-- ningún campo que las vincule por ID -- ver advertencia abajo.
-- ================================================================
INSERT INTO recepcionista (nombres, apellidos, dni, telefono, email) VALUES
('Inés', 'López Medina', '22232425', '987654350', 'ines.lopez@gmail.com'),
('Carlos', 'Benavides Ruiz', '33445567', '987654351', 'carlos.benavides@smarthealth.com'),
('Marta', 'Quiroga Salas', '44556678', '987654352', 'marta.quiroga@smarthealth.com');

-- ================================================================
-- ADVERTENCIA DE DISEÑO (no resuelta por este seed):
-- db_auth.usuarios no tiene una columna id_recepcionista (solo
-- id_doctor). Esto significa que, a diferencia del doctor, hoy no
-- existe una forma de saber por ID cuál fila de esta tabla
-- corresponde a qué usuario logueado con rol RECEPCIONISTA. El
-- vínculo de arriba es solo por coincidencia de email, no por
-- relación real en la base de datos.
-- ================================================================
