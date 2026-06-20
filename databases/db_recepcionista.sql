CREATE DATABASE db_recepcionista;

USE db_recepcionista;

CREATE TABLE recepcionista (
    id_recepcionista INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni CHAR(8) NOT NULL UNIQUE,
    telefono CHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT chk_recepcionista_dni
        CHECK (dni REGEXP '^[0-9]{8}$'),

    CONSTRAINT chk_recepcionista_telefono
        CHECK (telefono REGEXP '^[0-9]{9}$'),

    CONSTRAINT chk_recepcionista_email
        CHECK (email LIKE '%_@_%._%')
);

INSERT INTO recepcionista
(nombres, apellidos, dni, telefono, email, activo)
VALUES

('Ana', 'Torres Ramirez', '74125896', '987654321', 'ana.torres1@mail.com', TRUE),
('Carlos', 'Mendoza Soto', '85236974', '987654322', 'carlos.mendoza2@mail.com', TRUE),
('Lucia', 'Garcia Flores', '96325874', '987654323', 'lucia.garcia3@mail.com', TRUE),
('Jorge', 'Quispe Diaz', '74136985', '987654324', 'jorge.quispe4@mail.com', TRUE),
('Maria', 'Vargas Perez', '85214796', '987654325', 'maria.vargas5@mail.com', TRUE),

('Pedro', 'Rojas Medina', '96314785', '987654326', 'pedro.rojas6@mail.com', TRUE),
('Carmen', 'Lopez Cruz', '74185296', '987654327', 'carmen.lopez7@mail.com', TRUE),
('Luis', 'Castillo Ramos', '85296314', '987654328', 'luis.castillo8@mail.com', TRUE),
('Patricia', 'Salazar Vega', '96374125', '987654329', 'patricia.salazar9@mail.com', TRUE),
('Miguel', 'Fernandez Ortiz', '74196385', '987654330', 'miguel.fernandez10@mail.com', TRUE),

('Valeria', 'Morales Ruiz', '85274196', '987654331', 'valeria.morales11@mail.com', TRUE),
('Andres', 'Chavez Silva', '96385274', '987654332', 'andres.chavez12@mail.com', TRUE),
('Daniela', 'Gutierrez Leon', '74125863', '987654333', 'daniela.gutierrez13@mail.com', TRUE),
('Ricardo', 'Campos Flores', '85236941', '987654334', 'ricardo.campos14@mail.com', TRUE),
('Sofia', 'Alvarez Soto', '96325841', '987654335', 'sofia.alvarez15@mail.com', TRUE),

('Fernando', 'Reyes Castro', '74136952', '987654336', 'fernando.reyes16@mail.com', TRUE),
('Gabriela', 'Paredes Diaz', '85214763', '987654337', 'gabriela.paredes17@mail.com', TRUE),
('Victor', 'Espinoza Cruz', '96374158', '987654338', 'victor.espinoza18@mail.com', TRUE),
('Rosa', 'Miranda Soto', '74185263', '987654339', 'rosa.miranda19@mail.com', TRUE),
('Hugo', 'Delgado Perez', '85296347', '987654340', 'hugo.delgado20@mail.com', TRUE),
('Inés', 'López Medina', '22232425', '987654350', 'ines.lopez@gmail.com', TRUE);
