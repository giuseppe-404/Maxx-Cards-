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
-- Table structure for table `mostro`
--

DROP TABLE IF EXISTS `mostro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mostro` (
  `id` int NOT NULL,
  `Tipologia` enum('none','Fusione','Synchro','XYZ','Rituale','Link') NOT NULL DEFAULT 'none',
  `Livello` int NOT NULL,
  `Attributo` enum('Luce','Oscurità','Terra','Acqua','Fuoco','Vento','Divino') NOT NULL,
  `tipo` varchar(32) NOT NULL,
  `ATK` int NOT NULL,
  `DEF` int NOT NULL,
  `Categoria` enum('none','Toon','Gemello','Spirit','Unione') NOT NULL DEFAULT 'none',
  `Tuner` tinyint(1) NOT NULL DEFAULT '0',
  `frecce_link` bit(8) NOT NULL DEFAULT b'0',
  `scala_pendulum` int NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `tipo` (`tipo`),
  CONSTRAINT `mostro_ibfk_1` FOREIGN KEY (`id`) REFERENCES `carta` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mostro_ibfk_2` FOREIGN KEY (`tipo`) REFERENCES `tipo` (`tipo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `level_check_main` CHECK ((`Livello` between 1 and 12)),
  CONSTRAINT `link_check` CHECK (((((((((cast((`frecce_link` & 0x01) as signed) + cast(((`frecce_link` & 0x02) >> 1) as signed)) + cast(((`frecce_link` & 0x04) >> 2) as signed)) + cast(((`frecce_link` & 0x08) >> 3) as signed)) + cast(((`frecce_link` & 0x10) >> 4) as signed)) + cast(((`frecce_link` & 0x20) >> 5) as signed)) + cast(((`frecce_link` & 0x40) >> 6) as signed)) + cast(((`frecce_link` & 0x80) >> 7) as signed)) = `livello`)),
  CONSTRAINT `pendulum_check` CHECK ((`scala_pendulum` between -(1) and 13)),
  CONSTRAINT `stat_check_main` CHECK (((`ATK` >= -(1)) and (((`DEF` = 0) and (`tipologia` = _utf8mb4'Link')) or ((`DEF` >= -(1)) and (`tipologia` <> _utf8mb4'Link')))))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mostro`
--

LOCK TABLES `mostro` WRITE;
/*!40000 ALTER TABLE `mostro` DISABLE KEYS */;
/*!40000 ALTER TABLE `mostro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-23 18:00:48
