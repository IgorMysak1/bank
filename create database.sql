-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bankingapp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bankingapp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bankingapp` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `bankingapp` ;

-- -----------------------------------------------------
-- Table `bankingapp`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bankingapp`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` TINYTEXT NOT NULL,
  `secondName` TINYTEXT NOT NULL,
  `age` INT NOT NULL,
  `passportId` INT NOT NULL,
  `phoneNumber` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bankingapp`.`bank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bankingapp`.`bank` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bankName` TEXT NOT NULL,
  `rate` INT NOT NULL,
  `humanTrust` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bankingapp`.`deposits`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bankingapp`.`deposits` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bankId` INT NOT NULL,
  `maxSum` DOUBLE NOT NULL,
  `timeOfIncome` INT NOT NULL,
  `uan` TINYINT NOT NULL,
  `usd` TINYINT NOT NULL,
  `eur` TINYINT NOT NULL,
  `terminationPosibillity` TINYINT NOT NULL,
  `percent` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_deposits_bank1_idx` (`bankId` ASC) VISIBLE,
  CONSTRAINT `fk_deposits_bank1`
    FOREIGN KEY (`bankId`)
    REFERENCES `bankingapp`.`bank` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bankingapp`.`balance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bankingapp`.`balance` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `clientId` INT NOT NULL,
  `bankId` INT NOT NULL,
  `depositId` INT NOT NULL,
  `uan` DOUBLE NOT NULL,
  `eur` DOUBLE NOT NULL,
  `usd` DOUBLE NOT NULL,
  `depositDate` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_balance_client_idx` (`clientId` ASC) VISIBLE,
  INDEX `fk_balance_deposits1_idx` (`depositId` ASC) VISIBLE,
  INDEX `fk_balance_bank1_idx` (`bankId` ASC) VISIBLE,
  CONSTRAINT `fk_balance_client`
    FOREIGN KEY (`clientId`)
    REFERENCES `bankingapp`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_balance_deposits1`
    FOREIGN KEY (`depositId`)
    REFERENCES `bankingapp`.`deposits` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_balance_bank1`
    FOREIGN KEY (`bankId`)
    REFERENCES `bankingapp`.`bank` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bankingapp`.`bankoperations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bankingapp`.`bankoperations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `clientid` INT NOT NULL,
  `currency` TEXT NOT NULL,
  `money` DOUBLE NOT NULL,
  `card` TEXT NOT NULL,
  `date` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_bankoperations_client1_idx` (`clientid` ASC) VISIBLE,
  CONSTRAINT `fk_bankoperations_client1`
    FOREIGN KEY (`clientid`)
    REFERENCES `bankingapp`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bankingapp`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bankingapp`.`user` (
  `id` INT NOT NULL,
  `clientId` INT NOT NULL,
  `username` TEXT NOT NULL,
  `password` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_user_client1_idx` (`clientId` ASC) VISIBLE,
  CONSTRAINT `fk_user_client1`
    FOREIGN KEY (`clientId`)
    REFERENCES `bankingapp`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
