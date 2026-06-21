-- ================================================================
-- RESET COMPLETO DE BASE DE DATOS — db_doctor
-- ================================================================
DROP DATABASE IF EXISTS db_doctor;
CREATE DATABASE db_doctor;
USE db_doctor;

-- ================================================================
-- TABLA ESPECIALIDADES
-- ================================================================
CREATE TABLE especialidades (
    id_especialidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- ================================================================
-- TABLA DOCTORES
-- ================================================================
CREATE TABLE doctores (
    id_doctor INT AUTO_INCREMENT PRIMARY KEY,
    id_especialidad INT NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni CHAR(8) NOT NULL UNIQUE,
    telefono CHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_doctores_especialidad
        FOREIGN KEY (id_especialidad)
        REFERENCES especialidades(id_especialidad),
    CONSTRAINT chk_doctores_dni
        CHECK (dni REGEXP '^[0-9]{8}$'),
    CONSTRAINT chk_doctores_telefono
        CHECK (telefono REGEXP '^[0-9]{9}$'),
    CONSTRAINT chk_doctores_email
        CHECK (email LIKE '%_@_%._%')
);

-- ================================================================
-- SEED ESPECIALIDADES
-- ================================================================
INSERT INTO especialidades (nombre) VALUES
('CARDIOLOGIA'),
('NEUROLOGIA'),
('PEDIATRIA'),
('TRAUMATOLOGIA'),
('DERMATOLOGIA');

-- ================================================================
-- SEED DOCTORES
-- IMPORTANTE: id_doctor 1 y 2 deben coincidir exactamente con los
-- usuarios de db_auth (correos carlos.ramirez1@mail.com e
-- lucia.mendoza2@mail.com) para que el login funcione.
-- ================================================================
INSERT INTO doctores (id_especialidad, nombres, apellidos, dni, telefono, email) VALUES
(1, 'Carlos', 'Ramirez Soto', '74125896', '987654321', 'carlos.ramirez1@mail.com'),
(2, 'Lucia', 'Mendoza Perez', '85236974', '987654322', 'lucia.mendoza2@mail.com'),
(3, 'Juan', 'Quispe Flores', '96325874', '987654323', 'juan.quispe3@mail.com'),
(4, 'Maria', 'Lopez Vargas', '74136985', '987654324', 'maria.lopez4@mail.com'),
(5, 'Pedro', 'Huaman Torres', '85214796', '987654325', 'pedro.huaman5@mail.com'),

(1, 'Ana', 'Garcia Rojas', '96314785', '987654326', 'ana.garcia6@mail.com'),
(2, 'Jose', 'Perez Diaz', '74185296', '987654327', 'jose.perez7@mail.com'),
(3, 'Carmen', 'Soto Ramirez', '85296314', '987654328', 'carmen.soto8@mail.com'),
(4, 'Luis', 'Fernandez Cruz', '96374125', '987654329', 'luis.fernandez9@mail.com'),
(5, 'Elena', 'Vargas Medina', '74196385', '987654330', 'elena.vargas10@mail.com'),

(1, 'Miguel', 'Torres Rios', '85274196', '987654331', 'miguel.torres11@mail.com'),
(2, 'Sofia', 'Castillo Leon', '96385274', '987654332', 'sofia.castillo12@mail.com'),
(3, 'Diego', 'Ramos Paredes', '74125863', '987654333', 'diego.ramos13@mail.com'),
(4, 'Paola', 'Nunez Silva', '85236941', '987654334', 'paola.nunez14@mail.com'),
(5, 'Ricardo', 'Salazar Vega', '96325841', '987654335', 'ricardo.salazar15@mail.com'),

(1, 'Valeria', 'Morales Diaz', '74136952', '987654336', 'valeria.morales16@mail.com'),
(2, 'Andres', 'Vasquez Ortiz', '85214763', '987654337', 'andres.vasquez17@mail.com'),
(3, 'Daniela', 'Chavez Ruiz', '96374158', '987654338', 'daniela.chavez18@mail.com'),
(4, 'Fernando', 'Gutierrez Ramos', '74185263', '987654339', 'fernando.gutierrez19@mail.com'),
(5, 'Patricia', 'Reyes Castro', '85296347', '987654340', 'patricia.reyes20@mail.com'),

(1, 'Jorge', 'Campos Flores', '96314752', '987654341', 'jorge.campos21@mail.com'),
(2, 'Mariana', 'Alvarez Soto', '74196325', '987654342', 'mariana.alvarez22@mail.com'),
(3, 'Oscar', 'Rojas Medina', '85274163', '987654343', 'oscar.rojas23@mail.com'),
(4, 'Gabriela', 'Leon Vargas', '96385241', '987654344', 'gabriela.leon24@mail.com'),
(5, 'Victor', 'Mamani Quispe', '74125874', '987654345', 'victor.mamani25@mail.com'),

(1, 'Rosa', 'Delgado Perez', '85236985', '987654346', 'rosa.delgado26@mail.com'),
(2, 'Hugo', 'Espinoza Cruz', '96325896', '987654347', 'hugo.espinoza27@mail.com'),
(3, 'Claudia', 'Zapata Ramos', '74136974', '987654348', 'claudia.zapata28@mail.com'),
(4, 'Alberto', 'Miranda Soto', '85214785', '987654349', 'alberto.miranda29@mail.com'),
(5, 'Karla', 'Paredes Diaz', '96374196', '987654350', 'karla.paredes30@mail.com');
