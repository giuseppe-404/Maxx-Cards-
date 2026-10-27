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
-- Table structure for table `notizia`
--

DROP TABLE IF EXISTS `notizia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notizia` (
  `id` int NOT NULL,
  `titolo` varchar(64) NOT NULL,
  `corpo` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notizia`
--

LOCK TABLES `notizia` WRITE;
/*!40000 ALTER TABLE `notizia` DISABLE KEYS */;
INSERT INTO `notizia` VALUES (1,'Il nuovo sito Maxx \"Cards\" è finalmente disponibile!','Finalmente la notizia che tutti aspettavano, l\'e-commerce è online! Sul nostro nuovo sito troverete un vasto catalogo di carte singole, box sigillati, tin e accessori premium, con spedizioni rapide e sicure. '),(2,'Svelata la nuova espansione: L\'Era dei Draghi Supremi','Il meta sta per cambiare con l\'arrivo de L\'Era dei Draghi Supremi. Questo set introduce due nuovi archetipi e potentissimi mostri Synchro. Sono ora disponibili sul nostro sito!'),(3,'Disponibile ora: l\'ultimo set Battles of Legend!','Preparatevi a potenziare i vostri deck, perché le carte del nuovo set Battles of Legend sono ufficialmente disponibili sul nostro e-commerce! Questo set è un appuntamento imperdibile per ogni duellante, sempre ricco di ristampe fondamentali per il meta e carte iconiche in rarità foil ricercatissime dai collezionisti.');
/*!40000 ALTER TABLE `notizia` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-27 12:38:04
