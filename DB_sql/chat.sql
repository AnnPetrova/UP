-- MySQL Script generated by MySQL Workbench
-- 05/23/16 18:24:49
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Users` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = MEMORY;


-- -----------------------------------------------------
-- Table `mydb`.`Messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Messages` (
  `id` INT NOT NULL,
  `text` VARCHAR(45) NULL,
  `date` VARCHAR(45) NULL,
  `user_id` VARCHAR(45) NOT NULL,
  `Users_id` INT NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_Messages_Users_idx` (`Users_id` ASC),
  CONSTRAINT `fk_Messages_Users`
    FOREIGN KEY (`Users_id`)
    REFERENCES `mydb`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
