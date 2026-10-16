-- ================================================================
-- RESET COMPLETO DE BASE DE DATOS - db_cita
-- ================================================================
-- IMPORTANTE:
-- Elimina la base de datos anterior y todos sus datos.
--
-- COMPATIBILIDAD:
-- db_auth: doctores 1 y 2
-- db_doctor: doctores 1 y 2
-- db_paciente: pacientes 1 al 30
-- db_farmacia: recetas asociadas a las citas 10 al 15
--
-- DISTRIBUCION DE IDS:
-- 1-9   = PROGRAMADAS (fechas futuras relativas al momento de ejecución)
-- 10-15 = ATENDIDAS
-- 16-18 = CANCELADAS
-- 19-21 = PROGRAMADAS (fechas futuras relativas al momento de ejecución)
--
-- ESTADOS:
-- 1 = PROGRAMADA
-- 2 = ATENDIDA
-- 3 = CANCELADA
-- ================================================================

DROP DATABASE IF EXISTS db_cita;

CREATE DATABASE db_cita
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE db_cita;


-- ================================================================
-- 1. TABLA DE ESTADOS
-- ================================================================

CREATE TABLE estados_cita (
    id_estado INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_estado),
    UNIQUE KEY uk_estados_cita_nombre (nombre)
) ENGINE=InnoDB;


-- ================================================================
-- 2. TABLA DE CITAS
-- ================================================================

CREATE TABLE citas (
    id_cita INT NOT NULL AUTO_INCREMENT,
    id_paciente INT NOT NULL,
    id_doctor INT NOT NULL,
    fecha DATETIME NOT NULL,
    id_estado INT NOT NULL,
    fecha_reservable DATETIME GENERATED ALWAYS AS (
        CASE WHEN id_estado <> 3 THEN fecha ELSE NULL END
    ) STORED,

    PRIMARY KEY (id_cita),

    CONSTRAINT fk_citas_estados
        FOREIGN KEY (id_estado)
        REFERENCES estados_cita(id_estado),

    CONSTRAINT uk_citas_doctor_fecha_activa
        UNIQUE (id_doctor, fecha_reservable),

    INDEX idx_citas_paciente (id_paciente),
    INDEX idx_citas_doctor (id_doctor),
    INDEX idx_citas_fecha (fecha),
    INDEX idx_citas_estado (id_estado)
) ENGINE=InnoDB;


-- ================================================================
-- 3. INSERTAR ESTADOS
-- ================================================================

INSERT INTO estados_cita (id_estado, nombre) VALUES
(1, 'PROGRAMADA'),
(2, 'ATENDIDA'),
(3, 'CANCELADA');


-- ================================================================
-- 4. CITAS PROGRAMADAS
-- IDS: 1 AL 9
-- ================================================================

INSERT INTO citas
(id_cita, id_paciente, id_doctor, fecha, id_estado)
VALUES
 (1,  2,  1, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY), '09:00:00'), 1),
(2,  3,  1, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY), '09:30:00'), 1),
(3,  4,  2, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY), '10:00:00'), 1),
(4,  5,  1, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY), '11:00:00'), 1),
(5,  6,  2, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY), '08:30:00'), 1),
(6,  7,  1, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY), '15:00:00'), 1),
(7,  8,  2, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY), '14:00:00'), 1),
(8,  9,  1, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 4 DAY), '16:30:00'), 1),
(9, 10,  2, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 4 DAY), '09:00:00'), 1);


-- ================================================================
-- 5. CITAS ATENDIDAS
-- IDS: 10 AL 15
-- NO CAMBIAR EL ORDEN.
--
-- db_farmacia utiliza:
-- cita 10 -> medicamento 1
-- cita 11 -> medicamento 7
-- cita 12 -> medicamento 3
-- cita 13 -> medicamento 5
-- cita 14 -> medicamento 2
-- cita 15 -> medicamento 41
-- ================================================================

INSERT INTO citas
(id_cita, id_paciente, id_doctor, fecha, id_estado)
VALUES
(10, 11, 1, TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY), '09:00:00'), 2),
(11, 12, 1, TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 28 DAY), '10:30:00'), 2),
(12, 13, 2, TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 27 DAY), '08:00:00'), 2),
(13, 14, 2, TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 25 DAY), '11:30:00'), 2),
(14, 15, 1, TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 23 DAY), '14:30:00'), 2),
(15, 16, 2, TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 22 DAY), '16:00:00'), 2);


-- ================================================================
-- 6. CITAS CANCELADAS
-- IDS: 16 AL 18
-- ================================================================

INSERT INTO citas
(id_cita, id_paciente, id_doctor, fecha, id_estado)
VALUES
(16, 17, 1, TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 29 DAY), '13:00:00'), 3),
(17, 18, 2, TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 26 DAY), '09:30:00'), 3),
(18, 19, 1, TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 24 DAY), '15:30:00'), 3);


-- ================================================================
-- 7. CITAS PROGRAMADAS ADICIONALES
-- IDS: 19 AL 21
-- ================================================================

INSERT INTO citas
(id_cita, id_paciente, id_doctor, fecha, id_estado)
VALUES
(19, 1, 1, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY), '09:00:00'), 1),
(20, 2, 1, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY), '09:30:00'), 1),
(21, 1, 2, TIMESTAMP(DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY), '10:00:00'), 1);


-- ================================================================
-- 8. VERIFICAR TODAS LAS CITAS
-- ================================================================

SELECT
    c.id_cita,
    c.id_paciente,
    c.id_doctor,
    c.fecha,
    c.id_estado,
    e.nombre AS estado
FROM citas c
INNER JOIN estados_cita e
    ON c.id_estado = e.id_estado
ORDER BY c.id_cita;


-- ================================================================
-- 9. RESUMEN POR ESTADO
-- ================================================================

SELECT
    e.id_estado,
    e.nombre AS estado,
    COUNT(c.id_cita) AS cantidad
FROM estados_cita e
LEFT JOIN citas c
    ON e.id_estado = c.id_estado
GROUP BY e.id_estado, e.nombre
ORDER BY e.id_estado;


-- ================================================================
-- 10. VERIFICAR CITAS DE FARMACIA
-- DEBEN EXISTIR LOS IDS DEL 10 AL 15
-- ================================================================

SELECT
    id_cita,
    id_paciente,
    id_doctor,
    fecha,
    id_estado
FROM citas
WHERE id_cita BETWEEN 10 AND 15
ORDER BY id_cita;


-- ================================================================
-- 11. VERIFICAR TOTAL DE REGISTROS
-- RESULTADO ESPERADO: 21
-- ================================================================

SELECT COUNT(*) AS total_citas
FROM citas;
