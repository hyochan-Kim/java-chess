CREATE SCHEMA IF NOT EXISTS `chess` DEFAULT CHARACTER SET utf8 ;
USE `chess` ;

-- -----------------------------------------------------
-- Table `chess`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chess`.`user` (
  `userId` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `win` INT NOT NULL DEFAULT 0,
  `draw` INT NOT NULL DEFAULT 0,
  `lose` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chess`.`game`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chess`.`game` (
  `gameId` INT UNSIGNED NOT NULL,
  `black` INT NOT NULL,
  `white` INT NOT NULL,
  `isEnd` TINYINT NOT NULL,
  PRIMARY KEY (`gameId`),
  INDEX `userId_idx` (`black` ASC, `white` ASC) VISIBLE,
  CONSTRAINT `userId`
    FOREIGN KEY (`black` , `white`)
    REFERENCES `chess`.`user` (`userId` , `userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chess`.`move`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `chess`.`move` (
  `moveId` INT NOT NULL,
  `source` VARCHAR(2) NOT NULL,
  `target` VARCHAR(2) NOT NULL,
  `gameId` INT NOT NULL,
  PRIMARY KEY (`moveId`),
  INDEX `gameId_idx` (`gameId` ASC) VISIBLE,
  CONSTRAINT `gameId`
    FOREIGN KEY (`gameId`)
    REFERENCES `chess`.`game` (`black`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;