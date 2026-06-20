CREATE DATABASE db_farmacia;

USE db_farmacia;

CREATE TABLE medicamentos (
    id_medicamento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    stock INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
	CONSTRAINT chk_stock CHECK (stock >= 0),
    CONSTRAINT chk_precio CHECK (precio >= 0)
);

CREATE TABLE recetas (
    id_receta BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cita BIGINT NOT NULL,
    id_medicamento INT NOT NULL,
    cantidad INT NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT fk_recetas_medicamento
		FOREIGN KEY(id_medicamento)
        REFERENCES medicamentos(id_medicamento),
    CONSTRAINT chk_cantidad CHECK(cantidad > 0)
);

INSERT INTO medicamentos (nombre, stock, precio) VALUES
('Paracetamol 500mg', 120, 1.50),
('Ibuprofeno 400mg', 100, 2.00),
('Amoxicilina 500mg', 80, 3.50),
('Azitromicina 500mg', 60, 6.00),
('Loratadina 10mg', 90, 1.80),
('Cetirizina 10mg', 85, 2.10),
('Omeprazol 20mg', 110, 2.50),
('Pantoprazol 40mg', 70, 3.20),
('Metformina 850mg', 95, 2.30),
('Losartán 50mg', 75, 2.80),

('Enalapril 10mg', 65, 2.20),
('Aspirina 100mg', 150, 1.00),
('Diclofenaco 50mg', 88, 1.70),
('Naproxeno 550mg', 77, 2.90),
('Salbutamol inhalador', 40, 8.50),
('Prednisona 20mg', 55, 3.00),
('Dexametasona 4mg', 50, 2.60),
('Clonazepam 2mg', 45, 4.20),
('Diazepam 10mg', 35, 3.80),
('Tramadol 50mg', 60, 5.00),

('Metamizol 1g', 90, 1.90),
('Ranitidina 150mg', 70, 2.00),
('Ciprofloxacino 500mg', 65, 4.10),
('Levofloxacino 500mg', 55, 6.50),
('Claritromicina 500mg', 50, 5.80),
('Albendazol 400mg', 120, 2.40),
('Mebendazol 100mg', 110, 1.60),
('Insulina rápida', 30, 25.00),
('Insulina NPH', 25, 28.00),
('Atorvastatina 20mg', 85, 3.60),

('Simvastatina 20mg', 80, 2.90),
('Furosemida 40mg', 70, 1.40),
('Hidroclorotiazida 25mg', 95, 1.30),
('Amlodipino 5mg', 100, 2.20),
('Verapamilo 80mg', 60, 2.70),
('Metoprolol 50mg', 75, 3.10),
('Carvedilol 25mg', 65, 3.40),
('Salbutamol jarabe', 50, 4.50),
('Ambroxol jarabe', 90, 3.00),
('Bromhexina 8mg', 85, 2.50),

('Acetilcisteína 600mg', 70, 3.80),
('Vitamina C 1g', 200, 0.90),
('Vitamina D3 1000UI', 180, 1.20),
('Complejo B', 150, 1.50),
('Hierro 325mg', 130, 1.70),
('Calcio 600mg', 140, 1.60),
('Magnesio 400mg', 120, 1.80),
('Multivitamínico adulto', 100, 2.50),
('Suero oral', 160, 1.10),
('Clorfenamina 4mg', 90, 1.40);
