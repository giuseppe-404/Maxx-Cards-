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
-- Table structure for table `prodotto_comprato`
--

DROP TABLE IF EXISTS `prodotto_comprato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodotto_comprato` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_ordine` int NOT NULL,
  `id_originale` int DEFAULT NULL,
  `prezzo` int NOT NULL,
  `nome` varchar(256) NOT NULL,
  `qnt` int NOT NULL,
  `info` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`,`id_ordine`),
  KEY `id_ordine` (`id_ordine`),
  KEY `id_originale` (`id_originale`),
  CONSTRAINT `prodotto_comprato_ibfk_1` FOREIGN KEY (`id_ordine`) REFERENCES `ordine` (`id_ordine`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `prodotto_comprato_ibfk_2` FOREIGN KEY (`id_originale`) REFERENCES `prodotto` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto_comprato`
--

LOCK TABLES `prodotto_comprato` WRITE;
/*!40000 ALTER TABLE `prodotto_comprato` DISABLE KEYS */;
/*!40000 ALTER TABLE `prodotto_comprato` ENABLE KEYS */;
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
