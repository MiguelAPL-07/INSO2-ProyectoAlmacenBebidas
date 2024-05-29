-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.7.21-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para almacen
CREATE DATABASE IF NOT EXISTS `almacen` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `almacen`;


-- Volcando estructura para tabla alamacen.personas
CREATE TABLE IF NOT EXISTS `personas` (
  `IdPersona` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  `Apellido1` varchar(100) NOT NULL,
  `Apellido2` varchar(100) DEFAULT NULL,
  `DNI` varchar(100) NOT NULL,
  `Telefono` varchar(50) NOT NULL,
  `CorreoElectronico` varchar(100) NOT NULL,
  PRIMARY KEY (`IdPersona`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla almacen.personas: ~3 rows (aproximadamente)
DELETE FROM `personas`;
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
INSERT INTO `personas` (`IdPersona`, `Nombre`, `Apellido1`, `Apellido2`, `DNI`, `Telefono`,`CorreoElectronico`) VALUES
	(1, 'maria', 'fernandez', 'rodriguez', '12345678A', '12345', 'mariaferr@almacen.com'),
	(2, 'Antonio', 'García', 'Rodríguez', '253654643G', '45654', 'garuca@almacen.com'),
	(3, 'Gabriel', 'García', 'Pérez', '123234546C', '234', 'gabi@almacen.com');


-- Volcando estructura para tabla alamacen.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `IdRol` int(11) NOT NULL AUTO_INCREMENT,
  `TipoUsuario` char(1) NOT NULL,
  `Descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`IdRol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla alamacen.roles: ~3 rows (aproximadamente)
DELETE FROM `roles`;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`IdRol`, `TipoUsuario`, `Descripcion`) VALUES
	(1, 'C', 'Cliente'),
	(2, 'E', 'Empleado'),
	(3, 'O', 'Superusuario');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;


-- Volcando estructura para tabla almacen.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `IdUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `Usuario` varchar(20) NOT NULL,
  `Contrasena` varchar(50) NOT NULL,
  `UltimaConexion` datetime DEFAULT CURRENT_TIMESTAMP,
  `Estado` bit(1) NOT NULL,
  `IdPersona` int(11) NOT NULL,
  `IdRol` int(11) NOT NULL,
  PRIMARY KEY (`IdUsuario`),
  KEY `FK_Usuario_Persona` (`IdPersona`),
  KEY `FK_Usuario_Rol` (`IdRol`),
  CONSTRAINT `FK_Usuario_Persona` FOREIGN KEY (`IdPersona`) REFERENCES `personas` (`IdPersona`),
  CONSTRAINT `FK_Usuario_Rol` FOREIGN KEY (`IdRol`) REFERENCES `roles` (`IdRol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla alamacen.usuarios: ~3 rows (aproximadamente)
DELETE FROM `usuarios`;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`IdUsuario`, `Usuario`, `Contrasena`, `UltimaConexion`, `Estado`, `IdPersona`, `IdRol`) VALUES
	(1, 'Cliente', 'Cliente', NULL, b'1', 1, 1),
	(2, 'Empleado', 'Empleado', '2018-01-27 00:03:56', b'1', 2, 2),
	(3, 'Admin', 'Admin', '2018-01-31 17:34:43', b'1', 3, 3);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;


-- Volcando estructura para tabla alamacen.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `IdCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`IdCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla alamacen.categorias: ~3 rows (aproximadamente)
DELETE FROM `categorias`;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` (`IdCategoria`, `Nombre`) VALUES
	(1, 'Refrescos'),
	(2, 'Zumos'),
	(3, 'Cervezas');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;

-- Volcando estructura para tabla alamacen.productos
CREATE TABLE IF NOT EXISTS `productos` (
  `IdProducto` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `Descripcion` varchar(100) NOT NULL,
  `Precio` DOUBLE NOT NULL, 
  `Iva` DOUBLE NOT NULL,
  `Cantidad` int(10) NOT NULL,
  `IdCategoria` int(11) NOT NULL,
  PRIMARY KEY (`IdProducto`),
  KEY `FK_Productos_Categoria` (`IdCategoria`),
  CONSTRAINT `FK_Productos_Categoria` FOREIGN KEY (`IdCategoria`) REFERENCES `categorias` (`IdCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla alamacen.productos: ~10 rows (aproximadamente)
DELETE FROM `productos`;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` (`IdProducto`, `Nombre`, `Descripcion`, `Precio`, `Iva`, `Cantidad`, `IdCategoria`) VALUES
	(1, 'CocaCola', 'Botellín de cocacola de 20cl', 0.75, 21, 500, 1),
	(2, 'Fanta Naranja', 'Botellín de fanta de naranja de 20cl', 0.71, 21, 500, 1),
	(3, 'Fanta Limón', 'Botellín de fanta de limón de 20cl', 0.70, 21, 500, 1),
	(4, 'Nestea Limón', 'Bote de nestea de limón de 33cl', 0.81, 21, 500, 1),
	(5, 'Nestea Limón s/a', 'Bote de nestea de limón de 33cl sin azúcar', 0.81, 21, 200, 1),
	(6, 'Zumo naranja', 'Zumo Don Simón de Naranja. Bote 200ml', 0.61, 19, 250, 2),
	(7, 'Zumo melocotón', 'Zumo Don Simón de Melocotón. Bote 200ml', 0.60, 19, 250, 2),
	(8, 'Zumo piña', 'Zumo Don Simón de Piña. Bote 200ml', 0.63, 19, 250, 2),
	(9, 'Mahou 0.0 Tostada', 'Cerveza sin alcohol 0.0 tostada. Botellín de 33cl', 1.18, 21, 1000, 3),
	(10, 'Estrella Galicia', 'Cerveza especial. Botellín de 33cl', 1.06, 21, 1500, 3);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;


-- Volcando estructura para tabla alamacen.estadoPedido
CREATE TABLE IF NOT EXISTS `estadoPedido` (
  `IdEstado` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`IdEstado`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla alamacen.estadoPedido: ~3 rows (aproximadamente)
DELETE FROM `estadoPedido`;
/*!40000 ALTER TABLE `estadoPedido` DISABLE KEYS */;
INSERT INTO `estadoPedido` (`IdEstado`, `Descripcion`) VALUES
	(1, 'Recibido'),
	(2, 'En preparación'),
	(3, 'Enviado');
/*!40000 ALTER TABLE `estadoPedido` ENABLE KEYS */;


-- Volcando estructura para tabla almacen.pedidos
CREATE TABLE IF NOT EXISTS `pedidos` (
  `IdPedido` int(11) NOT NULL AUTO_INCREMENT,
  `FechaCreacion` DATE NOT NULL,
  `FechaEnvio` DATE,
  `Calle` varchar(100) NOT NULL,
  `NumeroCalle` varchar(10) NOT NULL,
  `CodigoPostal` varchar(50) NOT NULL,
  `Ciudad` varchar(100) NOT NULL,
  `IdEmpleado` int(11),
  `IdCliente` int(11) NOT NULL,
  `IdEstado` int(11) NOT NULL,
  PRIMARY KEY (`IdPedido`),
  KEY `FK_Pedidos_Empleado` (`IdEmpleado`),
  KEY `FK_Pedidos_Cliente` (`IdCliente`), 
  KEY `FK_Pedidos_Estado` (`IdEstado`),
  CONSTRAINT `FK_Pedidos_Empleado` FOREIGN KEY (`IdEmpleado`) REFERENCES `personas` (`IdPersona`),
  CONSTRAINT `FK_Pedidos_Cliente` FOREIGN KEY (`IdCliente`) REFERENCES `personas` (`IdPersona`),
  CONSTRAINT `FK_Pedidos_Estado` FOREIGN KEY (`IdEstado`) REFERENCES `estadoPedido` (`IdEstado`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


-- Volcando estructura para tabla almacen.productosPedidos
CREATE TABLE IF NOT EXISTS `productosPedidos` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdPedido` int(11) NOT NULL,
  `IdProducto` int(11) NOT NULL,
  `Cantidad` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ProductosPedidos_Pedidos` (`IdPedido`),
  KEY `FK_ProductosPedidos_Productos` (`IdProducto`),
  CONSTRAINT `FK_ProductosPedidos_Pedidos` FOREIGN KEY (`IdPedido`) REFERENCES `pedidos` (`IdPedido`),
  CONSTRAINT `FK_ProductosPedidos_Productos` FOREIGN KEY (`IdProducto`) REFERENCES `productos` (`IdProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


-- Volcando estructura para tabla alamacen.menus
CREATE TABLE IF NOT EXISTS `menus` (
  `IdMenu` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) NOT NULL,
  `Tipo` enum('S','I') NOT NULL,
  `Estado` bit(1) NOT NULL DEFAULT b'1',
  `IdRol` int(11) NOT NULL,
  `IdMenu_Menu` int(11) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`IdMenu`),
  KEY `FK_Menu_Rol` (`IdRol`),
  KEY `Fk_Menu_menu` (`IdMenu_Menu`),
  CONSTRAINT `FK_Menu_Rol` FOREIGN KEY (`IdRol`) REFERENCES `roles` (`IdRol`),
  CONSTRAINT `Fk_Menu_menu` FOREIGN KEY (`IdMenu_Menu`) REFERENCES `menus` (`IdMenu`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;


-- Volcando datos para la tabla alamacen.menus: ~22 rows (aproximadamente)
DELETE FROM `menus`;
/*!40000 ALTER TABLE `menus` DISABLE KEYS */;
INSERT INTO `menus` (`IdMenu`, `Nombre`, `Tipo`, `Estado`, `IdRol`, `IdMenu_Menu`, `url`) VALUES
	(1, 'Nuevo pedido', 'I', b'1', 1, NULL, '/privado/cliente/nuevoPedido.xhtml'),
	(2, 'Ver stock de productos', 'I', b'1', 1, NULL, '/privado/cliente/visualizarProductos.xhtml'),
	(3, 'Ver historial de pedidos', 'I', b'1', 1, NULL, '/privado/cliente/visualizarPedidos.xhtml'),
	(4, 'Cambiar datos personales', 'I', b'1', 1, NULL, '/privado/cliente/editarDatosPersonales.xhtml'),
	(5, 'Ver pedidos pendientes', 'I', b'1', 2, NULL, '/privado/empleado/visualizarPedidosPendientes.xhtml'),
	(6, 'Ver pedidos asignados', 'I', b'1', 2, NULL, '/privado/empleado/visualizarPedidosAsignados.xhtml'),
	(7, 'Productos', 'S', b'1', 2, NULL, NULL),
	(8, 'Nuevo producto', 'I', b'1', 2, 7, '/privado/empleado/nuevoProducto.xhtml'),
	(9, 'Gestionar el stock', 'I', b'1', 2, 7, '/privado/empleado/gestionStock.xhtml'),
	(10, 'Administrar productos', 'I', b'1', 2, 7, '/privado/empleado/administrarProductos.xhtml'),
	(11, 'Administrar categorias', 'I', b'1', 2, NULL, '/privado/empleado/administrarCategorias.xhtml'),
	(12, 'Nuevo pedido', 'I', b'1', 2, NULL, '/privado/empleado/nuevoPedido.xhtml'),
	(13, 'Usuarios', 'S', b'1', 3, NULL, NULL),
	(14, 'Nuevo usuario', 'I', b'1', 3, 13, 'privado/administrador/nuevoUsuario.xhtml'),
	(15, 'Ver todos', 'I', b'1', 3, 13, '/privado/administrador/visualizarUsuarios.xhtml'),
	(16, 'Ver clientes', 'I', b'1', 3, 13, '/privado/administrador/visualizarClientes.xhtml'),
	(17, 'Ver empleados', 'I', b'1', 3, 13, '/privado/administrador/visualizarEmpleados.xhtml'),
	(18, 'Gestor de roles', 'I', b'1', 3, NULL, '/privado/administrador/administrarRoles.xhtml'),
	(19, 'Ver pedidos', 'S', b'1', 3, NULL, NULL),
	(20, 'Empleados', 'I', b'1', 3, 19, '/privado/administrador/viusualizarPedidosEmpleados.xhtml'),
	(21, 'Clientes', 'I', b'1', 3, 19, '/privado/administrador/visualizarPedidosClientes.xhtml');	
/*!40000 ALTER TABLE `menus` ENABLE KEYS */;


/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
