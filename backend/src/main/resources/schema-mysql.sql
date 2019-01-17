CREATE DATABASE  IF NOT EXISTS `advocate10` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `advocate10`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: advocate10
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privilege`
--

DROP TABLE IF EXISTS `privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilege`
--

LOCK TABLES `privilege` WRITE;
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
INSERT INTO `privilege` VALUES (6,'MK()COMPANY_ADMINISTRATOR'),(5,'ADD_ADVOCATE_ACCOUNTS'),(4,'ADD_ADVOCATE_COMPANY_ACCOUNTS'),(7,'CREATE_CASES'),(8,'ACCESS_TO_CASE_DOCUMENTATION'),(9,'ACCESS_TO_SENSIBLE_DOCUMENTS'),(10,'ACCESS_TO_DEDICATED_CASES');
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'ROLE_PORTAL_ADMINISTRATOR'),(3,'ROLE_ADVOCATE_COMPANY_ADMINISTRATOR'),(4,'ROLE_ADVOCATE'),(5,'ROLE_APPRENTICE');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_privileges`
--

DROP TABLE IF EXISTS `roles_privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles_privileges` (
  `role_id` bigint(20) NOT NULL,
  `privilege_id` bigint(20) NOT NULL,
  KEY `FK5yjwxw2gvfyu76j3rgqwo685u` (`privilege_id`),
  KEY `FK9h2vewsqh8luhfq71xokh4who` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_privileges`
--

LOCK TABLES `roles_privileges` WRITE;
/*!40000 ALTER TABLE `roles_privileges` DISABLE KEYS */;
INSERT INTO `roles_privileges` VALUES (2,5),(2,4),(3,5),(4,7),(4,8),(4,9),(5,10);
/*!40000 ALTER TABLE `roles_privileges` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `status`;

create TABLE `status` (
  `id` smallint(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`)
);


INSERT INTO `status` VALUES (1,'Active','Active status');
INSERT INTO `status` VALUES (2,'Deleted','Deleted status');

DROP TABLE IF EXISTS `advocate_company`;

create TABLE `advocate_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `embs` varchar(100) DEFAULT NULL,
  `edbs` varchar(100) DEFAULT NULL,
  `license` varchar(255) DEFAULT NULL,
  `digital_signature` varchar(255) DEFAULT NULL,
  `status_id` SMALLINT(2) NOT NULL,
   PRIMARY KEY (`id`),
   foreign key (`status_id`) references status(id)
);


DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `password` varchar(60) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `status_id` SMALLINT(2) NOT NULL,
  `account_group_level_id` smallint (2) NOT NULL,
  `advocate_company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  foreign key (`status_id`) references status(id),
  foreign key (`advocate_company_id`) references advocate_company(id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FKt4v0rrweyk393bdgt107vdx0x` (`role_id`),
  KEY `FKgd3iendaoyh04b95ykqise6qh` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-29 16:40:07


/*
insert into role values(null,"ROLE_PORTAL_ADMINISTRATOR");
insert into role values(null,"ROLE_ADVOCATE_COMPANY_ADMINISTRATOR");
insert into role values(null,"ROLE_ADVOCATE");
insert into role values(null,"ROLE_APPRENTICE");

insert into privilege values(null,"ADD_ADVOCATE_COMPANY_ACCOUNTS");
insert into privilege values(null,"ADD_ADVOCATE_ACCOUNTS");
insert into privilege values(null,"COMPANY_ADMINISTRATOR");
insert into privilege values(null,"CREATE_CASES");
insert into privilege values(null,"ACCESS_TO_CASE_DOCUMENTATION");
insert into privilege values(null,"ACCESS_TO_SENSIBLE_DOCUMENTS");
insert into privilege values(null,"ACCESS_TO_DEDICATED_CASES");

 insert into roles_privileges (role_id, privilege_id) select (select id from role where role.name='ROLE_PORTAL_ADMINISTRATOR'),
 (select id from privilege where privilege.name='ADD_ADVOCATE_ACCOUNTS');
 insert into roles_privileges (role_id, privilege_id) select (select id from role where role.name='ROLE_PORTAL_ADMINISTRATOR'),
 (select id from privilege where privilege.name='ADD_ADVOCATE_COMPANY_ACCOUNTS');

 insert into roles_privileges (role_id, privilege_id) select (select id from role where role.name='ROLE_ADVOCATE_COMPANY_ADMINISTRATOR'),
 (select id from privilege where privilege.name='ADD_ADVOCATE_ACCOUNTS');

 insert into roles_privileges (role_id, privilege_id) select (select id from role where role.name='ROLE_ADVOCATE'),
 (select id from privilege where privilege.name='CREATE_CASES');
 insert into roles_privileges (role_id, privilege_id) select (select id from role where role.name='ROLE_ADVOCATE'),
 (select id from privilege where privilege.name='ACCESS_TO_CASE_DOCUMENTATION');
 insert into roles_privileges (role_id, privilege_id) select (select id from role where role.name='ROLE_ADVOCATE'),
 (select id from privilege where privilege.name='ACCESS_TO_SENSIBLE_DOCUMENTS');

 insert into roles_privileges (role_id, privilege_id) select (select id from role where role.name='ROLE_APPRENTICE'),
 (select id from privilege where privilege.name='ACCESS_TO_DEDICATED_CASES');
*/


