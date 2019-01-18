
DROP TABLE IF EXISTS `privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
   ;
CREATE TABLE `privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ;

INSERT INTO `privilege` VALUES (6,'COMPANY_ADMINISTRATOR'),(5,'ADD_ADVOCATE_ACCOUNTS'),(4,'ADD_ADVOCATE_COMPANY_ACCOUNTS'),(7,'CREATE_CASES'),(8,'ACCESS_TO_CASE_DOCUMENTATION'),(9,'ACCESS_TO_SENSIBLE_DOCUMENTS'),(10,'ACCESS_TO_DEDICATED_CASES');

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE2` (`name`)
);

INSERT INTO `role` VALUES (2,'ROLE_PORTAL_ADMINISTRATOR'),(3,'ROLE_ADVOCATE_COMPANY_ADMINISTRATOR'),(4,'ROLE_ADVOCATE'),(5,'ROLE_APPRENTICE');

DROP TABLE IF EXISTS `roles_privileges`;

CREATE TABLE `roles_privileges` (
  `role_id` bigint(20) NOT NULL,
  `privilege_id` bigint(20) NOT NULL,
  foreign key (`role_id`) references role(id),
  foreign key (`privilege_id`) references privilege(id)

);


INSERT INTO `roles_privileges` VALUES (2,5),(2,4),(3,5),(4,7),(4,8),(4,9),(5,10);


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
  `type`  smallint (2) DEFAULT 1,
  `digital_signature` varchar(255) DEFAULT NULL,
  `status_id` SMALLINT(2) NOT NULL,
   PRIMARY KEY (`id`),
   foreign key (`status_id`) references status(id)
);




DROP TABLE IF EXISTS `client`;

create TABLE `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `account_number` varchar(255) NOT NULL,
   `advocate_company_id` bigint(20) NOT NULL,
   PRIMARY KEY (`id`),
   foreign key (`advocate_company_id`) references advocate_company(id)
);

DROP TABLE IF EXISTS `client_company`;

create TABLE `client_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `embs` varchar(7) NOT NULL,
  `edb` varchar(13) DEFAULT NULL,
  `manager_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  foreign key (`id`) references client(id)
);

DROP TABLE IF EXISTS `client_person`;

create TABLE `client_person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `personal_id` varchar(255) NOT NULL,
  `embg` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  foreign key (`id`) references client(id)
);


DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
   ;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `password` varchar(60) NOT NULL,
  `phone` VARCHAR(45) DEFAULT NULL,
  `status_id` SMALLINT(2) NOT NULL,
  `account_group_level_id` SMALLINT(2) NOT NULL,
  `advocate_company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  foreign key (`status_id`) references status(id),
   foreign key (`advocate_company_id`) references advocate_company(id)
);

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
   foreign key (`user_id`) references user(id),
   foreign key (`role_id`) references role(id)
);


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


