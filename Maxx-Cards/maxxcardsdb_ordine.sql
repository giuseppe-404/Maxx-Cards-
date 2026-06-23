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
-- Table structure for table `ordine`
--

DROP TABLE IF EXISTS `ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordine` (
  `stato` enum('Carrello','Acquistato','Spedito','Consegnato','Rimborsato') NOT NULL DEFAULT 'Carrello',
  `id_ordine` int NOT NULL AUTO_INCREMENT,
  `id_utente` int DEFAULT NULL,
  `id_metodo` int DEFAULT NULL,
  `id_infosped` int DEFAULT NULL,
  `data_acquisto` datetime DEFAULT NULL,
  `data_consegna` datetime DEFAULT NULL,
  PRIMARY KEY (`id_ordine`),
  KEY `id_utente` (`id_utente`,`id_metodo`),
  KEY `id_utente_2` (`id_utente`,`id_infosped`),
  CONSTRAINT `ordine_ibfk_1` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `ordine_ibfk_2` FOREIGN KEY (`id_utente`, `id_metodo`) REFERENCES `metodopagamento` (`id_utente`, `id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `ordine_ibfk_3` FOREIGN KEY (`id_utente`, `id_infosped`) REFERENCES `infosped` (`id_utente`, `id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordine`
--

LOCK TABLES `ordine` WRITE;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-23 18:00:46
