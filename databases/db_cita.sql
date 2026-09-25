-- ================================================================
-- SEED REALISTA DE CITAS — db_cita
-- ================================================================
--
-- COHERENCIA CON LOS DEMÁS MICROSERVICIOS
--
-- db_auth:
--   id_doctor 1 = Carlos Ramirez Soto
--   id_doctor 2 = Lucia Mendoza Perez
--
-- db_doctor:
--   id_doctor 1 = Carlos Ramirez Soto
--   id_doctor 2 = Lucia Mendoza Perez
--
-- db_paciente:
--   pacientes 1 al 30 existen
--
-- db_farmacia:
--   recetas utiliza específicamente:
--       id_cita 10
--       id_cita 11
--       id_cita 12
--       id_cita 13
--       id_cita 14
--       id_cita 15
--
-- POR ESO ES IMPORTANTE NO CAMBIAR EL ORDEN DE LAS CITAS.
--
-- Distribución:
--   1 - 9   = PROGRAMADAS
--   10 - 15 = ATENDIDAS
--   16 - 18 = CANCELADAS
--   19 - 21 = PROGRAMADAS
--
-- Estados:
--   1 = PROGRAMADA
--   2 = ATENDIDA
--   3 = CANCELADA
--
-- Reglas:
--   - Horario entre 08:00 y 17:30
--   - Solo minutos :00 y :30
--   - No repetir doctor + fecha/hora
--
-- ================================================================

USE db_cita;


-- ================================================================
-- DESACTIVAR SAFE UPDATE TEMPORALMENTE
--
-- Necesario para poder ejecutar los UPDATE de estado.
-- ================================================================

SET SQL_SAFE_UPDATES = 0;


-- ================================================================
-- 1. CITAS PROGRAMADAS
--
-- IDs esperados:
--   1 al 9
--
-- Fechas futuras / actuales respecto a 24/09/2026.
-- ================================================================

INSERT INTO citas (id_paciente, id_doctor, fecha, id_estado) VALUES

-- ------------------------------------------------
-- CITAS DE HOY: 24/09/2026
-- ------------------------------------------------

(2, 1, '2026-09-24 09:00:00', 1),
(3, 1, '2026-09-24 09:30:00', 1),
(4, 2, '2026-09-24 10:00:00', 1),
(5, 1, '2026-09-24 11:00:00', 1),

-- ------------------------------------------------
-- CITAS FUTURAS
-- ------------------------------------------------

(6, 2, '2026-09-25 08:30:00', 1),
(7, 1, '2026-09-25 15:00:00', 1),

(8, 2, '2026-09-26 14:00:00', 1),
(9, 1, '2026-09-28 16:30:00', 1),
(10, 2, '2026-09-29 09:00:00', 1);


-- ================================================================
-- 2. CITAS ATENDIDAS
--
-- IDs esperados:
--   10 al 15
--
-- IMPORTANTE:
-- db_farmacia.recetas utiliza exactamente estos id_cita:
--
--   10 -> medicamento 1
--   11 -> medicamento 7
--   12 -> medicamento 3
--   13 -> medicamento 5
--   14 -> medicamento 2
--   15 -> medicamento 41
--
-- NO CAMBIAR EL ORDEN.
-- ================================================================

INSERT INTO citas (id_paciente, id_doctor, fecha, id_estado) VALUES

(11, 1, '2026-09-10 09:00:00', 1),
(12, 1, '2026-09-12 10:30:00', 1),
(13, 2, '2026-09-13 08:00:00', 1),
(14, 2, '2026-09-15 11:30:00', 1),
(15, 1, '2026-09-17 14:30:00', 1),
(16, 2, '2026-09-18 16:00:00', 1);


-- ------------------------------------------------
-- PROGRAMADA -> ATENDIDA
-- ------------------------------------------------

UPDATE citas
SET id_estado = 2
WHERE id_paciente IN (11, 12, 13, 14, 15, 16)
  AND fecha < '2026-09-19 00:00:00';


-- ================================================================
-- 3. CITAS CANCELADAS
--
-- IDs esperados:
--   16 al 18
-- ================================================================

INSERT INTO citas (id_paciente, id_doctor, fecha, id_estado) VALUES

(17, 1, '2026-09-11 13:00:00', 1),
(18, 2, '2026-09-14 09:30:00', 1),
(19, 1, '2026-09-16 15:30:00', 1);


-- ------------------------------------------------
-- PROGRAMADA -> CANCELADA
-- ------------------------------------------------

UPDATE citas
SET id_estado = 3
WHERE id_paciente IN (17, 18, 19)
  AND fecha < '2026-09-19 00:00:00';


-- ================================================================
-- 4. CITAS PROGRAMADAS ADICIONALES
--
-- IDs esperados:
--   19 al 21
--
-- Se mantienen porque ya formaban parte de tu seed original.
-- ================================================================

INSERT INTO citas (id_paciente, id_doctor, fecha, id_estado) VALUES

(1, 1, '2026-09-26 09:00:00', 1),
(2, 1, '2026-09-26 09:30:00', 1),
(1, 2, '2026-09-26 10:00:00', 1);


-- ================================================================
-- VOLVER A ACTIVAR SAFE UPDATE
-- ================================================================

SET SQL_SAFE_UPDATES = 1;


-- ================================================================
-- VERIFICACIÓN
-- ================================================================

SELECT
    id_cita,
    id_paciente,
    id_doctor,
    fecha,
    id_estado
FROM citas
ORDER BY id_cita;


-- ================================================================
-- VERIFICAR RESUMEN POR ESTADO
-- ================================================================

SELECT
    id_estado,
    COUNT(*) AS cantidad
FROM citas
GROUP BY id_estado
ORDER BY id_estado;


-- ================================================================
-- VERIFICAR LAS CITAS UTILIZADAS POR db_farmacia
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