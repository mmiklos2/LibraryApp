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
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `user_id` INT auto_increment,
  `user_username` VARCHAR(20) NOT NULL,
  `user_email` VARCHAR(100) NOT NULL,
  `user_password` VARCHAR(20) NOT NULL,
  `user_firstname` VARCHAR(45) NOT NULL,
  `user_lastname` VARCHAR(45) NOT NULL,
  `role` CHAR(1) NOT NULL,

  PRIMARY KEY (`user_id`));


-- -----------------------------------------------------
-- Table `mydb`.`publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`publisher` (
  `publisher_id` INT auto_increment,
  `publisher_name` VARCHAR(45) NULL,
  `publisher_city` VARCHAR(45) NULL,
  PRIMARY KEY (`publisher_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`genres` (
  `genre_id` INT auto_increment,
  `genre_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`genre_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`books` (
  `book_id` INT auto_increment,
  `book_isbn` VARCHAR(13) NOT NULL,
  `book_title` VARCHAR(200) NOT NULL,
  `book_publisher_id` INT NOT NULL,
  `book_published_year` INT NULL,
  `book_copies` INT NOT NULL,
  `book_location` VARCHAR(45) NOT NULL,
  `book_genre_id` INT NOT NULL,
  PRIMARY KEY (`book_id`),
  INDEX `fk_genre_idx` (`book_genre_id` ASC),
  INDEX `fk_book_publisher_idx` (`book_publisher_id` ASC),
  CONSTRAINT `fk_book_publisher`
    FOREIGN KEY (`book_publisher_id`)
    REFERENCES `mydb`.`publisher` (`publisher_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_genre`
    FOREIGN KEY (`book_genre_id`)
    REFERENCES `mydb`.`genres` (`genre_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`author` (
  `author_id` INT auto_increment,
  `author_firstname` VARCHAR(45) NOT NULL,
  `author_lastname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`author_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`author_book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`author_book` (
  `author_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  INDEX `fk_author_idx` (`author_id` ASC),
  INDEX `fk_book_idx` (`book_id` ASC),
  PRIMARY KEY (`book_id`, `author_id`),
  CONSTRAINT `fk_author`
    FOREIGN KEY (`author_id`)
    REFERENCES `mydb`.`author` (`author_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_book`
    FOREIGN KEY (`book_id`)
    REFERENCES `mydb`.`books` (`book_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`books_on_loan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`books_on_loan` (
  `book_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `date_due` DATETIME NULL,
  `returned` TINYINT(1) NULL,
  PRIMARY KEY (`book_id`, `user_id`),
  INDEX `fk_loan_book_id_idx` (`book_id` ASC),
  INDEX `fk_loan_user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_loan_book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `mydb`.`books` (`book_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_loan_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

SELECT * from books	;

INSERT INTO user (user_username, user_email, user_password, user_firstname, user_lastname, role) VALUES ( 'lxg6892', 'lxg6892@g.rit.edu', 'leonas123', 'Leon', 'Grubisic','U');

INSERT INTO user (user_username, user_email, user_password, user_firstname, user_lastname, role) VALUES ( 'lxc8852', 'lxc8852@g.rit.edu', 'crnilegenda', 'Luka', 'Crnjakovic','L');

INSERT INTO user (user_username, user_email, user_password, user_firstname, user_lastname, role) VALUES ( 'mxm1234', 'mxm1234@g.rit.edu', 'miklos220', 'Mario', 'Miklos','L');

INSERT INTO user (user_username, user_email, user_password, user_firstname, user_lastname, role) VALUES ( 'ixm6752', 'ixm6752@g.rit.edu', 'mazalo6743', 'Ivan', 'Mazalovic','L');

INSERT INTO publisher (publisher_name, publisher_city) VALUES ( 'Audible.com', 'Newark');

INSERT INTO publisher (publisher_name, publisher_city) VALUES ( 'Nature America', 'New York City');

INSERT INTO publisher (publisher_name, publisher_city) VALUES ( 'Scholastic, Inc.', 'New York');

INSERT INTO publisher (publisher_name, publisher_city) VALUES ( 'Warner Bros Global Publishing', 'Burbank');

INSERT INTO genres (genre_name) VALUES ('Sci-Fi');

INSERT INTO genres (genre_name) VALUES ( 'Thriller');

INSERT INTO genres (genre_name) VALUES ('Romance');

INSERT INTO genres (genre_name) VALUES ('Fantasy');

INSERT INTO books (book_isbn, book_title, book_publisher_id, book_published_year, book_copies, book_location, book_genre_id) VALUES ( 'a', 'Lirael', 1, 2001, 7200, 'Australia', 4);

INSERT INTO books (book_isbn, book_title, book_publisher_id, book_published_year, book_copies, book_location, book_genre_id) VALUES ( '9780143109259', 'Modern Romance', 2, 2016, 125000, 'USA', 3);

INSERT INTO books (book_isbn, book_title, book_publisher_id, book_published_year, book_copies, book_location, book_genre_id) VALUES ( '9780451528964', 'A Journey to the Center of the Earth', 3, 2003, 7500000, 'USA', 1);

INSERT INTO books (book_isbn, book_title, book_publisher_id, book_published_year, book_copies, book_location, book_genre_id) VALUES ( '9780307474278', 'The Da Vinci Code', 4, 2003, 21000000, 'USA', 2);

INSERT INTO author (author_firstname, author_lastname) VALUES ('Garth', 'Nix');

INSERT INTO author (author_firstname, author_lastname) VALUES ('Aziz', 'Ansari');

INSERT INTO author (author_firstname, author_lastname) VALUES ('Jules', 'Verne');

INSERT INTO author (author_firstname, author_lastname) VALUES ('Dan', 'Brown');
INSERT INTO books_on_loan (book_id,user_id,date_due,returned) VALUES (1,1,'2017-05-17 10:01:00',0);


INSERT INTO author_book (author_id, book_id) VALUES (1, 1);

INSERT INTO author_book (author_id, book_id) VALUES (2, 2);

INSERT INTO author_book (author_id, book_id) VALUES (3, 3);

INSERT INTO author_book (author_id, book_id) VALUES (4, 4);