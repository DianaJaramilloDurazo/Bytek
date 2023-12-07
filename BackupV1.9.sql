-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.11.5-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para sgs_db
DROP DATABASE IF EXISTS `sgs_db`;
CREATE DATABASE IF NOT EXISTS `sgs_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci */;
USE `sgs_db`;

-- usuario 
DROP USER IF EXISTS 'spring_user'@'localhost';
CREATE USER spring_user@localhost IDENTIFIED BY '123456';
GRANT INSERT ON sgs_db.* TO 'spring_user'@'localhost';
GRANT EXECUTE ON sgs_db.* TO 'spring_user'@'localhost';
GRANT UPDATE ON sgs_db.* TO 'spring_user'@'localhost';
GRANT SELECT ON sgs_db.* TO 'spring_user'@'localhost';
GRANT EVENT ON sgs_db.* TO 'spring_user'@'localhost'; 

-- Volcando estructura para tabla sgs_db.act_asociada
DROP TABLE IF EXISTS `act_asociada`;
CREATE TABLE IF NOT EXISTS `act_asociada` (
  `idAct_Asociada` int(11) NOT NULL AUTO_INCREMENT,
  `Act_Nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`idAct_Asociada`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.act_asociada: ~6 rows (aproximadamente)
DELETE FROM `act_asociada`;
INSERT INTO `act_asociada` (`idAct_Asociada`, `Act_Nombre`) VALUES
	(1, 'CACEI'),
	(2, 'Licenciatura'),
	(3, 'Personal'),
	(4, 'Sistema de Gestión de Calidad'),
	(5, 'Posgrado'),
	(6, 'Otra');

-- Volcando estructura para tabla sgs_db.act_asociada_solicitud
DROP TABLE IF EXISTS `act_asociada_solicitud`;
CREATE TABLE IF NOT EXISTS `act_asociada_solicitud` (
  `Act_Asociada_idAct_Asociada` int(11) NOT NULL,
  `Solicitud_idSolicitud` int(11) NOT NULL,
  `Descripcion` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`Act_Asociada_idAct_Asociada`,`Solicitud_idSolicitud`),
  KEY `fk_Act_Asociada_has_Solicitud_Solicitud1_idx` (`Solicitud_idSolicitud`),
  KEY `fk_Act_Asociada_has_Solicitud_Act_Asociada1_idx` (`Act_Asociada_idAct_Asociada`),
  CONSTRAINT `fk_Act_Asociada_has_Solicitud_Act_Asociada1` FOREIGN KEY (`Act_Asociada_idAct_Asociada`) REFERENCES `act_asociada` (`idAct_Asociada`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Act_Asociada_has_Solicitud_Solicitud1` FOREIGN KEY (`Solicitud_idSolicitud`) REFERENCES `solicitud` (`idSolicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.act_asociada_solicitud: ~0 rows (aproximadamente)
DELETE FROM `act_asociada_solicitud`;

-- Volcando estructura para tabla sgs_db.carrera
DROP TABLE IF EXISTS `carrera`;
CREATE TABLE IF NOT EXISTS `carrera` (
  `idCarrera` int(11) NOT NULL AUTO_INCREMENT,
  `Carrera_Nombre` varchar(120) NOT NULL,
  PRIMARY KEY (`idCarrera`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.carrera: ~11 rows (aproximadamente)
DELETE FROM `carrera`;
INSERT INTO `carrera` (`idCarrera`, `Carrera_Nombre`) VALUES
	(1, 'Ingeniería en Computación'),
	(2, 'Ingeniería Industrial'),
	(3, 'Ingeniería Electrónica'),
	(4, 'Bioingeniería'),
	(5, 'Posgrado'),
	(6, 'Arquitectura'),
	(7, 'Ingeniería en Nanotecnología'),
	(8, 'Ingeniería Civil'),
	(9, 'Ingeniería en Software y Tecnologías Emergentes'),
	(10, 'Tronco Común Ingeniería'),
	(11, 'Tronco Común Arquitectura');

-- Volcando estructura para tabla sgs_db.categoria
DROP TABLE IF EXISTS `categoria`;
CREATE TABLE IF NOT EXISTS `categoria` (
  `idCategoria` int(11) NOT NULL AUTO_INCREMENT,
  `Cat_Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.categoria: ~6 rows (aproximadamente)
DELETE FROM `categoria`;
INSERT INTO `categoria` (`idCategoria`, `Cat_Descripcion`) VALUES
	(1, '101 - Profesor de Asignatura'),
	(2, '102 - Profesor de Asignatura'),
	(3, '103 - Profesor de Asignatura'),
	(4, '104 - Profesor de Tiempo Completo Asistente'),
	(5, '105 - Profesor de Tiempo Completo Asistente'),
	(6, '106 - Profesor de Tiempo Completo Asistente'),
	(7, '107 - Profesor de Tiempo Completo Asociado'),
	(8, '108 - Profesor de Tiempo Completo Asociado'),
	(9, '109 - Profesor de Tiempo Completo Asociado'),
	(10, '110 - Profesor de Tiempo Completo Titular'),
	(11, '111 - Profesor de Tiempo Completo Titular'),
	(12, '112 - Profesor de Tiempo Completo Titular'),
	(13, '113 - Profesor de Medio Tiempo Asistente'),
	(14, '114 - Profesor de Medio Tiempo Asistente'),
	(15, '115 - Profesor de Medio Tiempo Asistente'),
	(16, '116 - Profesor de Medio Tiempo Asociado'),
	(17, '117 - Profesor de Medio Tiempo Asociado'),
	(18, '118 - Profesor de Medio Tiempo Asociado'),
	(19, '119 - Profesor de Medio Tiempo Titular'),
	(20, '120 - Profesor de Medio Tiempo Titular'),
	(21, '121 - Profesor de Medio Tiempo Titular'),
	(22, '122 - Técnico Acádemico de Asignatura'),
	(23, '123 - Técnico Acádemico de Asignatura'),
	(24, '124 - Técnico Acádemico de Asignatura'),
	(25, '125 - Técnico Acádemico de Asignatura');

-- Volcando estructura para tabla sgs_db.estado
DROP TABLE IF EXISTS `estado`;
CREATE TABLE IF NOT EXISTS `estado` (
  `idEstado` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstado`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.estado: ~2 rows (aproximadamente)
DELETE FROM `estado`;
INSERT INTO `estado` (`idEstado`, `status`) VALUES
	(1, 'Activo'),
	(2, 'Inactivo');

-- Volcando estructura para tabla sgs_db.estado_solicitud
DROP TABLE IF EXISTS `estado_solicitud`;
CREATE TABLE IF NOT EXISTS `estado_solicitud` (
  `idEstado_Solicitud` int(11) NOT NULL AUTO_INCREMENT,
  `DescripcionEstado` varchar(50) NOT NULL,
  PRIMARY KEY (`idEstado_Solicitud`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.estado_solicitud: ~8 rows (aproximadamente)
DELETE FROM `estado_solicitud`;
INSERT INTO `estado_solicitud` (`idEstado_Solicitud`, `DescripcionEstado`) VALUES
	(1, 'Pendiente de firma'),
	(2, 'Firma parcial'),
	(3, 'Firmada'),
	(4, 'En curso'),
	(5, 'Reporte pendiente'),
	(6, 'Finalizada'),
	(7, 'En correción'),
	(8, 'Cancelada');

-- Volcando estructura para procedimiento sgs_db.firmar_solicitud
DROP PROCEDURE IF EXISTS `firmar_solicitud`;
DELIMITER //
CREATE PROCEDURE `firmar_solicitud`(
	in id_solicitud int,
	in id_usuario int,
	in id_rol int,
	out res int
)
`whole_proc`:
begin
	declare sol_usuario int;
	declare firmo_dir int;
	declare firmo_sub int;
	declare firmo_admi int;
	declare dir_usuario int;
	
	update firmas_solicitud set idUsuario = id_usuario where idRol = id_rol and idSolicitud = id_solicitud;

	select fs.idUsuario is not null and fs.idUsuario = id_usuario into res
	from firmas_solicitud fs 
	where fs.idSolicitud = id_solicitud
	and fs.idRol = id_rol;
	if res != 1 then
		leave whole_proc;
	end if;
	
	update solicitud set idEstado_Solicitud = 2
	where idSolicitud = id_solicitud;

	-- Validar las firmas

	select s.idUsuario into sol_usuario
	from solicitud s
	where s.idSolicitud = id_solicitud;

	select r.Usuario_idUsuario into dir_usuario
	from rol r 
	where r.idRol = 2;
	
	-- Revisar si el usuario que la creó no es el director
	if sol_usuario != dir_usuario then

		select 2 in (
			select fs.idRol
			from firmas_solicitud fs
			where fs.idSolicitud = id_solicitud and fs.idUsuario is not null
		) into firmo_dir;
		
		-- Si no es el director, con que haya firmado el director es suficiente
		if firmo_dir = 1 then
		
			update solicitud set idEstado_Solicitud = 3
			where idSolicitud = id_solicitud;
		
			select 3 = (
				select s.idEstado_Solicitud
				from solicitud s
				where s.idSolicitud = id_solicitud
			) into res;
		
		else
		
			select 2 = (
				select s.idEstado_Solicitud
				from solicitud s
				where s.idSolicitud = id_solicitud
			) into res;
		
		end if;
	else
		
		-- Si es el director, solo se acepta si firmó el subdirector y administración
		select 1 in (
			select fs.idRol
			from firmas_solicitud fs
			where fs.idSolicitud = id_solicitud and fs.idUsuario is not null
			) into firmo_sub;
	
		select 3 in (
			select fs.idRol
			from firmas_solicitud fs
			where fs.idSolicitud = id_solicitud and fs.idUsuario is not null
		) into firmo_admi;
	
		if @firmo_sub = 1 and @firmo_admi = 1 then
		
			update solicitud set idEstado_Solicitud = 3
			where idSolicitud = id_solicitud;
		
			select 3 = (
				select s.idEstado_Solicitud
				from solicitud s
				where s.idSolicitud = id_solicitud
			) into res;
		
		else
		
			select 2 = (
				select s.idEstado_Solicitud
				from solicitud s
				where s.idSolicitud = id_solicitud
			) into res;
		
		end if;
	end if;
END//
DELIMITER ;

-- Volcando estructura para tabla sgs_db.firmas_solicitud
DROP TABLE IF EXISTS `firmas_solicitud`;
CREATE TABLE IF NOT EXISTS `firmas_solicitud` (
  `idFirma` int(11) NOT NULL AUTO_INCREMENT,
  `idSolicitud` int(11) NOT NULL,
  `idRol` int(11) NOT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`idFirma`),
  KEY `fk_Firma_Solicitud2_idx` (`idSolicitud`),
  KEY `fk_Firmas_Solicitud_Rol1_idx` (`idRol`),
  KEY `fk_Firmas_Solicitud_Usuario1_idx` (`idUsuario`),
  CONSTRAINT `fk_Firma_Solicitud2` FOREIGN KEY (`idSolicitud`) REFERENCES `solicitud` (`idSolicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Firmas_Solicitud_Rol1` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Firmas_Solicitud_Usuario1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.firmas_solicitud: ~0 rows (aproximadamente)
DELETE FROM `firmas_solicitud`;

-- Volcando estructura para procedimiento sgs_db.get_usuario
DROP PROCEDURE IF EXISTS `get_usuario`;
DELIMITER //
CREATE PROCEDURE `get_usuario`(in correo varchar(100))
begin

    declare c int;
    select count(*) into @c FROM rol r WHERE r.Correo_rol = correo;

    if @c <= 0 then
        select u.idUsuario, u.Usr_Nombre, u.Ap_Paterno, u.Ap_Materno,
            u.Correo, u.Usr_Pwd, u.Num_Empleado, u.Carrera_idCarrera,
            u.Categoria_idCategoria1, u.Estado_idEstado, 0 as idRol
        from usuario u where u.Correo = correo;
    else
        select r.Usuario_idUsuario as idUsuario, u.Usr_Nombre, u.Ap_Paterno, u.Ap_Materno,
            r.Correo_rol as Correo, r.password as Usr_Pwd, u.Num_Empleado, u.Carrera_idCarrera,
            u.Categoria_idCategoria1, u.Estado_idEstado, r.idRol
        from rol r inner join usuario u
        on r.Usuario_idUsuario = u.idUsuario
        where r.Correo_rol = correo;
    end if;


END//
DELIMITER ;

-- Volcando estructura para procedimiento sgs_db.insert_rol
DROP PROCEDURE IF EXISTS `insert_rol`;
DELIMITER //
CREATE PROCEDURE `insert_rol`(
    in rol varchar(100),
    in idUsuario int,
    in correo varchar(100),
    in pwd varchar(120),
    out res int
)
BEGIN


    -- Insertar un nuevo rol
    INSERT INTO rol(Rol_Descripcion, usuario_idUsuario, Correo_rol,password)
    VALUES (rol, idUsuario, correo,pwd);

    -- Obtener el número de filas afectadas (debe ser 1)
    SELECT ROW_COUNT() INTO res;

END//
DELIMITER ;

-- Volcando estructura para procedimiento sgs_db.insert_solicitud
DROP PROCEDURE IF EXISTS `insert_solicitud`;
DELIMITER //
CREATE PROCEDURE `insert_solicitud`(
    IN nombre_evento VARCHAR(100),
    IN fecha_salida DATETIME,
    IN fecha_regreso DATETIME,
    IN costo FLOAT,
    IN lugar VARCHAR(100),
    IN id_usuario INT(11),
    IN id_carrera INT(11),
    OUT idSolicitud INT(11)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        -- Manejo de excepciones si es necesario
        SET idSolicitud = 0;
    END;

    -- Insertar el dato en la tabla
    INSERT INTO solicitud (
        Nombre_Evento,
        Fecha_Salida,
        Fecha_Regreso,
        Costo,
        Lugar,
        idUsuario,
        idCarrera,
        idEstado_Solicitud
    ) VALUES (
        nombre_evento,
        fecha_salida,
        fecha_regreso,
        costo,
        lugar,
        id_usuario,
        id_carrera,
        1
    );

    -- Obtener el ID del último registro insertado
    SET idSolicitud = LAST_INSERT_ID();
END//
DELIMITER ;

-- Volcando estructura para procedimiento sgs_db.insert_usuario
DROP PROCEDURE IF EXISTS `insert_usuario`;
DELIMITER //
CREATE PROCEDURE `insert_usuario`(
    in nombre varchar(100),
    in ap_paterno varchar(45),
    in ap_materno varchar(45),
    in correo varchar(100),
    in pwd varchar(120),
    in num_empleado int,
    in id_carrera int,
    in id_categoria int,
    out res int
)
BEGIN
    DECLARE user_count INT;
    

    -- Insertar un nuevo usuario
    INSERT INTO usuario(Usr_Nombre, Ap_Paterno, Ap_Materno, Correo, Usr_Pwd, Num_Empleado,
    Carrera_idCarrera, Categoria_idCategoria1, Estado_idEstado)
    VALUES (nombre, ap_paterno, ap_materno, correo, pwd, num_empleado, id_carrera,
    id_categoria, 1);

    -- Obtener el número de filas afectadas (debe ser 1)
    SELECT ROW_COUNT() INTO res;

END//
DELIMITER ;

-- Volcando estructura para tabla sgs_db.recursos
DROP TABLE IF EXISTS `recursos`;
CREATE TABLE IF NOT EXISTS `recursos` (
  `idRecursos` int(11) NOT NULL AUTO_INCREMENT,
  `Rec_Descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`idRecursos`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.recursos: ~5 rows (aproximadamente)
DELETE FROM `recursos`;
INSERT INTO `recursos` (`idRecursos`, `Rec_Descripcion`) VALUES
	(1, 'Hospedaje'),
	(2, 'Transporte'),
	(3, 'Combustible'),
	(4, 'Viáticos'),
	(5, 'Otro');

-- Volcando estructura para tabla sgs_db.rol
DROP TABLE IF EXISTS `rol`;
CREATE TABLE IF NOT EXISTS `rol` (
  `idRol` int(11) NOT NULL AUTO_INCREMENT,
  `Rol_Descripcion` varchar(100) NOT NULL,
  `Usuario_idUsuario` int(11) NOT NULL,
  `Correo_rol` varchar(100) DEFAULT NULL,
  `password` varchar(120) NOT NULL,
  `Carrera_idCarrera` int(11) DEFAULT NULL,
  PRIMARY KEY (`idRol`),
  KEY `fk_Rol_Usuario1_idx` (`Usuario_idUsuario`),
  KEY `fk_Rol_Carrera1_idx` (`Carrera_idCarrera`),
  CONSTRAINT `fk_Rol_Carrera1` FOREIGN KEY (`Carrera_idCarrera`) REFERENCES `carrera` (`idCarrera`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Rol_Usuario1` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.rol: ~16 rows (aproximadamente)
DELETE FROM `rol`;
INSERT INTO `rol` (`idRol`, `Rol_Descripcion`, `Usuario_idUsuario`, `Correo_rol`, `password`, `Carrera_idCarrera`) VALUES
	(1, 'Subdirector', 24, 'subdireccion.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', NULL),
	(2, 'Director', 27, 'direccion.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', NULL),
	(3, 'Administración', 2, 'administracion.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', NULL),
	(4, 'Secretario', 4, 'secretario1.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', NULL),
	(5, 'Secretario', 3, 'secretario2.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', NULL),
	(6, 'Coordinador Computación', 13, 'computacion.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 1),
	(7, 'Coordinador Industrial ', 5, 'industrial.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 2),
	(8, 'Coordinador Electrónica', 6, 'electronica.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 3),
	(9, 'Coordinador Bioingeniería', 7, 'bioingenieria.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 4),
	(10, 'Coordinador Arquitectura', 8, 'arquitectura.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 6),
	(11, 'Coordinador Nanotecnología ', 9, 'coord.nano.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 7),
	(12, 'Coordinador Civil', 10, 'civil.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 8),
	(13, 'Coordinador Software', 12, 'software.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 9),
	(14, 'Posgrado ', 11, 'posgradofie@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 5),
	(15, 'Coordinador Tronco Común Ingeniería', 13, 'tc.ing.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 10),
	(16, 'Coordinador Tronco Común Arquitectura', 14, 'tc.arquitectura.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 11);

-- Volcando estructura para tabla sgs_db.solicitud
DROP TABLE IF EXISTS `solicitud`;
CREATE TABLE IF NOT EXISTS `solicitud` (
  `idSolicitud` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre_Evento` varchar(100) NOT NULL,
  `Fecha_Salida` datetime NOT NULL,
  `Fecha_Regreso` datetime NOT NULL,
  `Costo` float NOT NULL,
  `Lugar` varchar(100) NOT NULL,
  `Reporte_Final` LONGBLOB NULL,
  `Oficio_Sellado` LONGBLOB NULL,
  `idUsuario` int(11) NOT NULL,
  `idCarrera` int(11) NOT NULL,
  `idEstado_Solicitud` int(11) NOT NULL,
  PRIMARY KEY (`idSolicitud`),
  KEY `fk_Solicitud_Usuario2_idx` (`idUsuario`),
  KEY `fk_Solicitud_Carrera1_idx` (`idCarrera`),
  KEY `fk_Solicitud_Estado_Solicitud1_idx` (`idEstado_Solicitud`),
  CONSTRAINT `fk_Solicitud_Carrera1` FOREIGN KEY (`idCarrera`) REFERENCES `carrera` (`idCarrera`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Solicitud_Estado_Solicitud1` FOREIGN KEY (`idEstado_Solicitud`) REFERENCES `estado_solicitud` (`idEstado_Solicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Solicitud_Usuario2` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.solicitud: ~0 rows (aproximadamente)
DELETE FROM `solicitud`;

-- Volcando estructura para tabla sgs_db.solicitud_recursos
DROP TABLE IF EXISTS `solicitud_recursos`;
CREATE TABLE IF NOT EXISTS `solicitud_recursos` (
  `idSolicitudRecursos` int(11) NOT NULL AUTO_INCREMENT,
  `idSolicitud` int(11) NOT NULL,
  `idRecursos` int(11) NOT NULL,
  `Detalles` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`idSolicitudRecursos`,`idSolicitud`,`idRecursos`),
  KEY `fk_Solicitud_has_Recursos_Recursos1_idx` (`idRecursos`),
  KEY `fk_Solicitud_has_Recursos_Solicitud1_idx` (`idSolicitud`),
  CONSTRAINT `fk_Solicitud_has_Recursos_Recursos1` FOREIGN KEY (`idRecursos`) REFERENCES `recursos` (`idRecursos`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Solicitud_has_Recursos_Solicitud1` FOREIGN KEY (`idSolicitud`) REFERENCES `solicitud` (`idSolicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.solicitud_recursos: ~0 rows (aproximadamente)
DELETE FROM `solicitud_recursos`;

-- Volcando estructura para procedimiento sgs_db.update_solicitud
DROP PROCEDURE IF EXISTS `update_solicitud`;
DELIMITER //
CREATE PROCEDURE `update_solicitud`(
    in id_solicitud INT,
    in nombre_evento VARCHAR(100),
    in fecha_salida DATETIME,
    in fecha_regreso DATETIME,
    in costo FLOAT,
    in lugar VARCHAR(100),
    in id_carrera INT,
    OUT resultado INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET resultado = 0;
    END;

    UPDATE solicitud
    SET Nombre_Evento=nombre_evento, 
        Fecha_Salida=fecha_salida, 
        Fecha_Regreso=fecha_regreso, 
        Costo=costo, 
        Lugar=lugar,
        idCarrera=id_carrera, 
        idEstado_Solicitud=1
    WHERE idSolicitud=id_solicitud;

    SET resultado = 1;
END//
DELIMITER ;

-- Volcando estructura para procedimiento sgs_db.update_usuario
DROP PROCEDURE IF EXISTS `update_usuario`;
DELIMITER //
CREATE PROCEDURE `update_usuario`(
    IN id_usuario INT,
    IN nombre        VARCHAR(100),
    IN ap_paterno VARCHAR(45),
    IN ap_materno VARCHAR(45),
    IN p_carrera INT,
    IN p_categoria INT,
    IN p_estado INT,
    OUT resultado INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET resultado = 0;
    END;

    UPDATE usuario
    SET  Usr_Nombre=nombre ,Ap_Paterno = ap_paterno, Ap_Materno = ap_materno,
    Carrera_idCarrera = p_carrera, Categoria_idCategoria1 = p_categoria,
    Estado_idEstado = p_estado
    WHERE idUsuario = id_usuario;

    SET resultado = 1;
END//
DELIMITER ;

-- Volcando estructura para tabla sgs_db.usuario
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `Usr_Nombre` varchar(100) NOT NULL,
  `Ap_Paterno` varchar(45) NOT NULL,
  `Ap_Materno` varchar(45) NOT NULL,
  `Correo` varchar(100) NOT NULL,
  `Usr_Pwd` varchar(120) NOT NULL,
  `Num_Empleado` int(6) NOT NULL,
  `Carrera_idCarrera` int(11) NOT NULL,
  `Categoria_idCategoria1` int(11) NOT NULL,
  `Estado_idEstado` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `Correo_UNIQUE` (`Correo`),
  UNIQUE KEY `Num_Empleado_UNIQUE` (`Num_Empleado`),
  KEY `fk_Usuario_Carrera1_idx` (`Carrera_idCarrera`),
  KEY `fk_Usuario_Categoria2_idx` (`Categoria_idCategoria1`),
  KEY `fk_Usuario_Estado1_idx` (`Estado_idEstado`),
  CONSTRAINT `fk_Usuario_Carrera1` FOREIGN KEY (`Carrera_idCarrera`) REFERENCES `carrera` (`idCarrera`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_Categoria2` FOREIGN KEY (`Categoria_idCategoria1`) REFERENCES `categoria` (`idCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_Estado1` FOREIGN KEY (`Estado_idEstado`) REFERENCES `estado` (`idEstado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.usuario: ~31 rows (aproximadamente)
DELETE FROM `usuario`;
INSERT INTO `usuario` (`idUsuario`, `Usr_Nombre`, `Ap_Paterno`, `Ap_Materno`, `Correo`, `Usr_Pwd`, `Num_Empleado`, `Carrera_idCarrera`, `Categoria_idCategoria1`, `Estado_idEstado`) VALUES
	(1, 'Omar', 'Herrera', 'Santos', 'omar.herrera13@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20053, 1, 1, 1),
	(2, 'Roberto', 'Guzman', 'Gonzale', 'robertoGuzman@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20054, 2, 2, 1),
	(3, 'Hugo', 'Dorante', 'Alarco', 'hufo@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20454, 2, 4, 1),
	(4, 'Edgar', 'Nava', 'Dorantes', 'luis@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20455, 1, 1, 1),
	(5, 'Ana', 'González', 'Sánchez', 'ana@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20456, 2, 3, 1),
	(6, 'Carlos', 'Gonzalez', 'Altamirano', 'carlos@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20457, 2, 2, 1),
	(7, 'Maria', 'Rodríguez', 'López', 'maria@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20458, 2, 2, 1),
	(8, 'Daniel', 'Gómez', 'Hernández', 'daniel@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20459, 1, 2, 1),
	(9, 'Laura', 'Sánchez', 'González', 'laura@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20460, 2, 2, 1),
	(10, 'Sofía', 'Martínez', 'Ortiz', 'sofia@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20461, 2, 2, 1),
	(11, 'Elena', 'García', 'Pérez', 'elena@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20462, 2, 2, 1),
	(12, 'Javier', 'Fernández', 'Gómez', 'pedro@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20463, 4, 2, 1),
	(13, 'Alejandro', 'López', 'Sánchez', 'alejandro@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20464, 2, 2, 1),
	(14, 'Isabel', 'Martínez', 'Díaz', 'isabel@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20465, 2, 2, 1),
	(15, 'Fernanda', 'Hernández', 'Rodríguez', 'fernanda@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20466, 2, 2, 1),
	(16, 'Daniela', 'Sánchez', 'González', 'ricardo@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 20467, 2, 2, 1),
	(17, 'Omar', 'Herrera', 'Santos', 'ppruea@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 32432, 1, 1, 1),
	(18, 'marta', 'Herre', 'santigo', 'yomara@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 43532, 3, 1, 1),
	(19, 'Karla', 'Hernandez', 'Pacheco', 'karina@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 23422, 4, 1, 1),
	(20, 'Ana', 'Navarro', '', 'analuz@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 54364, 1, 1, 1),
	(21, 'Gines', 'Acevedo', 'Venegas', 'yfamil@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 34234, 1, 1, 1),
	(22, 'Marcelo', 'Sánchez', 'Martinez', 'yfdds@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 34534, 1, 3, 1),
	(23, 'Marco', 'Perez', 'Lopez', 'uana@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 42425, 1, 1, 1),
	(24, 'Angel', 'Herrera', 'Santos', 'angel@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 45435, 1, 1, 1),
	(25, 'Andrea m', 'Santiago', 'Cruz', 'andrea@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 12321, 1, 2, 1),
	(26, 'Osiel', 'Santos', 'Ocampo', 'angel43@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 23245, 1, 1, 1),
	(27, 'Andrea', 'Santiago', 'Cruz', 'andrea2@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 53453, 1, 1, 1),
	(28, 'admin', 'Guzman', 'Gonzale', 'director@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 12121, 1, 1, 1),
	(29, 'Roberto', 'Guzman', 'Cruz', 'oma54r@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 12333, 1, 5, 1),
	(30, 'Karina', 'Hernandez', 'Cruz', 'karina07@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 55544, 3, 1, 1),
	(31, 'Roberto', 'Guzman', 'Gonzalez', 'roberto.guzman.gonzalez@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E', 22323, 1, 1, 1);


-- Permisos para borrar 
GRANT DELETE ON sgs_db.solicitud_recursos TO 'spring_user'@'localhost';
GRANT DELETE ON sgs_db.act_asociada_solicitud TO 'spring_user'@'localhost';

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;