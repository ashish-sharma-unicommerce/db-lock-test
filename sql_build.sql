create database DBLockTest;
CREATE TABLE db_lock (id INT NOT NULL AUTO_INCREMENT, lock_path varchar(45), updated datetime, PRIMARY KEY (id), UNIQUE KEY `UK_lock_path` (`lock_path`)) ENGINE = InnoDB;

create table temp_order(id INT NOT NULL AUTO_INCREMENT, code varchar(45), updated datetime, PRIMARY KEY (id), UNIQUE KEY `UK_lock_path` (`code`)) ENGINE = InnoDB;
