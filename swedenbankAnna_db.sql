-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.37 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for swedenbank
CREATE DATABASE IF NOT EXISTS `swedenbank` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_swedish_ci */;
USE `swedenbank`;

-- Dumping structure for table swedenbank.bankaccounts
CREATE TABLE IF NOT EXISTS `bankaccounts` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `accountnumber` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  `type` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  `accountname` varchar(50) COLLATE utf8mb4_swedish_ci DEFAULT NULL,
  `amount` double NOT NULL DEFAULT '0',
  `user_id` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `accountnumber` (`accountnumber`),
  KEY `FK_bankaccounts_user` (`user_id`),
  CONSTRAINT `FK_bankaccounts_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_swedish_ci;

-- Data exporting was unselected.
-- Dumping structure for table swedenbank.transactions
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `message` varchar(50) COLLATE utf8mb4_swedish_ci DEFAULT NULL,
  `amount` double NOT NULL DEFAULT '0',
  `fromaccountnumber` varchar(50) COLLATE utf8mb4_swedish_ci DEFAULT '0',
  `toaccountnumber` varchar(50) COLLATE utf8mb4_swedish_ci DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_transactions_bankaccounts` (`fromaccountnumber`),
  KEY `FK_transactions_bankaccounts_2` (`toaccountnumber`),
  CONSTRAINT `FK_transactions_bankaccounts` FOREIGN KEY (`fromaccountnumber`) REFERENCES `bankaccounts` (`accountnumber`),
  CONSTRAINT `FK_transactions_bankaccounts_2` FOREIGN KEY (`toaccountnumber`) REFERENCES `bankaccounts` (`accountnumber`)
) ENGINE=InnoDB AUTO_INCREMENT=445 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_swedish_ci;

-- Data exporting was unselected.
-- Dumping structure for table swedenbank.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) COLLATE utf8mb4_swedish_ci NOT NULL,
  `lastname` varchar(50) COLLATE utf8mb4_swedish_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8mb4_swedish_ci DEFAULT NULL,
  `date_of_birth` varchar(50) COLLATE utf8mb4_swedish_ci DEFAULT '0000-00-00-0000',
  `email` varchar(50) COLLATE utf8mb4_swedish_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8mb4_swedish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `date_of_birth` (`date_of_birth`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_swedish_ci;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
