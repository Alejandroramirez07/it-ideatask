
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8;
USE `mydb`;

-- =====================================================
-- 1. BASE TABLES
-- =====================================================

-- -----------------------------------------------------
-- Table project_managers
-- -----------------------------------------------------
DROP TABLE IF EXISTS `project_managers`;
CREATE TABLE `project_managers` (
  `username` VARCHAR(16) NOT NULL,
  `email` VARCHAR(255) NULL,
  `password` VARCHAR(32) NOT NULL,
  `project_code` INT NULL,
  `employee_code` INT NOT NULL,
  PRIMARY KEY (`employee_code`)
);

INSERT INTO project_managers VALUES
('alejandro.pm','alejandro.pm@solv.com','pass',101,1),
('bob.pm','bob.pm@solv.com','pass',102,2),
('caroline.pm','caroline.pm@solv.com','pass',103,3),
('david.pm','david.pm@solv.com','pass',104,4),
('eve.pm','eve.pm@solv.com','pass',105,5);

-- -----------------------------------------------------
-- Table developers
-- -----------------------------------------------------
DROP TABLE IF EXISTS `developers`;
CREATE TABLE `developers` (
  `username` VARCHAR(16) NOT NULL,
  `email` VARCHAR(255) NULL,
  `password` VARCHAR(32) NOT NULL,
  `project_code` INT NULL,
  `employee_code` INT NOT NULL,
  `manager_employee_code` INT NOT NULL,
  PRIMARY KEY (`employee_code`),
  FOREIGN KEY (`manager_employee_code`) REFERENCES `project_managers` (`employee_code`)
);

INSERT INTO developers VALUES
('john.dev','john.dev@solv.com','dev123',101,11,1),
('sara.dev','sara.dev@solv.com','dev123',102,12,2),
('mike.dev','mike.dev@solv.com','dev123',103,13,3),
('linda.dev','linda.dev@solv.com','dev123',104,14,4),
('tom.dev','tom.dev@solv.com','dev123',105,15,5),
('anna.dev','anna.dev@solv.com','dev123',101,16,1),
('peter.dev','peter.dev@solv.com','dev123',102,17,2);

-- -----------------------------------------------------
-- Table projects
-- -----------------------------------------------------
DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects` (
  `project_code` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`project_code`)
);

INSERT INTO projects VALUES
(101,'Billing API'),
(102,'E-Commerce Platform'),
(103,'Cloud Migration'),
(104,'AI Chatbot'),
(105,'Data Lake Implementation');

-- =====================================================
-- 2. APPLICATION TABLES
-- =====================================================

-- -----------------------------------------------------
-- Table client_details
-- -----------------------------------------------------
DROP TABLE IF EXISTS `client_details`;
CREATE TABLE `client_details` (
  `email` VARCHAR(255) NOT NULL,
  `username` VARCHAR(16) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `project_code` INT NULL,
  PRIMARY KEY (`email`),
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`)
);

INSERT INTO client_details VALUES
('contact.acme@gmail.com','acme','client123',101),
('support.tricell@gmail.com','tricell','client123',102),
('support.stark@gmail.com','stark','client123',103),
('info.umbrella@gmail.com','umbrella','client123',104),
('tech.wayne@gmail.com','wayne','client123',105);
-- -----------------------------------------------------
-- Table client_history
-- -----------------------------------------------------
DROP TABLE IF EXISTS `client_history`;
CREATE TABLE `client_history` (
  `client_email` VARCHAR(255) NOT NULL,
  `username` VARCHAR(16) NOT NULL,
  `number_of_projects` INT NULL,
  FOREIGN KEY (`client_email`) REFERENCES `client_details` (`email`)
);

INSERT INTO client_history VALUES
('contact.acme@gmail.com','acme',3),
('support.tricell@gmail.com','tricell',5),
('support.stark@gmail.com','stark',2),
('info.umbrella@gmail.com','umbrella',4),
('tech.wayne@gmail.com','wayne',6);

-- -----------------------------------------------------
-- Table java_projects
-- -----------------------------------------------------
DROP TABLE IF EXISTS `java_projects`;
CREATE TABLE `java_projects` (
  `project_code` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `client_comments` VARCHAR(45) NULL,
  `client_score_1_10` INT NULL,
  `java_version` DECIMAL(5,2) NULL,
  PRIMARY KEY (`project_code`),
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`)
);

INSERT INTO java_projects VALUES
(103,'Cloud Migration','Excellent scalability',9,11.00),
(105,'Data Lake Implementation','Complex but robust',8,17.00);

-- -----------------------------------------------------
-- Table python_projects
-- -----------------------------------------------------
DROP TABLE IF EXISTS `python_projects`;
CREATE TABLE `python_projects` (
  `project_code` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `client_comments` VARCHAR(45) NULL,
  `client_score_1_10` INT NULL,
  `python_version` DECIMAL(5,2) NULL,
  PRIMARY KEY (`project_code`),
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`)
);

INSERT INTO python_projects VALUES
(101,'Billing API','Very stable',9,3.90),
(104,'AI Chatbot','Good but slow',7,3.80);

-- -----------------------------------------------------
-- Table javascript_projects
-- -----------------------------------------------------
DROP TABLE IF EXISTS `javascript_projects`;
CREATE TABLE `javascript_projects` (
  `project_code` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `client_comments` VARCHAR(45) NULL,
  `client_score_1_10` INT NULL,
  `javascript_version` DECIMAL(5,2) NULL,
  `used_in_backend` TINYINT(1) NULL,
  PRIMARY KEY (`project_code`),
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`)
);

INSERT INTO javascript_projects VALUES
(102,'E-Commerce Platform','User friendly',8,14.00,1);

-- -----------------------------------------------------
-- Table time_invested
-- -----------------------------------------------------
DROP TABLE IF EXISTS `time_invested`;
CREATE TABLE `time_invested` (
  `start_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `finish_time` TIMESTAMP NULL,
  `project_code` INT NOT NULL,
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`)
);

INSERT INTO time_invested VALUES
('2025-01-01 09:00:00','2025-01-15 18:00:00',101),
('2025-02-01 09:00:00','2025-02-20 18:00:00',102),
('2025-03-01 09:00:00','2025-03-30 18:00:00',103),
('2025-04-01 09:00:00','2025-04-18 18:00:00',104),
('2025-05-01 09:00:00','2025-05-25 18:00:00',105);

-- -----------------------------------------------------
-- Table splunk_monitoring
-- -----------------------------------------------------
DROP TABLE IF EXISTS `splunk_monitoring`;
CREATE TABLE `splunk_monitoring` (
  `project_code` INT NOT NULL,
  `monitor_comments` VARCHAR(255) NOT NULL,
  `number_incidents` INT NULL,
  PRIMARY KEY (`project_code`),
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`)
);

INSERT INTO splunk_monitoring VALUES
(101,'Stable logs',1),
(102,'Minor errors in checkout flow',3),
(103,'Cloud warnings resolved quickly',2),
(104,'Few latency spikes',5),
(105,'Heavy loads but stable',4);

-- -----------------------------------------------------
-- Table AWS_cloud
-- -----------------------------------------------------
DROP TABLE IF EXISTS `AWS_cloud`;
CREATE TABLE `AWS_cloud` (
  `project_code` INT NOT NULL,
  `number_of_regions` INT NULL,
  PRIMARY KEY (`project_code`),
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`)
);

INSERT INTO AWS_cloud VALUES
(101,5),
(103,7);

-- -----------------------------------------------------
-- Table Azure_cloud
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Azure_cloud`;
CREATE TABLE `Azure_cloud` (
  `project_code` INT NOT NULL,
  `number_of_regions` INT NULL,
  PRIMARY KEY (`project_code`),
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`)
);

INSERT INTO Azure_cloud VALUES
(102,4),
(105,6);

-- -----------------------------------------------------
-- Table GCP_cloud
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GCP_cloud`;
CREATE TABLE `GCP_cloud` (
  `project_code` INT NOT NULL,
  `number_of_regions` INT NULL,
  PRIMARY KEY (`project_code`),
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`)
);

INSERT INTO GCP_cloud VALUES
(104,3);

-- -----------------------------------------------------
-- Table income_projects
-- -----------------------------------------------------
DROP TABLE IF EXISTS `income_projects`;
CREATE TABLE `income_projects` (
  `total_income_usd` DECIMAL(16,2) NOT NULL,
  `comments_on_income` VARCHAR(255) NOT NULL,
  `project_code` INT NOT NULL,
  `report_number` INT NOT NULL,
  PRIMARY KEY (`report_number`),
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`)
);

INSERT INTO income_projects VALUES
(250000.00,'High demand from clients',101,1001),
(180000.00,'Steady sales',102,1002),
(300000.00,'Enterprise migration deal',103,1003),
(220000.00,'AI adoption increasing',104,1004),
(270000.00,'Large data analytics contract',105,1005);

-- -----------------------------------------------------
-- Table resources_invested
-- -----------------------------------------------------
DROP TABLE IF EXISTS `resources_invested`;
CREATE TABLE `resources_invested` (
  `total_spent_usd` DECIMAL(16,2) NOT NULL,
  `comments_on_spendings` VARCHAR(255) NOT NULL,
  `project_code` INT NOT NULL,
  `report_number` INT NULL,
  PRIMARY KEY (`project_code`),
  FOREIGN KEY (`project_code`) REFERENCES `projects` (`project_code`),
  FOREIGN KEY (`report_number`) REFERENCES `income_projects` (`report_number`)
);

INSERT INTO resources_invested VALUES
(100000.00,'Development resources',101,1001),
(75000.00,'Frontend/backend team',102,1002),
(150000.00,'Cloud infra + devops',103,1003),
(90000.00,'AI engineers and infra',104,1004),
(120000.00,'Data engineers + storage',105,1005);

-- -----------------------------------------------------
-- Table general_clouds
-- -----------------------------------------------------
DROP TABLE IF EXISTS `general_clouds`;
CREATE TABLE `general_clouds` (
  `gcp_project_code` INT NOT NULL,
  `aws_project_code` INT NOT NULL,
  `azure_project_code` INT NOT NULL,
  PRIMARY KEY (`gcp_project_code`, `aws_project_code`, `azure_project_code`),
  FOREIGN KEY (`gcp_project_code`) REFERENCES `GCP_cloud` (`project_code`),
  FOREIGN KEY (`aws_project_code`) REFERENCES `AWS_cloud` (`project_code`),
  FOREIGN KEY (`azure_project_code`) REFERENCES `Azure_cloud` (`project_code`)
);

INSERT INTO general_clouds VALUES
(104,101,102),
(104,103,105);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

