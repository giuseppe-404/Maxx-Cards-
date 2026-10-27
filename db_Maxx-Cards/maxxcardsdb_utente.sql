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
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `salt` varbinary(128) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `pwd` varchar(256) NOT NULL,
  `amministratore` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (10,_binary 'ﬂ∑ú\√6Ω\ﬁ∂e±\” \’hõ','giuseppederosa07@gmail.com','9c0c29fc18109ac7e9b2d8353bf01d1416ea5814eb27df83746f008b5a9219a5',0),(11,_binary 'ê\›EFø\Ëq´ûÅ0X\Œ','giuseppe@g.com','4ac673d40ddd707a08ee488ad18ca6a9f9e9be93e55417a5f8734d15b323b548',1),(17,NULL,NULL,' ',0),(18,_binary '-NΩÀó9»í^ÑîxY','ciao@ciao','9454ec16370a580b304b3cef2f9254d70133b4ffe2fe1da943f683e21c8549e8',1),(19,_binary '0SâW\Ÿ˚fßáK\0ˆb›£','ciao@ciaone','319e883593c5345cc8763e4c2e1275b95c3d251a19ef065b620793fb96ad313f',0),(20,_binary 'êd`!\⁄ò\€v:#3i¸','sopra@gmail.com','4ebb0c2323f8a94b8b171e919fda598ec8bbf57e351e3ed0dc4ac5d5fc54e494',0),(22,_binary 'M\Œ\‘AO\ÿ+}B]=/k¯ç','s@g.com','0e4006ad3ab5a7c969dbc5ba051b3cbc5195052606cc07f00f8743d422b769eb',1);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-27 12:38:05
