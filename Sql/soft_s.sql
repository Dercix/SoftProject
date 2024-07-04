-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: soft
-- ------------------------------------------------------
-- Server version	5.7.26

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
-- Table structure for table `s`
--

DROP TABLE IF EXISTS `s`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `s` (
  `sid` int(11) NOT NULL,
  `sname` varchar(30) NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `sid` (`sid`,`sname`),
  UNIQUE KEY `sid_UNIQUE` (`sid`),
  UNIQUE KEY `sname_UNIQUE` (`sname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s`
--

LOCK TABLES `s` WRITE;
/*!40000 ALTER TABLE `s` DISABLE KEYS */;
INSERT INTO `s` VALUES (1,'阮健乘','1234'),(2,'张四','123'),(3,'李四','1234'),(4,'彭昊辉','123456'),(6,'林春霞','123'),(7,'董一超','12345'),(8,'董超','123'),(9,'张千','10086'),(10,'李万','10085'),(14,'薇尔莉特','123'),(21,'庄亮','123'),(22,'钟平','1234'),(23,'李煜豪','123'),(25,'aaa','$2a$10$nTjDITipc29YQbrk46btGu2BQNjZRwCvJm0kbsHrAd6MRi3PgRPoK'),(123,'qwer','$2a$10$daFufrz10q/vGAMc7RSepefld9rs4eYUDezhWn/HNuDqx9OwZITLq'),(1234,'www','$2a$10$1zHNEtmsnhKoPJj/pblBnuOwhj.G9rotyNlayZgEU31of3Eu/Q1oe'),(12345,'qqqq','$2a$10$qsl7fkby1kMQ2NVHfEztXOPLFiXKxDzF/bAdv0sNVcneztRvf0DPi');
/*!40000 ALTER TABLE `s` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-10 11:34:23
