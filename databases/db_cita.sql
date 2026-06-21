-- ================================================================
-- SEED REALISTA DE CITAS — db_cita
-- ================================================================
-- Respeta las reglas de negocio de CitaService.registrarCita():
--   - Horario: 08:00 a 17:30
--   - Minutos: solo :00 o :30
--   - Un doctor no puede tener dos citas a la misma fecha/hora exacta
--
-- Usa los 2 doctores con cuenta de login (db_auth):
--   id_doctor 1 = Carlos Ramírez Soto (CARDIOLOGIA)
--   id_doctor 2 = Lucía Mendoza Pérez (NEUROLOGIA)
--
-- Usa pacientes reales del seed de db_paciente (ids 2 al 20, evitando
-- el id_paciente 1 "Carlos Ramírez Soto" para no confundirlo con el
-- doctor del mismo nombre en las demos).
--
-- Mezcla los 3 estados para poblar el dashboard de ADMIN y permitir
-- probar el flujo completo (atender / cancelar) con datos ya existentes.
--
-- IMPORTANTE: el trigger trg_citas_before_insert fuerza id_estado = 1
-- en cualquier INSERT, así que para sembrar citas ATENDIDA/CANCELADA
-- hay que insertar y luego hacer UPDATE del estado (el trigger solo
-- corre en BEFORE INSERT, no en UPDATE).
-- ================================================================

USE db_cita;

-- ---- Citas PROGRAMADAS (futuras, para probar "hoy" y próximos días) ----

INSERT INTO citas (id_paciente, id_doctor, fecha, id_estado) VALUES
(2,  1, '2026-06-22 09:00:00', 1),
(3,  1, '2026-06-22 09:30:00', 1),
(4,  2, '2026-06-22 10:00:00', 1),
(5,  1, '2026-06-22 11:00:00', 1),
(6,  2, '2026-06-23 08:30:00', 1),
(7,  1, '2026-06-23 15:00:00', 1),
(8,  2, '2026-06-24 14:00:00', 1),
(9,  1, '2026-06-25 16:30:00', 1),
(10, 2, '2026-06-26 09:00:00', 1);

-- ---- Citas ya ATENDIDAS (historial pasado) ----
-- Se insertan como PROGRAMADA (el trigger lo fuerza) y luego se actualizan,
-- porque el trigger solo intercepta INSERT, no UPDATE.

INSERT INTO citas (id_paciente, id_doctor, fecha, id_estado) VALUES
(11, 1, '2026-06-10 09:00:00', 1),
(12, 1, '2026-06-12 10:30:00', 1),
(13, 2, '2026-06-13 08:00:00', 1),
(14, 2, '2026-06-15 11:30:00', 1),
(15, 1, '2026-06-17 14:30:00', 1),
(16, 2, '2026-06-18 16:00:00', 1);

UPDATE citas SET id_estado = 2
WHERE id_paciente IN (11, 12, 13, 14, 15, 16)
  AND fecha < '2026-06-19 00:00:00';

-- ---- Citas CANCELADAS (historial pasado) ----

INSERT INTO citas (id_paciente, id_doctor, fecha, id_estado) VALUES
(17, 1, '2026-06-11 13:00:00', 1),
(18, 2, '2026-06-14 09:30:00', 1),
(19, 1, '2026-06-16 15:30:00', 1);

UPDATE citas SET id_estado = 3
WHERE id_paciente IN (17, 18, 19)
  AND fecha < '2026-06-19 00:00:00';
