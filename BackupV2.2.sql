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
  `Cat_Descripcion` varchar(50) NOT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Volcando datos para la tabla sgs_db.categoria: ~6 rows (aproximadamente)
DELETE FROM `categoria`;
INSERT INTO `categoria` (`idCategoria`, `Cat_Descripcion`) VALUES
	(1,   '101 - Profesor de Asignatura'),
	(2,   '102 - Profesor de Asignatura'),
	(3,   '103 - Profesor de Asignatura'),
	(4,   '104 - Profesor de Tiempo Completo Asistente'),
	(5,   '105 - Profesor de Tiempo Completo Asistente'),
	(6,   '106 - Profesor de Tiempo Completo Asistente'),
	(7,   '107 - Profesor de Tiempo Completo Asociado'),
	(8,   '108 - Profesor de Tiempo Completo Asociado'),
	(9,   '109 - Profesor de Tiempo Completo Asociado'),
	(10,  '110 - Profesor de Tiempo Completo Titular'),
	(11,  '111 - Profesor de Tiempo Completo Titular'),
	(12,  '112 - Profesor de Tiempo Completo Titular'),
	(13,  '113 - Profesor de Medio Tiempo Asistente'),
	(14,  '114 - Profesor de Medio Tiempo Asistente'),
	(15,  '115 - Profesor de Medio Tiempo Asistente'),
	(16,  '116 - Profesor de Medio Tiempo Asociado'),
	(17,  '117 - Profesor de Medio Tiempo Asociado'),
	(18,  '118 - Profesor de Medio Tiempo Asociado'),
	(19,  '119 - Profesor de Medio Tiempo Titular'),
	(20,  '120 - Profesor de Medio Tiempo Titular'),
	(21,  '121 - Profesor de Medio Tiempo Titular'),
	(22,  '122 - Técnico Acádemico de Asignatura'),
	(23,  '123 - Técnico Acádemico de Asignatura'),
	(24,  '124 - Técnico Acádemico de Asignatura'),
	(25,  '125 - Técnico Acádemico de Asignatura'),
	(26,  '164 - Técnico Acádemico'),
  (27,  '166 - Técnico Acádemico'),
  (28,  '167 - Técnico Acádemico'),
	(29,  '168 - Técnico Acádemico de Tiempo Completo'),
	(30,  '172 - Técnico Acádemico de Tiempo Completo'),
	(31,  '175 - Técnico Acádemico de Tiempo Completo'),
	(32,  '509 - Profesor'),
  (33,  '165 - Técnico Acádemico');


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
	(1, 'Subdirector', 7, 'subdireccion.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', NULL),
	(2, 'Director', 10, 'direccion.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', NULL),
	(3, 'Administración', 227, 'administracion.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', NULL),
	(4, 'Secretario', 228, 'secretario1.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', NULL),
	(5, 'Secretario', 229, 'secretario2.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', NULL),
	(6, 'Coordinador Computación', 15, 'computacion.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 1),
	(7, 'Coordinador Industrial ', 111, 'industrial.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 2),
	(8, 'Coordinador Electrónica', 26, 'electronica.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 3),
	(9, 'Coordinador Bioingeniería', 123, 'bioingenieria.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 4),
	(10, 'Coordinador Arquitectura', 171, 'arquitectura.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 6),
	(11, 'Coordinador Nanotecnología ', 97, 'coord.nano.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 7),
	(12, 'Coordinador Civil', 56, 'civil.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 8),
	(13, 'Coordinador Software', 13, 'software.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 9),
	(14, 'Posgrado ', 130, 'posgradofie@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 5),
	(15, 'Coordinador Tronco Común Ingeniería', 42, 'tc.ing.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 10),
	(16, 'Coordinador Tronco Común Arquitectura', 195, 'tc.arquitectura.fiad@uabc.edu.mx', '$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs', 11);

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
    in password VARCHAR(120),
    OUT resultado INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET resultado = 0;
    END;

   UPDATE usuario
    SET  Usr_Nombre=nombre, Ap_Paterno = ap_paterno, Ap_Materno = ap_materno,
    Carrera_idCarrera = p_carrera, Categoria_idCategoria1 = p_categoria,
    Estado_idEstado = p_estado, Usr_Pwd = password
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
  `Ap_Materno` varchar(45) NULL,
  `Correo` varchar(100) NOT NULL,
  `Usr_Pwd` varchar(120) NOT NULL,
  `Num_Empleado` int(6) NOT NULL ,
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
	(1,'ROBERTO','CONTE','GALVAN','roberto.conte@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        9220,   3,  3,1),
  (2,'ELIA ALFONSINA','CORRAL','AMAO','corral.elia@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    8672,   6,  3,1),
  (3,'JOSÉ EDGAR','ARROYO','ORTEGA','edgar@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                            9743,   2,  29,1),
  (4,'JOEL MELCHOR','OJEDA','RUIZ','joel.ojeda@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        9752,   8,  11,1),
  (5,'OSCAR ROBERTO','LOPEZ','BONILLA','olopez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        10507,  4,  32,1),
  (6,'JOSE LUIS JAVIER','SANCHEZ','GONZALEZ','javsanchez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                              10523,  2,  11,1),
  (7,'HUMBERTO','CERVANTES','DE AVILA','hcervantes@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    11779,  3,  12,1),
  (8,'CARLOS SAUL','LOPEZ','SANCHEZ','clopez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                          12086,  10, 3,1),
  (9,'HORACIO LUIS','MARTINEZ','REYES','hmartine@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                      12644,  3,  12,1),
  (10,'MIGUEL ENRIQUE','MARTINEZ','ROSAS','emartine@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   12697,  3,  12,1),
  (11,'JUAN DE DIOS','SANCHEZ','LOPEZ','jddios@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        13524,  3,  12,1),
  (12,'JUAN IVAN','NIETO','HIPOLITO','jnieto@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                          14177,  1,  12,1),
  (13,'VICTOR RAFAEL','NAZARIO VELAZQUEZ','MEJIA','vvmejia@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                            14216,  9,  10,1),
  (14,'ROGELIO','REYES','SERRANO','roreyes@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                            14441,  1,  25,1),
  (15,'LUZ EVELIA','LOPEZ','CHICO','evelia@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                            14716,  1,  11,1),
  (16,'HAYDEE','MELENDEZ','GUILLEN','haydee@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                           14719,  1,  11,1),
  (17,'BENITO','OROZCO','SERNA','benito.orozco@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        15081,  1,  3,1),
  (18,'VENERANDA GUADALUPE','GARCES','CHAVEZ','veneranda.garces@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                       15152,  4,  3,1),
  (19,'OCTAVIO','TELLES','HIRSCH','otellez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                            15293,  4,  3,1),
  (20,'FEGDHA SUEMY','FRANCO','ORLAYNETA','suemy@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                      15336,  10, 3,1),
  (21,'ALBERTO','PARRA','MEZA','albertoparra@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                          15630,  8,  11,1),
  (22,'MARGARITA','SARLAT','SANCHEZ','sarlat@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                          15685,  1,  25,1),
  (23,'SERGIO OMAR','INFANTE','PRIETO','sinfante@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                      16083,  1,  12,1),
  (24,'JUAN PABLO','TORRES','HERRERA','pablotorres@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    16219,  1,  12,1),
  (25,'CLAUDIA','RIVERA','TORRES','claudia_rivera@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     16305,  6,  11,1),
  (26,'ROSA MARTHA','LOPEZ','GUTIERREZ','roslopez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     16545,  3,  12,1),
  (27,'PEDRO','PEREZ','RUIZ','pedro@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                                   16559,  10, 3,1),
  (28,'ROSALBA','PINTO','','rosalba.pinto@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                             16902,  6,  3,1),
  (29,'JAIME','GARCIA','TOSCANO','jaime.garcia.toscano@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                16907,  8,  3,1),
  (30,'ISRAEL','ROCHA','MENDOZA','israel.rocha@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        17068,  7,  3,1),
  (31,'JAVIER','SANDOVAL','FELIX','javier.sandoval60@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  17130,  6,  3,1),
  (32,'LOURDES ESTELA','SANCHEZ','MORENO','estela@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     17620,  10  ,3,1),
  (33,'FRANCISCO JAVIER','DOMINGUEZ','CAMARENA','fdominguez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                           17679,  10  ,3,1),
  (34,'EVERARDO','INZUNZA','GONZALEZ','einzunza@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                       17780,  3,  12,1),
  (35,'MANUEL MOISES','MIRANDA','VELASCO','mmiranda@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   18081,  3,  11,1),
  (36,'MIGUEL','VIDAL','JAIME','miguel.vidal@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                          18975,  8,  3,1),
  (37,'GABRIEL','ORTIZ','ALVARADO','gortiz@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                            18609,  3,  29,1),
  (38,'SAUL','MLADOSICH','DIAZ','saul.mladosich@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                       19084,  6,  3,1),
  (39,'CARLOS','GOMEZ','AGIS','cgomez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                                 18610,  3,  10,1),
  (40,'MABEL','VAZQUEZ','BRISENO','mabel.vazquez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                      19234,  1,  11,1),
  (41,'ELITANIA','JIMENEZ','GARCIA','ejimenez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                         18622,  1,  12,1),
  (42,'CLAUDIA SOLEDAD','HERRERA','OLIVA','cherrera@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   19356,  8,  12,1),
  (43,'CHRISTIAN XAVIER','NAVARRO','COTA','cnavarro@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   18674,  1,  12,1),
  (44,'EDUARDO','CESENA','BELTRAN','lalo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                              19808,  1,  3,1),
  (45,'MANUEL','JIMENEZ','OROZCO','manuel@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                             18689,  1,  28,1),
  (46,'ODIN ISAAC','MELING','LOPEZ','odme@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                             20053,  1,  29,1),
  (47,'CARLOS','FLORES','ABURTO','cflores52@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                           18961,  8,  3,1),
  (48,'RAQUEL','MU¶IZ','SALAZAR','ramusal@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                             20174,  5,  3,1),
  (49,'CLAUDIA','FIMBRES','CASTRO','claudia.fimbres.castro@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                            19036,  10  ,3,1),
  (50,'JAVIER','VERA','CHAVEZ','javier.vera.chavez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    20219,  10  ,3,1),
  (51,'RICARDO','SANCHEZ','VERGARA','ricardo.sanchez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  19177,  8,  10,1),
  (52,'JULIO CESAR','CANO','GUTIERREZ','jcano@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                         20418,  2,  11,1),
  (53,'PEDRO','NUNEZ','YEPIZ','pedro_yepiz@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                            19262,  1,  3,1),
  (54,'CLAUDIA','CAMARGO','WILSON','ccamargo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                          20494,  2,  12,1),
  (55,'CLAUDIA MARIANA','GOMEZ','GUTIERREZ','cmgomezg@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 19614,  4,  11,1),
  (56,'JOSE RUBEN','CAMPOS','GAYTAN','rcampos@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                         20539,  8,  12,1),
  (57,'EDGAR RENE','ORTIZ','MORENO','rortiz@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                           19971,  10  ,3,1),
  (58,'YOLANDA ANGELICA','BAEZ','LOPEZ','yolanda@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                      20672,  2,  12,1),
  (59,'ENRIQUE EFREN','GARCIA','GUERRERO','eegarcia@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   20161,  7,  12,1),
  (60,'FRANCISCO JAVIER','RODRIGUEZ','SANTOYO','psicxavier@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                            20837,  10  ,3,1),
  (61,'JESUS EVERARDO','OLGUIN','TIZNADO','jeol79@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     20176,  2,  12,1),
  (62,'LILIANA','CARDOZA','AVENDANO','lcardoza@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        21086,  3,  11,1),
  (63,'CLAUDIA MARCELA','CALDERON','AGUILERA','claudiacalderon@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                        20362,  6,  12,1),
  (64,'JORGE OCTAVIO','MATA','RAMIREZ','jorge.mata@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    21599,  7,  12,1),
  (65,'CLAUDIA LETICIA','SANCHEZ','MORA','claudia.leticia.sanchez.mora@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                20457,  10  ,3,1),
  (66,'IRMA ALEJANDRA','AMAYA','PATRON','iamaya@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                       21953,  1,  10,1),
  (67,'TANIA ANGELICA','LOPEZ','CHICO','tania.lopez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   20496,  10  ,3,1),
  (68,'GABRIELA DE JESUS','BACELIS','DORANTES','gabriela.bacelis@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                      22341,  6,  3,1),
  (69,'CESAR','CRUZ','HERNANDEZ','cesar.cruz@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                          20605,  3,  3,1),
  (70,'ALEJANDRO','CANDELA','MODESTO','acandela@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                       22374,  6,  3,1),
  (71,'DIEGO ALFREDO','TLAPA','MENDOZA','diegotlapa@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   20673,  2,  12,1),
  (72,'LINDA ELIZABETH','SALAZAR','GUTIERREZ','salazar.linda@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                          22423,  6,  3,1),
  (73,'ANA ERIKA','RUIZ','ARELLANO','erika.ruiz@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                       21013,  6,  3,1),
  (74,'JOSUE ERNESTO','CASTILLO','ARANDA','jcastilloaranda@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                            22470,  4,  3,1),
  (75,'JORGE','LIMON','ROMERO','jorge.limon@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                           21096,  2,  12,1),
  (76,'EUNICE','VARGAS','VIVEROS','eunice@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                             22504,  7,  11,1),
  (77,'MABEL','SANCHEZ','MONDRAGON','mabel.sanchez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    21735,  4,  3,1),
  (78,'BARBARA','ARREOLA','ALVARADO','barbara.arreola@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 22824,  6,  3,1),
  (79,'CLAUDIA MARGARITA','RANGEL','LOPEZ','rangel.claudia@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                            22038,  9,  3,1),
  (80,'JUAN ANTONIO','VAZQUEZ','SANCHEZ','jantonio@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    22993,  6,  3,1),
  (81,'RENE BRUNO DANIEL','BARAJAS','GARRIDO','bruno.barajas@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                          22342,  6,  3,1),
  (82,'TATIANA NENETZEN','OLIVARES','BANUELOS','tatiana.olivares@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                      23093,  4,  3,1),
  (83,'ANNIA PAOLA','MONTES','MENDOZA','paola.montes@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  22415,  6,  3,1),
  (84,'CECILIA','RODRIGUEZ','SERRATO','cecilia.rodriguez.serrato@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                      23176,  3,  3,1),
  (85,'ALBERTO','ALMEJO','ORNELAS','alberto.almejo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    22454,  5,  3,1),
  (86,'SERGIO EDUARDO','QUIROZ MARTINEZ','LIZARRAGA','quirozs@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         23380,  6,  3,1),
  (87,'ADRIAN','ARELLANO','DELGADO','adrian.arellano@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  22498,  3,  3,1),
  (88,'JUAN FRANCISCO','OROZCO','SERNA','orozco.juan@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  23504,  6,  3,1),
  (89,'MIRNA PATRICIA','PEREZ','ECHAURI','mirna.perez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 22726,  6,  3,1),
  (90,'AMALIA DE JESUS','MONZON','CARDENAS','amalia.monzon@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                            23605,  2,  3,1),
  (91,'AIXA AUBE','CARDENAS','TORRES','aixa.cardenas@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  22870,  1,  3,1),
  (92,'CESAR ALBERTO','LOPEZ','MERCADO','lopez.cesar7@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 23644,  4,  10,1),
  (93,'GENARO','NAJERA','OCEGUEDA','genaro.najera@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     23023,  6,  3,1),
  (94,'NOEMI','CASTELLANO','COLLINS','noemi.castellano@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                23732,  2,  25,1),
  (95,'JULIAN ISRAEL','AGUILAR','DUQUE','julian.aguilar@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                               23156,  2,  11,1),
  (96,'ISRAEL RAMIRO','RIVERA','HERNANDEZ','ramiro.rivera@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                             23761,  6,  3,1),
  (97,'GUILLERMO','AMAYA','PARRA','amaya@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                              23181,  7,  12,1),
  (98,'EDUARDO','BUENROSTRO','MORAN','eduardo.buenrostro@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                              23801,  6,  3,1),
  (99,'MIGUEL ANGEL','CUESTA','ORTEGA','mcuesta@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                       23416,  6,  3,1),
  (100,'MARIA ISABEL','OJEDA','GUTIERREZ','maria.ojeda@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                23906,  10  ,3,1),
  (101,'JOSE ENRIQUE','BUSTILLOS','ROQUENI','ebustillos@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                               23583,  6,  3,1),
  (102,'RUBEN CESAR','VILLARREAL','SANCHEZ','ruben.villarreal@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         24096,  4,  11,1),
  (103,'FATIMA','PEREZ','OSUNA','fatima.perez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                           23606,  10  ,3,1),
  (104,'VICTOR MANUEL','MORINEAU','CARRILLO','victor.morineau@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         24189,  6,  3,1),
  (105,'ABRAHAM','FLORES','VERGARA','venumc@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                           23666,  3,  3,1),
  (106,'MARISOL','MONTIEL','BERUMEN','marisol.montiel@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 24194,  6,  3,1),
  (107,'ALEX ALBERTO','ESPARZA','YUREAR','alex.esparza@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                23760,  6,  3,1),
  (108,'YAZMIN','ROMERO','VASQUEZ','yromero97@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                         24274,  3,  26,1),
  (109,'MONICA PATRICIA','RODRIGUEZ','ALVAREZ','monica.patricia.rodriguez.alvarez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',     23799,  6,  3,1),
  (110,'ABIMAEL ELIAQUIM','SALCEDO','GARCIA','abimael.salcedo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                          24351,  3,  3,1),
  (111,'VICTOR MANUEL','JUAREZ','LUNA','juarezv@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        23834,  2,  11,1),
  (112,'MARIA ISABEL','PONCE','CAZARES','iponce87@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                      24671,  4,  3,1),
  (113,'ANGEL','RAMIREZ','FUENTES','ramirez.angel75@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   23972,  10  ,3,1),
  (114,'OSCAR','GONZALEZ','DAVIS','ogdavis@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                            24782,  7,  3,1),
  (115,'DANTE ALBERTO','MAGDALENO','MONCAYO','dante.magdaleno@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         24156,  4,  10,1),
  (116,'ARMANDO','AGUIRRE','VELAZQUEZ','aguirre.armando@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                               24813,  6,  3,1),
  (117,'ARTURO VIDAL','GUTIERREZ','PEREZ','vidal.gutierrez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                            24191,  6,  3,1),
  (118,'JESUS','PI/A','MORENO','jesus.pia@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                             25133,  8,  25,1),
  (119,'ARAM','HAWA','CALVO','aram.hawa@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                               24231,  3,  3,1),
  (120,'DINA MARIA','GONZALEZ','COTA','dina.gonzalez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  25244,  10  ,3,1),
  (121,'LAURA ELIZABETH','GARCIA','MELENDREZ','laura.garcia62@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         24343,  8,  3,1),
  (122,'GUADALUPE','SANDOVAL','GAMBOA','gsandoval26@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   25446,  1,  3,1),
  (123,'DAVID','CERVANTES','VASQUEZ','dcervantes42@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    24466,  4,  10,1),
  (124,'AARON','SANDOVAL','LOPEZ','aaron.sandoval@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     25547,  4,  3,1),
  (125,'ARMANDO','PEREZ','SANCHEZ','armando.perez.sanchez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                             24752,  6,  3,1),
  (126,'ANGEL RICARDO','URIBE','ROMO','angel.uribe@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    25896,  6,  3,1),
  (127,'CHRISTIAN MANUEL','LOERA','SANCHEZ','christian.loera@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                          24795,  10  ,3,1),
  (128,'ALEJANDRA','ORTIZ','MENDOZA','aleortiz.men@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    26141,  4,  3,1),
  (129,'HECTOR','TORREZ','CARRANZA','torrezh@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                          24886,  2,  3,1),
  (130,'PRISCY ALFREDO','LUQUE','MORALES','pluque@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     26228,  4,  12,1),
  (131,'LUIS ANGEL','MONGE','DE LA CRUZ','luis.monge@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  25135,  9,  3,1),
  (132,'NOEMI','ABUNDIZ','CISNEROS','nabundiz@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                         26247,  7,  3,1),
  (133,'HAYDEE','LOPEZ','RODRIGUEZ','lopez.haydee@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     25279,  7,  10,1),
  (134,'SAMARA LILIAN','VILCHIS','MEDINA','samara.vilchis@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                             26360,  6,  3,1),
  (135,'MARIANA','VILLADA','CANELA','mvilladac@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        25507,  7,  3,1),
  (136,'JOSE LUIS','LEON','LUNA','luis.leon@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                           26646,  10  ,3,1),
  (137,'ALVARO ALBERTO','LOPEZ','LAMBRANO','alopezl@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   25792,  8,  12,1),
  (138,'WALTER DANIEL','GARCIA','MORENO','walter.garcia@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                               26706,  6,  3,1),
  (139,'RICARDO ISRAEL','FLORES','BARRERA','rflores8@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  25967,  10, 26,1),
  (140,'MARIA TERESA','MAYORQUIN','MEJIA','mayorquin.mejia@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                            27133,  2,  31,1),
  (141,'ARACELI GUADALUPE','AVI/A','CERVANTES','araceli.avia@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                          26201,  10  ,3,1),
  (142,'MIGUEL ANGEL','MURILLO','ESCOBAR','murillo.miguel90@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                           27197,  3,  3,1),
  (143,'GABRIEL OCTAVIO','LEYVA','HERNANDEZ','leyva.gabriel@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                           26229,  6,  3,1),
  (144,'AURORA GARCIA','GARCIA','DE LEON','auroragarcia@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                               27631,  6,  11,1),
  (145,'RAUL','RAMIREZ','RAMIREZ','raul_ramirez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                       26316,  3,  3,1),
  (146,'FRANKLIN DAVID','MUNOZ','MUNOZ','franklin.muoz@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                27702,  7,  11,1),
  (147,'PAOLA','GONGORA','LUGO','pgongora@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                             26639,  6,  3,1),
  (148,'MARCO ANTONIO','MORALES','COLORES','marco.antonio.morales.colores@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',             27773,  9,  3,1),
  (149,'JOAQUIN HERIBERTO','VILLAVICENCIO','MORENO','joaquin.villavicencio@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',            26668,  4,  3,1),
  (150,'GUADALUPE','VALENZUELA','RODRIGUEZ','guadalupe.valenzuela.rodriguez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',           27839,  6,  3,1),
  (151,'IXCHEL','DELGADILLO','SANCHEZ','ixchel.delgadillo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                             27007,  10  ,3,1),
  (152,'DE LA TORRE RODRIGUEZ','DANN','SALVADOR','dann.delatorre@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                      27947,  3,  3,1),
  (153,'LEONARDO AMILCAR','CAMARENA','CALDERON','leonardo.camarena@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                    27187,  6,  3,1),
  (154,'JULIO CESAR','RINCON','MARTINEZ','julio.rincon@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                27954,  6,  11,1),
  (155,'HORTENCIA','SILVA','JIMENEZ','hortencia.silva@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 27561,  4,  3,1),
  (156,'FAUSTO','ABUNDIZ','PEREZ','abundizf@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                           27986,  10  ,3,1),
  (157,'ULISES JESUS','TAMAYO','PEREZ','ulises.tamayo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 27681,  7,  10,1),
  (158,'ALFREDO','GONZALEZ','CARRAZCO','agonzalez48@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   28166,  2,  10,1),
  (159,'JOSE JUAN','VILLEGAS','LEON','jvillegas82@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     27763,  8,  10,1),
  (160,'ESPERANZA','GUERRA','ROSAS','esperanza.guerra@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 28450,  4,  3,1),
  (161,'CRISTINA','MALACARA','MERCADO','cristina.malacara@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                             27835,  6,  3,1),
  (162,'CARLOS ALBERTO','ZEPEDA','LUGO','czepeda@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                      28558,  2,  3,1),
  (163,'BONIFACIO ALEJANDRO','CAN','UC','bcan@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                         27863,  7,  3,1),
  (164,'CHRISTIAN','ALDACO','AVENDANO','caldaco@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                       28841,  10  ,3,1),
  (165,'ELMER','CRUZ','MENDOZA','elmer.cruz@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                           27952,  7,  3,1),
  (166,'AURORA EISENIA','RAZO','TOLEDO','aurora.razo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  29109,  2,  3,1),
  (167,'LAURA SUSANA','ZAMUDIO','VEGA','zamudio.laura@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 27963,  6,  11,1),
  (168,'JESUS RAUL','MURILLO','ESPINOZA','raul.murillo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 29166,  7,  3,1),
  (169,'MARCOS EDUARDO','GONZALEZ','TREVIZO','eduardo.gonzalez35@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                      28120,  6,  11,1),
  (170,'JAVIER ALONSO','LOPEZ','MEDINA','javier.alonso.lopez.medina@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                   29318,  7,  25,1),
  (171,'FRANCISCO','FERNANDEZ','MELCHOR','francisco.fernandez.melchor@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                 28199,  6,  11,1),
  (172,'RAMON HIRAM','SALAZAR','GUTIERREZ','hiram.salazar@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                             29412,  2,  3,1),
  (173,'SARA OLIMPIA','TOPETE','MARTINEZ','stopete@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    28478,  10, 33,1),
  (174,'FLOR KARYNA','VENEGAS','VEGA','karyna.venegas@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 29558,  1,  3,1),
  (175,'ALMENDRA','VILLELA Y','MENDOZA','avillela@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     28636,  8,  11,1),
  (176,'HÉCTOR','ZATARAÍN','ACEVES','hector.zatarain@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  29639,  9,  10,1),
  (177,'SOCORRO','JIMENEZ','VALERA','socorro.jimenez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  29086,  4,  3,1),
  (178,'CLELIA','MACIAS','RODRIGUEZ','clelia.macias.rodriguez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         29684,  4,  3,1),
  (179,'HUGO ALEJANDRO','BORBON','NUNEZ','hborbon@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                     29127,  7,  3,1),
  (180,'OMAR PERZEUS','NIETO','HIPOLITO','omar.nieto@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                  29884,  10  ,3,1),
  (181,'KAREN ESTRELLA','MARTINEZ','TORRES','karen.martinez24@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         29295,  6,  11,1),
  (182,'HERMINIO','ESTRADA','FLORES','herminio.estrada@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                29911,  6,  3,1),
  (183,'IRMA','DELGADILLO','SANCHEZ','irma.delgadillo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 29323,  6,  3,1),
  (184,'DAYANIRA SHEIRA','PANIAGUA','MEZA','paniagua.dayanira@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         30121,  4,  10,1),
  (185,'CRISTIAN','COVARRUBIAS','CERDA','cristian.covarrubias@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         29419,  2,  30,1),
  (186,'LIZBETH ERANDI','DE LA CRUZ','LARIOS','delacruzl@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                              30263,  10, 3,1),
  (187,'MANUEL ANTONIO','BARRAZA','GUERRERO','barraza.manuel@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                          29589,  8,  10,1),
  (188,'KAREN NOHEMI','FLORES','PADILLA','floresk1@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    30649,  8,  25,1),
  (189,'PAOLA DENISSE','VELAZQUEZ','PENA','paola.velazquez15@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                          29662,  8,  26,1),
  (190,'WILVERHT','HERNANDEZ','ESTRADA','w30662@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                       30662,  8,  3,1),
  (191,'EMMANUEL','MUNOZ','GARCIA','emmanuel.munoz@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                    29692,  8,  3,1),
  (192,'AILED','MAYMES','MONTES','ailed.montes@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        30762,  3,  3,1),
  (193,'ANA DENISSE','HUERTA','GUTIERREZ','denisse.huerta49@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                           29893,  10  ,3,1),
  (194,'ESTRADA','LECHUGA','JESSICA','estrada.jessica@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 30849,  3,  3,1),
  (195,'MARIEL','ORGANISTA','CAMACHO','organista.mariel@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                               30119,  6,  10,1),
  (196,'ANNY BERENISSE','SANCHEZ','GARAYZAR','anny.sanchez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                            30941,  4,  25,1),
  (197,'CARLOS','BELMAN','RODRIGUEZ','carlos.belman@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   30156,  7,  3,1),
  (198,'HERIAN ALBERTO','LEYVA','MADRIGAL','herian.leyva@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                              31170,  8,  10,1),
  (199,'MIGUEL ANGEL','GOMEZ','FRAGOSO','miguel.gomez9@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                30485,  8,  3,1),
  (200,'TOMAS DE JESUS','ARCE','MANRIQUEZ','arce.tomas@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                30650,  8,  3,1),
  (201,'FELIPE','ORTIZ','HUERTA','ortiz.felipe@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                        30692,  10  ,3,1),
  (202,'JUAN JOSE','CETINA','DENIS','juan.cetina@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                      30817,  3,  3,1),
  (203,'SANDRA BEATRIZ','AGUIRRE','VEGA','sandra.vega@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 30913,  7,  3,1),
  (204,'ANTONIA VENERANDA','HOLSWORTH','RIOS','vholsworth@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                             31122,  6,  3,1),
  (205,'LAYLA BEATRIZ','SANCHEZ','GARCIA','sanchez.layla@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                              31172,  6,  3,1),
  (206,'LUIS FERNANDO','FLORES','GONZALEZ','luis.fernando.flores.gonzalez@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',             31239,  6,  3,1),
  (207,'REINA VIANEY','QUEVEDO','ROBLES','reina.quevedo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                               31272,  2,  3,1),
  (208,'LILIANA IVETH','ALTAMIRANO','DEVORA','liliana.altamirano@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                      31273,  10, 3,1),
  (209,'ANDRES IVAN','SOSA','ANDRADE','ivan.sosa@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                      31300,  6,  3,1),
  (210,'DIANA ITZEL','SOTO','CASTAÑEDA','soto.diana52@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                 31352,  3,  3,1),
  (211,'AGUILAR OJEDA','JOSE','ALONSO','alonso.aguilar@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                31452,  10, 3,1),
  (212,'OSCAR ADRIAN','AGUIRRE','CASTRO','oscar.aguirre@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                               31494,  3,  10,1),
  (213,'MELISSA AIREM','CAZARES','MANRIQUEZ','melissa.cazares88@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                       31620,  2,  3,1),
  (214,'LIDIA YOLANDA','RAMIREZ','RIOS','lidia.ramirez.rios@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                           31623,  2,  25,1),
  (215,'ASTRID','HERNANDEZ','CRUZ','astrid.hernandez.cruz@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                             31788,  10, 3,1),
  (216,'COTA REAL','MARCOS','ALAN','marcos.cota@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                       31792,  3,  3,1),
  (217,'YULITH VANESSA','ALTAMIRANO','FLORES','altamirano.yulith@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                      31846,  9,  3,1),
  (218,'LUIS MARCEL','CELAYA','LOMELI','celaya.luis@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   31938,  9,  3,1),
  (219,'ABHRIL BELINDA','AGUIRRE','BUCKOVECZ','abhril.aguirre@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         31987,  6,  3,1),
  (220,'ALBERTO ENRIQUE','ESTRELLA','CASTRO','alberto.estrella@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                        32083,  6,  3,1),
  (221,'JUANA BERENICE','MONTES','FRAUSTO','juana.montes.frausto@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                      32158,  4,  3,1),
  (222,'JONATAN','CRESPO','RAGLAND','jonatan.crespo@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   32266,  9,  3,1),
  (223,'JOSE EDEN','CASTRO','DELGADO','delgado32323@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                                   32323,  8,  3,1),
  (224,'MAYRA RENATA','CASTRO','VARGAS','mayra.castro16@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                               32326,  8,  3,1),
  (225,'DULCE GABRIELA','VELAZQUEZ','JUAREZ','velazquez.dulce@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         32331,  6,  3,1),
  (226,'JONATHAN RAMON','RAMIREZ','CASTRO','jonathan.ramirez1@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',                         32324,  8,  3,1),
  (227,'MARIA','ARMENDARIZ','FLORES','administracion.fiad@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',1,1,3,1),
  (228,'SECRETARIO ','SECRETARIO','UNO','sec1@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',2,1,3,1),
  (229,'SECRETARIO','SECRETARIO','DOS','sec2.ramirez1@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E',3,1,3,1);

-- Permisos para borrar 
GRANT DELETE ON sgs_db.solicitud_recursos TO 'spring_user'@'localhost';
GRANT DELETE ON sgs_db.act_asociada_solicitud TO 'spring_user'@'localhost';

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
