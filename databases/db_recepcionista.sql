-- ================================================================
-- RESET COMPLETO DE BASE DE DATOS — db_recepcionista
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
-- ================================================================
INSERT INTO recepcionista (nombres, apellidos, dni, telefono, email) VALUES
('Inés', 'López Medina', '22232425', '987654350', 'ines.lopez@gmail.com'),
('Carlos', 'Benavides Ruiz', '33445567', '987654351', 'carlos.benavides@smarthealth.com'),
('Marta', 'Quiroga Salas', '44556678', '987654352', 'marta.quiroga@smarthealth.com');
