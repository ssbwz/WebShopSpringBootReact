-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: amazon_clone
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(200) DEFAULT NULL,
  `password` text NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(20) NOT NULL,
  `user_type` enum('Customer','Owner') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'O@d.n','$2a$10$yt4w9TFtVQZbyzk1rSGol.zik4dPCV/vkCazqfRKsP56wZZLd4YVe','Saeed','Ba Wazir','Owner'),(3,'C@d.n','$2a$10$jkGvg5rt.AIy67DJQdj9d.JAy8HpqOMtXUpAWdKi20ihcBhEufqk6','Jessie','Van Nuenen','Customer'),(4,'w@d.n','$2a$10$TnUHdqc4qPptpLPKVafc4e0kNUmfGGtLEi6JOAqiAIge4PUYiatjC','Jessie','Van Nuenen','Customer'),(5,'T@S.nl','$2a$10$KDtRCNjGxCZPsvSxBgDUK.t.UGiHm0fUyg3QtiSPlh6Jc6OfJV0Q6','Tom','Van Eindhoven','Customer'),(7,'T@S.S','$2a$10$mXsgp2WyBmPd3zrgTQiYou5RZQj6LN5Sklj/r89eWT65xsTSh8V66','Tom','Van Eindhoven','Customer'),(8,'s@s.s','$2a$10$phu3QJhRyQxusbSgP8DJvu1Mr8IfbVD0j7wXJFKFsWueJiB5wOkvu','s','s','Customer'),(12,'L@L.l','$2a$10$N6TgrCu4AehUwWl4wldRfO6udqTrLoz8Uo1G6Bp1M6ID6eeAAvgTm','Saeed','Saeed','Customer'),(13,'s@d.n','$2a$10$fzWTrH3CsQ5LijEi8YDAnOZBgDaCaqPnAy/w/XVuHipDzlmRz3bR2','Saeed','Saeed','Customer');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-24 14:57:19
