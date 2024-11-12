CREATE TABLE cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);

CREATE TABLE producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    precio DECIMAL(10, 2),
    stock INT
);

CREATE TABLE comprobante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT,
    fecha DATE,
    total DECIMAL(10, 2),
    cantidad_productos INT,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE linea_factura (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comprobante_id BIGINT,
    producto_id BIGINT,
    cantidad INT,
    subtotal DECIMAL(10, 2),
    FOREIGN KEY (comprobante_id) REFERENCES comprobante(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);
