CREATE DATABASE IF NOT EXISTS dis;
USE dis;

/* Create Tables */

CREATE TABLE IF NOT EXISTS calendar
(
  calendarid    INT          NOT NULL AUTO_INCREMENT,
  name          VARCHAR(255) NOT NULL,
  active        TINYINT,
  createdby     VARCHAR(255) NOT NULL,
  -- 1 = public
  -- 2 = private
  privatePublic TINYINT      NOT NULL
  COMMENT '1 = public
	2 = private',
  PRIMARY KEY (calendarid)
);


CREATE TABLE IF NOT EXISTS dailyupdate
(
  date                DATETIME     NOT NULL UNIQUE,
  apparentTemperature DOUBLE,
  summary             TEXT,
  qotd                VARCHAR(300) NOT NULL,
  msg_type            VARCHAR(100) NOT NULL,
  update_timestamp    TIMESTAMP DEFAULT NOW() ON UPDATE NOW(),
  PRIMARY KEY (date)
);


CREATE TABLE IF NOT EXISTS events
(
  eventid     INT        NOT NULL AUTO_INCREMENT,
  type        INT        NOT NULL,
  location    INT,
  createdby   INT        NOT NULL,
  start       DATETIME   NOT NULL,
  end         DATETIME   NOT NULL,
  name        VARCHAR(0) NOT NULL,
  text        TEXT       NOT NULL,
  -- Decides wether the event is an import-event or user created
  --
  customevent BOOLEAN COMMENT 'Decides wether the event is an import-event or user created
',
  calendarid  INT        NOT NULL,
  PRIMARY KEY (eventid)
);


CREATE TABLE IF NOT EXISTS locationdata
(
  locationdataid INT NOT NULL AUTO_INCREMENT,
  longitude      INT NOT NULL,
  latitude       INT UNIQUE,
  PRIMARY KEY (locationdataid)
);


CREATE TABLE IF NOT EXISTS notes
(
  noteid    INT          NOT NULL AUTO_INCREMENT,
  eventid   INT          NOT NULL,
  createdby INT          NOT NULL,
  text      TEXT,
  dateTime  DATETIME     NOT NULL,
  active    BIT,
  PRIMARY KEY (noteid)
);


CREATE TABLE IF NOT EXISTS roles
(
  roleid INT          NOT NULL AUTO_INCREMENT,
  userid INT          NOT NULL,
  type   VARCHAR(200) NOT NULL,
  PRIMARY KEY (roleid)
);


CREATE TABLE IF NOT EXISTS userevents
(
  userid     INT NOT NULL,
  calendarid INT NOT NULL
);


CREATE TABLE IF NOT EXISTS users
(
  userid   INT          NOT NULL AUTO_INCREMENT,
  email    VARCHAR(40)  NOT NULL,
  active   BOOLEAN,
  created  DATETIME     NOT NULL DEFAULT NOW(),
  password VARCHAR(200) NOT NULL,
  PRIMARY KEY (userid)
);

/* Create Dummy Account */

INSERT INTO `dis`.`users`(`email`,`active`,`password`)
VALUES ('admin@admin.dk', TRUE, 'd6YSr320JnLXlp8YYxUcNQ==');


/* Create Foreign Keys */

ALTER TABLE events
  ADD FOREIGN KEY (calendarid)
REFERENCES calendar (calendarid)
  ON UPDATE RESTRICT;


ALTER TABLE userevents
  ADD FOREIGN KEY (calendarid)
REFERENCES calendar (calendarid)
  ON UPDATE RESTRICT;


ALTER TABLE notes
  ADD FOREIGN KEY (eventid)
REFERENCES events (eventid)
  ON UPDATE RESTRICT;


ALTER TABLE events
  ADD FOREIGN KEY (location)
REFERENCES locationdata (locationdataid)
  ON UPDATE RESTRICT;


ALTER TABLE events
  ADD FOREIGN KEY (createdby)
REFERENCES users (userid)
  ON UPDATE RESTRICT;


ALTER TABLE roles
  ADD FOREIGN KEY (userid)
REFERENCES users (userid)
  ON UPDATE RESTRICT;


ALTER TABLE userevents
  ADD FOREIGN KEY (userid)
REFERENCES users (userid)
  ON UPDATE RESTRICT;


ALTER TABLE notes
  ADD FOREIGN KEY (createdby)
REFERENCES users (userid)
  ON UPDATE RESTRICT;