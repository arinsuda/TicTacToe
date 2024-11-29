-- MySQL Script generated by MySQL Workbench
-- Wed Nov 27 22:00:16 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema tictactoe
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tictactoe
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tictactoe` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `tictactoe` ;

-- -----------------------------------------------------
-- Table `tictactoe`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tictactoe`.`users` (
  `uid` BINARY(16) NOT NULL,
  `username` VARCHAR(30) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `age` INT NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `tictactoe`.`friends`(
    user_id BINARY(16) NOT NULL,
    friend_id BINARY(16) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('pending', 'accepted', 'rejected')),
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(uid) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(uid) ON DELETE CASCADE
)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `tictactoe`.`games`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tictactoe`.`games` (
    game_id BINARY(16) PRIMARY KEY,
    creator_id BINARY(16) NOT NULL,
    opponent_id BINARY(16),
    game_status ENUM('waiting', 'started', 'finished', 'inprogress') NOT NULL,
    current_turn BINARY(16),
    first_player_id BINARY(16) NOT NULL,
    winner_id BINARY(16),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (creator_id) REFERENCES users(uid) ON DELETE CASCADE,
    FOREIGN KEY (opponent_id) REFERENCES users(uid) ON DELETE SET NULL,
    FOREIGN KEY (winner_id) REFERENCES users(uid) ON DELETE SET NULL
) ENGINE = InnoDB;

-- เพิ่ม index เพื่อเพิ่มประสิทธิภาพการค้นหา
CREATE INDEX idx_creator_id ON games(creator_id);
CREATE INDEX idx_opponent_id ON games(opponent_id);
CREATE INDEX idx_current_turn ON games(current_turn);

-- -----------------------------------------------------
-- Table `tictactoe`.`GameLog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tictactoe`.`GameLog` (
  `id` BINARY(16) NOT NULL,
  `game_id` BINARY(16) NOT NULL,
  `player_x_id` BINARY(16) NOT NULL,
  `player_o_id` BINARY(16) NOT NULL,
  `position` INT NOT NULL,
  `move_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_GameLog_maingame_idx` (`game_id` ASC),
  INDEX `fk_GameLog_players_idx` (`player_x_id` ASC, `player_o_id` ASC),
  CONSTRAINT `fk_GameLog_maingame`
    FOREIGN KEY (`game_id`)
    REFERENCES `tictactoe`.`maingame` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_GameLog_players`
    FOREIGN KEY (`player_x_id`, `player_o_id`)
    REFERENCES `tictactoe`.`maingame` (`player_x_id`, `player_o_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `tictactoe`.`game_moves`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tictactoe`.`game_moves` (
    move_id BINARY(16) PRIMARY KEY,
    game_id BINARY(16) NOT NULL,
    player_id BINARY(16) NOT NULL,
    move_position VARCHAR(3) NOT NULL,
    move_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (game_id) REFERENCES games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES users(uid) ON DELETE CASCADE
)ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `tictactoe`.`game_invites`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tictactoe`.`game_invites`(
    invite_id BINARY(16) PRIMARY KEY,
    game_id BINARY(16) NOT NULL,
    inviter_id BINARY(16) NOT NULL,
    invitee_id BINARY(16) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('pending', 'accepted', 'rejected')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (game_id) REFERENCES games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (inviter_id) REFERENCES users(uid) ON DELETE CASCADE,
    FOREIGN KEY (invitee_id) REFERENCES users(uid) ON DELETE CASCADE
)ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `tictactoe`.`user_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tictactoe`.`user_history`(
    history_id BINARY(16) PRIMARY KEY,
    user_id BINARY(16) NOT NULL,
    game_id BINARY(16) NOT NULL,
    result VARCHAR(20) NOT NULL CHECK (result IN ('won', 'lost', 'draw')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(uid) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES games(game_id) ON DELETE CASCADE
)ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `tictactoe`.`user_stats`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `tictactoe`.`user_stats` (
    user_id BINARY(16) PRIMARY KEY,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    draws INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(uid) ON DELETE CASCADE
)ENGINE = InnoDB;

-- create user "devtictactoe"@"localhost" IDENTIFIED by "tictactoepassword";
-- grant all privileges on tictactoe.* to "devtictactoe"@"localhost";

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
