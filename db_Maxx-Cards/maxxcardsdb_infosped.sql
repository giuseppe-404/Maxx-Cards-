-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: maxxcardsdb
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `infosped`
--

DROP TABLE IF EXISTS `infosped`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infosped` (
  `id` int NOT NULL,
  `id_utente` int NOT NULL,
  `nome` varchar(64) NOT NULL,
  `cognome` varchar(64) NOT NULL,
  `via` varchar(128) NOT NULL,
  `civico` int NOT NULL,
  `cap` int NOT NULL,
  PRIMARY KEY (`id`,`id_utente`),
  KEY `id_utente` (`id_utente`),
  CONSTRAINT `infosped_ibfk_1` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infosped`
--

LOCK TABLES `infosped` WRITE;
/*!40000 ALTER TABLE `infosped` DISABLE KEYS */;
INSERT INTO `infosped` VALUES (1,11,'Giuseppe','De Rosa','Via lui no',13,84013),(1,17,'AAAA','b','via via',1,84013),(1,18,'a','b','c',3,12345),(1,20,'sd','teq','via idns',12,65434),(2,11,'Giuseppe','De Rosa','Via lui no',13,84013),(2,17,'Giuseppe','De Rosa','Via lui no',13,84013),(2,18,'a','b','c',3,12345),(3,11,'Giuseppe','De Rosa','Via lui no',13,84013),(3,17,'a','b','c',3,12345),(3,18,'a','b','c',3,12345),(4,11,'Giuseppe','De Rosa','Via lui no',13,84013),(4,17,'a','b','c',3,12345),(4,18,'a','b','c',3,12345),(5,11,'Giuseppe','De Rosa','Via lui no',13,84013),(5,17,'a','b','c',3,12345),(5,18,'a','b','c',3,12345),(6,11,'Giuseppe','De Rosa','Via lui no',13,84013),(6,17,'Samuele','s','vvad',44,66681),(6,18,'a','b','c',3,12345),(7,18,'a','b','c',3,12345),(8,18,'a','b','c',3,12345),(9,18,'a','b','c',3,12345);
/*!40000 ALTER TABLE `infosped` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-27 12:38:06
