-- Insertar clientes
INSERT INTO clientes (dni, nombre, apellidos, correo, direccion) VALUES
('12345678A', 'Juan', 'García López', 'juan.garcia@example.com', 'Calle Principal 123, Madrid'),
('87654321B', 'María', 'Rodríguez Martín', 'maria.rodriguez@example.com', 'Avenida Secundaria 456, Barcelona'),
('11223344C', 'Carlos', 'López Fernández', 'carlos.lopez@example.com', 'Calle Tercera 789, Valencia'),
('55667788D', 'Ana', 'Martínez García', 'ana.martinez@example.com', 'Paseo Cuarto 321, Sevilla'),
('99887766E', 'Pedro', 'Sánchez Ruiz', 'pedro.sanchez@example.com', 'Plaza Quinta 654, Bilbao');

-- Insertar artículos
INSERT INTO articulos (numero_serie, nombre_producto, precio_venta, stock_disponible) VALUES
('LAPTOP001', 'Laptop Dell XPS 13', 999.99, 15),
('LAPTOP002', 'Laptop HP Pavilion 15', 649.99, 0),
('MOUSE001', 'Ratón Logitech MX Master 3', 99.99, 25),
('TECLADO001', 'Teclado Mecánico Corsair K70', 179.99, 10),
('MONITOR001', 'Monitor LG 27 pulgadas 4K', 449.99, 8),
('CABLE001', 'Cable HDMI 2.1 3 metros', 15.99, 50),
('FUENTE001', 'Fuente Poder 750W Gold Modular', 129.99, 12),
('SSD001', 'SSD Samsung 970 EVO 1TB', 89.99, 20),
('RAM001', 'RAM Corsair Vengeance 16GB DDR4', 79.99, 18),
('WEBCAM001', 'Webcam Logitech C922 Pro', 99.99, 0);

-- Insertar pedidos
INSERT INTO pedidos (fecha_pedido, estado, dni_cliente) VALUES
('2024-01-18 14:30:00', 'PENDIENTE', '12345678A'),
('2024-01-19 10:15:00', 'ENVIADO', '87654321B'),
('2024-01-20 16:45:00', 'CANCELADO', '11223344C'),
('2024-01-21 09:00:00', 'PENDIENTE', '55667788D'),
('2024-01-22 11:30:00', 'ENVIADO', '99887766E'),
('2024-01-23 13:20:00', 'PENDIENTE', '12345678A');

-- Insertar líneas de pedido (relación Many-to-Many entre Pedidos y Artículos)
INSERT INTO lineas_pedido (pedido_id, articulo_numero_serie) VALUES
(1, 'LAPTOP001'),
(1, 'MOUSE001'),
(1, 'TECLADO001'),
(2, 'MONITOR001'),
(2, 'CABLE001'),
(3, 'FUENTE001'),
(3, 'SSD001'),
(4, 'RAM001'),
(5, 'WEBCAM001'),
(5, 'LAPTOP001'),
(6, 'MONITOR001'),
(6, 'MOUSE001');
