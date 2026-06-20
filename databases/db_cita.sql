CREATE DATABASE db_cita;

USE db_cita;

CREATE TABLE estados (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE citas (
    id_cita BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_doctor INT NOT NULL,
    fecha DATETIME NOT NULL,
	id_estado INT NOT NULL,
    CONSTRAINT fk_citas_estado
        FOREIGN KEY (id_estado)
        REFERENCES estados(id_estado)
);

INSERT INTO estados (nombre) VALUES
('PROGRAMADA'),
('ATENDIDA'),
('CANCELADA');

DELIMITER $$

CREATE TRIGGER trg_citas_before_insert
BEFORE INSERT ON citas
FOR EACH ROW
BEGIN
    SET NEW.id_estado = 1;
END$$

DELIMITER ;
