-- Ejecutar una sola vez sobre la base db_cita ya existente.
USE db_cita;

ALTER TABLE estados_cita
    ADD COLUMN activo BOOLEAN NOT NULL DEFAULT TRUE;

SELECT id_estado, nombre, activo
FROM estados_cita
ORDER BY id_estado;
