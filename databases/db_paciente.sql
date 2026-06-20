CREATE DATABASE db_paciente;

USE db_paciente;

CREATE TABLE pacientes (
    id_paciente INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni CHAR(8) NOT NULL UNIQUE,
    telefono CHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_pacientes_dni
        CHECK (dni REGEXP '^[0-9]{8}$'),
    CONSTRAINT chk_pacientes_telefono
        CHECK (telefono REGEXP '^[0-9]{9}$'),
	CONSTRAINT chk_pacientes_email
		CHECK (email LIKE '%_@_%._%')
);

INSERT INTO pacientes (nombres, apellidos, dni, telefono, email) VALUES
('Carlos', 'Ramírez Soto', '12345678', '987654321', 'carlos.ramirez@gmail.com'),
('María', 'Gómez Torres', '23456789', '987654322', 'maria.gomez@hotmail.com'),
('Luis', 'Paredes Ruiz', '34567890', '987654323', 'luis.paredes@yahoo.com'),
('Ana', 'Vargas Medina', '45678901', '987654324', 'ana.vargas@gmail.com'),
('José', 'Chávez Flores', '56789012', '987654325', 'jose.chavez@hotmail.com'),
('Lucía', 'Quispe Herrera', '67890123', '987654326', 'lucia.quispe@gmail.com'),
('Pedro', 'Salazar Vega', '78901234', '987654327', 'pedro.salazar@yahoo.com'),
('Sofía', 'Mendoza Ríos', '89012345', '987654328', 'sofia.mendoza@gmail.com'),
('Diego', 'Castillo Núñez', '90123456', '987654329', 'diego.castillo@hotmail.com'),
('Valeria', 'Ortega Díaz', '11223344', '987654330', 'valeria.ortega@gmail.com'),

('Jorge', 'Flores Paredes', '22334455', '987654331', 'jorge.flores@yahoo.com'),
('Camila', 'Reyes Salas', '33445566', '987654332', 'camila.reyes@gmail.com'),
('Andrés', 'Navarro Cruz', '44556677', '987654333', 'andres.navarro@hotmail.com'),
('Fernanda', 'Rojas León', '55667788', '987654334', 'fernanda.rojas@gmail.com'),
('Ricardo', 'Espinoza Mora', '66778899', '987654335', 'ricardo.espinoza@yahoo.com'),
('Paula', 'Cáceres Luna', '77889900', '987654336', 'paula.caceres@gmail.com'),
('Miguel', 'Herrera Campos', '88990011', '987654337', 'miguel.herrera@hotmail.com'),
('Daniela', 'Vásquez Silva', '99001122', '987654338', 'daniela.vasquez@gmail.com'),
('Sergio', 'Torres Aguilar', '10111213', '987654339', 'sergio.torres@yahoo.com'),
('Elena', 'Ramos Peña', '12131415', '987654340', 'elena.ramos@gmail.com'),

('Hugo', 'Flores Castillo', '13141516', '987654341', 'hugo.flores@hotmail.com'),
('Natalia', 'Mora Quispe', '14151617', '987654342', 'natalia.mora@gmail.com'),
('Bruno', 'Ponce Delgado', '15161718', '987654343', 'bruno.ponce@yahoo.com'),
('Carla', 'Aguilar Rojas', '16171819', '987654344', 'carla.aguilar@gmail.com'),
('Raúl', 'Soto Vargas', '17181920', '987654345', 'raul.soto@hotmail.com'),
('Milagros', 'Vega Flores', '18192021', '987654346', 'milagros.vega@gmail.com'),
('Kevin', 'Díaz Ramos', '19202122', '987654347', 'kevin.diaz@yahoo.com'),
('Patricia', 'Suárez Torres', '20212223', '987654348', 'patricia.suarez@gmail.com'),
('Omar', 'Guzmán Ríos', '21222324', '987654349', 'omar.guzman@hotmail.com'),
('Inés', 'López Medina', '22232425', '987654350', 'ines.lopez@gmail.com');
