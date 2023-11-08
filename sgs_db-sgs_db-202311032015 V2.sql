-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: sgs_db
-- ------------------------------------------------------
-- Server version	11.3.0-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- ***********************************************************************************************
-- usuario admin: subdireccion.fiad@uabc.edu.mx  password: 123456789
-- usuario docente: angel@uabc.edu.mx  password: 123456789
-- ***********************************************************************************************

DROP TABLE IF EXISTS `act_asociada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `act_asociada` (
  `idAct_Asociada` int(11) NOT NULL,
  `Act_Nombre` varchar(100) NOT NULL,
  `Descripcion` varchar(45) DEFAULT NULL,
  `Solicitud_idSolicitud` int(11) NOT NULL,
  PRIMARY KEY (`idAct_Asociada`),
  KEY `fk_Act_Asociada_Solicitud1_idx` (`Solicitud_idSolicitud`),
  CONSTRAINT `fk_Act_Asociada_Solicitud1` FOREIGN KEY (`Solicitud_idSolicitud`) REFERENCES `solicitud` (`idSolicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_asociada`
--

LOCK TABLES `act_asociada` WRITE;
/*!40000 ALTER TABLE `act_asociada` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_asociada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrera`
--

DROP TABLE IF EXISTS `carrera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrera` (
  `idCarrera` int(11) NOT NULL,
  `Carrera_Nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idCarrera`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrera`
--

LOCK TABLES `carrera` WRITE;
/*!40000 ALTER TABLE `carrera` DISABLE KEYS */;
INSERT INTO `carrera` VALUES (1,'Ingenieria en Computacion'),(2,'Ingenieria Industrial'),(3,'Ingenieria Electronica'),(4,'Bioingenieria');
/*!40000 ALTER TABLE `carrera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `idCategoria` int(11) NOT NULL,
  `Cat_Descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Profesor de Asignatura'),(2,'Profesor de Tiempo Completo Asistente'),(3,'Profesor de Tiempo Completo Asociado'),(4,'Profesor de Tiempo Completo Titular'),(5,'Profesor de Medio Tiempo Asistente'),(6,'Profesor de Medio Tiempo Asociado');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado` (
  `idEstado` int(11) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES (1,'Activo'),(2,'Inactivo');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento` (
  `idEvento` int(11) NOT NULL,
  `Nombre_Evento` varchar(120) NOT NULL,
  PRIMARY KEY (`idEvento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `firmas_doc`
--

DROP TABLE IF EXISTS `firmas_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `firmas_doc` (
  `idFirma` int(11) NOT NULL,
  `Estado_Firma` varchar(45) NOT NULL,
  `Solicitud_idSolicitud` int(11) NOT NULL,
  `Autoridad_Firma` varchar(45) NOT NULL,
  PRIMARY KEY (`idFirma`),
  KEY `fk_Firma_Solicitud2_idx` (`Solicitud_idSolicitud`),
  CONSTRAINT `fk_Firma_Solicitud2` FOREIGN KEY (`Solicitud_idSolicitud`) REFERENCES `solicitud` (`idSolicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `firmas_doc`
--

LOCK TABLES `firmas_doc` WRITE;
/*!40000 ALTER TABLE `firmas_doc` DISABLE KEYS */;
/*!40000 ALTER TABLE `firmas_doc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recursos`
--

DROP TABLE IF EXISTS `recursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recursos` (
  `idRecursos` int(11) NOT NULL,
  `Rec_Descripcion` varchar(100) NOT NULL,
  `Solicitud_idSolicitud` int(11) NOT NULL,
  PRIMARY KEY (`idRecursos`),
  KEY `fk_Recursos_Solicitud1_idx` (`Solicitud_idSolicitud`),
  CONSTRAINT `fk_Recursos_Solicitud1` FOREIGN KEY (`Solicitud_idSolicitud`) REFERENCES `solicitud` (`idSolicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recursos`
--

LOCK TABLES `recursos` WRITE;
/*!40000 ALTER TABLE `recursos` DISABLE KEYS */;
/*!40000 ALTER TABLE `recursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte_comision`
--

DROP TABLE IF EXISTS `reporte_comision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte_comision` (
  `idReporte_Comision` int(11) NOT NULL,
  `Rep_Folio` varchar(45) NOT NULL,
  `Rep_Fecha` datetime NOT NULL,
  `Motivo` text NOT NULL,
  PRIMARY KEY (`idReporte_Comision`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte_comision`
--

LOCK TABLES `reporte_comision` WRITE;
/*!40000 ALTER TABLE `reporte_comision` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporte_comision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte_final`
--

DROP TABLE IF EXISTS `reporte_final`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte_final` (
  `idReporte_final` int(11) NOT NULL,
  `Direccion` varchar(100) NOT NULL,
  `Solicitud_idSolicitud` int(11) NOT NULL,
  PRIMARY KEY (`idReporte_final`),
  KEY `fk_Reporte_final_Solicitud1_idx` (`Solicitud_idSolicitud`),
  CONSTRAINT `fk_Reporte_final_Solicitud1` FOREIGN KEY (`Solicitud_idSolicitud`) REFERENCES `solicitud` (`idSolicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte_final`
--

LOCK TABLES `reporte_final` WRITE;
/*!40000 ALTER TABLE `reporte_final` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporte_final` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL,
  `Rol_Descripcion` varchar(100) NOT NULL,
  `Usuario_idUsuario` int(11) NOT NULL,
  `Correo_rol` varchar(100) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`idRol`),
  UNIQUE KEY `idRol_UNIQUE` (`idRol`),
  KEY `fk_Rol_Usuario1_idx` (`Usuario_idUsuario`),
  CONSTRAINT `fk_Rol_Usuario1` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'Subdirector',24,'subdireccion.fiad@uabc.edu.mx','$argon2id$v=19$m=4096,t=3,p=1$CzlFyDYURLQOymmgDwXDWgefDXel+kCY44eyTY8tUns$5AJ8WEVOaSe+4H9v0buVy9CNgcNnEvBdGUimWoOfGbs');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solicitud`
--

DROP TABLE IF EXISTS `solicitud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solicitud` (
  `idSolicitud` int(11) NOT NULL,
  `Foliio` varchar(45) NOT NULL,
  `Fecha_Salida` datetime NOT NULL,
  `Fecha_Regreso` varchar(45) NOT NULL,
  `Costo` varchar(45) NOT NULL,
  `Usuario_idUsuario` int(11) NOT NULL,
  `Evento_idEvento` int(11) NOT NULL,
  `Reporte_Comision_idReporte_Comision` int(11) NOT NULL,
  PRIMARY KEY (`idSolicitud`),
  KEY `fk_Solicitud_Usuario2_idx` (`Usuario_idUsuario`),
  KEY `fk_Solicitud_Evento2_idx` (`Evento_idEvento`),
  KEY `fk_Solicitud_Reporte_Comision1_idx` (`Reporte_Comision_idReporte_Comision`),
  CONSTRAINT `fk_Solicitud_Evento2` FOREIGN KEY (`Evento_idEvento`) REFERENCES `evento` (`idEvento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Solicitud_Reporte_Comision1` FOREIGN KEY (`Reporte_Comision_idReporte_Comision`) REFERENCES `reporte_comision` (`idReporte_Comision`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Solicitud_Usuario2` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitud`
--

LOCK TABLES `solicitud` WRITE;
/*!40000 ALTER TABLE `solicitud` DISABLE KEYS */;
/*!40000 ALTER TABLE `solicitud` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `Usr_Nombre` varchar(100) NOT NULL,
  `Ap_Paterno` varchar(45) NOT NULL,
  `Ap_Materno` varchar(45) NOT NULL,
  `Correo` varchar(100) NOT NULL,
  `Num_Empleado` int(6) NOT NULL,
  `Carrera_idCarrera` int(11) NOT NULL,
  `Categoria_idCategoria1` int(11) NOT NULL,
  `Estado_idEstado` int(11) NOT NULL,
  `Usr_Pwd` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `idUsuario_UNIQUE` (`idUsuario`),
  UNIQUE KEY `Correo_UNIQUE` (`Correo`),
  UNIQUE KEY `Num_Empleado_UNIQUE` (`Num_Empleado`),
  KEY `fk_Usuario_Carrera1_idx` (`Carrera_idCarrera`),
  KEY `fk_Usuario_Categoria2_idx` (`Categoria_idCategoria1`),
  KEY `fk_Usuario_Estado1_idx` (`Estado_idEstado`),
  CONSTRAINT `fk_Usuario_Carrera1` FOREIGN KEY (`Carrera_idCarrera`) REFERENCES `carrera` (`idCarrera`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_Categoria2` FOREIGN KEY (`Categoria_idCategoria1`) REFERENCES `categoria` (`idCategoria`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_Estado1` FOREIGN KEY (`Estado_idEstado`) REFERENCES `estado` (`idEstado`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Omar','Herrera','Santos','omar.herrera13@uabc.edu.mx',20053,1,1,2,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(2,'Roberto','Guzman','Gonzale','robertoGuzman@uabc.edu.mx',20054,2,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(3,'Hugo','Dorante','Alarco','hufo@uabc.edu.mx',20454,2,4,2,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(4,'Edgar','Nava','Dorantes','luis@uabc.edu.mx',20455,1,1,2,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(5,'Ana','González','Sánchez','ana@uabc.edu.mx',20456,2,3,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(6,'Carlos','Gonzalez','Altamirano','carlos@uabc.edu.mx',20457,2,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(7,'Maria','Rodríguez','López','maria@uabc.edu.mx',20458,2,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(8,'Daniel','Gómez','Hernández','daniel@uabc.edu.mx',20459,1,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(9,'Laura','Sánchez','González','laura@uabc.edu.mx',20460,2,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(10,'Sofía','Martínez','Ortiz','sofia@uabc.edu.mx',20461,2,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(11,'Elena','García','Pérez','elena@uabc.edu.mx',20462,2,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(12,'Javier','Fernández','Gómez','pedro@uabc.edu.mx',20463,4,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(13,'Alejandro','López','Sánchez','alejandro@uabc.edu.mx',20464,2,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(14,'Isabel','Martínez','Díaz','isabel@uabc.edu.mx',20465,2,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(15,'Fernanda','Hernández','Rodríguez','fernanda@uabc.edu.mx',20466,2,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(16,'Daniela','Sánchez','González','ricardo@uabc.edu.mx',20467,2,2,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(17,'Omar','Herrera','Santos','ppruea@uabc.edu.mx',32432,1,1,1,'$argon2id$v=19$m=4096,t=3,p=1$ICx43inGTCPE/cJIQM5X2Uo+Ud06MxXsrdpWTp1CJks$hROQSsvn2LdM4I0maoo0XIzwXMzVbN6h5CFGoDqAnbw'),(18,'marta','Herre','santigo','yomara@uabc.edu.mx',43532,3,1,1,'$argon2id$v=19$m=4096,t=3,p=1$BmAAUrDYCYfUV8l/47nTHnEX0clFfIRdDauqP6WPb6w$YSgQFkOzoW52WQspjhdgrOaS6Xx/w9F+rt6/fUH/fqQ'),(19,'Karla','Hernandez','Pacheco','karina@uabc.edu.mx',23422,4,1,1,'$argon2id$v=19$m=4096,t=3,p=1$T/4I10K/V3jfZNJA3x3BJVC9M3YfdpQkNo/Fvm+P6DM$4yOuEJCZJzZ39cxr58eLx0cYd2ZdF+J4Dy69P/sXjGA'),(20,'Ana','Navarro','','analuz@uabc.edu.mx',54364,1,1,1,'$argon2id$v=19$m=4096,t=3,p=1$zdqMjGUxDHey2Y8mQoWIom4RzrLlNlxUu4okxRx0vXo$Q6AiDMpsxY/DaEvq3KFhKW7zRy/RCf8imP6vjBaFOF0'),(21,'Gines','Acevedo','Venegas','yfamil@uabc.edu.mx',34234,1,1,1,'$argon2id$v=19$m=4096,t=3,p=1$d5gIgUyDWEd+7LBISGmmSNrB0H6IB8YfPOT1aEmQtWA$US2SSObXxmbh4v2YC+GKRXTv32x9iX2/NMb6hrwdLqk'),(22,'Marcelo','Sánchez','Martinez','yfdds@uabc.edu.mx',34534,1,3,1,'$argon2id$v=19$m=4096,t=3,p=1$xOJgfldjaGOZbJcpZnT0XSxAnm1M9d66+/5scXyRqQo$9is3VRNLbLgL52fqYN2l9fBIlKxslHZLKJ8HeM+NXro'),(23,'Marco','Perez','Lopez','uana@uabc.edu.mx',42425,1,1,1,'$argon2id$v=19$m=4096,t=3,p=1$63JVxHhSAW0H33r4Hies7iSpb5bQqOx/IJtc4+EYjxU$qy7+VyIJOvRxhqH+gF9R0+qf8bswsbO8zxB49Sc7WTA'),(24,'Angel','Herrera','Santos','angel@uabc.edu.mx',45435,1,1,1,'$argon2id$v=19$m=4096,t=3,p=1$klcFrBBWvaVRmVLYSJVcA5O6kw4CYosILONSNkd2OWk$8ktFtkrmSHBIxAK5uKXzgpbAlL9ArRY1HheWw28zM8E'),(25,'Andrea m','Santiago','Cruz','andrea@uabc.edu.mx',12321,1,2,1,'$argon2id$v=19$m=4096,t=3,p=1$mAg5q9UUC/e/Z5usPyY87cARFI7KxYlBKMQqHxdPR68$V7z+WsoFhWrHExDf3ln9CtXU/7xFK/4ERfnqZL96uyQ'),(26,'Osiel','Santos','Ocampo','angel43@uabc.edu.mx',23245,1,1,1,'$argon2id$v=19$m=4096,t=3,p=1$uiam+L+9EcYvC07GICw+xuKwdIKqFMvv8ZqkdNj7xmw$wseIYunuy/gR8I0xdevfCQ4CwqrDJNNWUitW2LkSQRA'),(27,'Andrea','Santiago','Cruz','andrea2@uabc.edu.mx',53453,1,1,1,'$argon2id$v=19$m=4096,t=3,p=1$X/L9LJjYvFYlW0AD3epmjQPTGzZLFrqhLjfUm1yeOUg$chA2qv5XRegOE3PnWdQNo5J/sseqbnvt9zyeakLn74o');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'sgs_db'
--  Procedimientos

DELIMITER //
DROP PROCEDURE IF EXISTS get_usuario;
CREATE PROCEDURE get_usuario(in correo varchar(100))
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


END //
DELIMITER ;
DELIMITER //
DROP PROCEDURE IF EXISTS insert_usuario;

CREATE PROCEDURE insert_usuario(
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
    
    -- Obtener el número de usuarios existentes
    SELECT COUNT(*) INTO user_count FROM usuario;

    -- Insertar un nuevo usuario
    INSERT INTO usuario(idUsuario, Usr_Nombre, Ap_Paterno, Ap_Materno, Correo, Usr_Pwd, Num_Empleado,
    Carrera_idCarrera, Categoria_idCategoria1, Estado_idEstado)
    VALUES (user_count + 1, nombre, ap_paterno, ap_materno, correo, pwd, num_empleado, id_carrera,
    id_categoria, 1);

    -- Obtener el número de filas afectadas (debe ser 1)
    SELECT COUNT(*) INTO res FROM usuario WHERE idUsuario = user_count + 1;

END //
DELIMITER ;




DELIMITER //
DROP PROCEDURE IF EXISTS update_usuario;
CREATE PROCEDURE update_usuario(
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
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS insert_rol;

CREATE PROCEDURE insert_rol(
    in rol varchar(100),
    in idUsuario int,
    in correo varchar(100),
    in pwd varchar(120),
    out res int
)
BEGIN
    DECLARE user_count INT;

    -- Obtener el número de roles existentes
    SELECT COUNT(*) INTO user_count FROM rol;

    -- Insertar un nuevo rol
    INSERT INTO rol(idRol, Rol_Descripcion, usuario_idUsuario, Correo_rol)
    VALUES (user_count + 1, rol, idUsuario, correo);

    -- Obtener el número de filas afectadas (debe ser 1)
    SELECT COUNT(*) INTO res FROM rol WHERE idRol = user_count + 1;

END //
DELIMITER ;


-- *******************
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-03 20:15:28
