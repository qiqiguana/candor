#note: This file assumes you are using mysql5 with innodb tables enabled
#first you need to have the database created, and the user credentials set up, for example

CREATE DATABASE IF NOT EXISTS mfw_db;
USE mfw_db;

#note that you need to edit "persistence.xml" file inside "persistence.jar" file of the server-agent to put in matching values for the database

create user 'noen-mfw'@'localhost' identified by 'tru4A7rU';
grant all privileges on mfw_db.* to 'noen-mfw'@'localhost';

#and below are the sql statements to create the database

alter table bm_description drop foreign key FK78D2D608B6C1CC8C;
alter table bm_value drop foreign key FKF3035C7D8D6A0CB8;
alter table probe_description drop foreign key FKB823A1ED8D6A0CB8;
alter table probe_description drop foreign key FKB823A1EDB6C1CC8C;

#thanks to EADS for formatting the statements below, and for the hibernate_sequence table script

DROP TABLE IF EXISTS mfw_db.bm_description;
DROP TABLE IF EXISTS mfw_db.bm_value;
DROP TABLE IF EXISTS mfw_db.event;
DROP TABLE IF EXISTS mfw_db.probe_description;
DROP TABLE IF EXISTS mfw_db.target_description;
DROP TABLE IF EXISTS mfw_db.hibernate_sequence;

CREATE TABLE mfw_db.bm_description (
  id bigint(20) NOT NULL auto_increment,
  class varchar(255),
  data_type int(4),
  description varchar(255),
  name varchar(255),
  target_id bigint(20),
  PRIMARY KEY (id)) ENGINE=InnoDB;

CREATE TABLE mfw_db.bm_value (
  id bigint(20) NOT NULL auto_increment,
  value_precision int(11),
  value_time datetime,
  value_string varchar(255),
  bm_id bigint(20),
  PRIMARY KEY (id)) ENGINE=InnoDB;

CREATE TABLE  mfw_db.event (
  id bigint(20) NOT NULL auto_increment,
  message varchar(255),
  event_source varchar(255),
  event_time datetime,
  event_type int(4),
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE  mfw_db.hibernate_sequence (
  next_val bigint(20)
) ENGINE=InnoDB;

INSERT INTO mfw_db.hibernate_sequence (next_val) VALUES (1);

CREATE TABLE  mfw_db.probe_description (
  id bigint(20) NOT NULL auto_increment,
  endpoint varchar(255),
  bm_precision int(11),
  probe_name varchar(255),
  bm_id bigint(20),
  target_id bigint(20),
  PRIMARY KEY (id)) ENGINE=InnoDB;

CREATE TABLE  mfw_db.target_description (
  id bigint(20) NOT NULL auto_increment,
  target_name varchar(255),
  target_type varchar(255),
  PRIMARY KEY (id)) ENGINE=InnoDB;

alter table bm_description add index FK78D2D608B6C1CC8C (target_id), add constraint FK78D2D608B6C1CC8C foreign key (target_id) references target_description (id);
alter table bm_value add index FKF3035C7D8D6A0CB8 (bm_id), add constraint FKF3035C7D8D6A0CB8 foreign key (bm_id) references bm_description (id);
alter table probe_description add index FKB823A1ED8D6A0CB8 (bm_id), add constraint FKB823A1ED8D6A0CB8 foreign key (bm_id) references bm_description (id);
alter table probe_description add index FKB823A1EDB6C1CC8C (target_id), add constraint FKB823A1EDB6C1CC8C foreign key (target_id) references target_description (id);

