DROP DATABASE dis;
CREATE DATABASE dis;
Use dis;

CREATE TABLE IF NOT EXISTS students
(
  studentid   INT          NOT NULL AUTO_INCREMENT,
  email    VARCHAR(40)  NOT NULL,
  active   BOOLEAN,
  created  DATETIME     NOT NULL DEFAULT NOW(),
  password VARCHAR(200) NOT NULL,
  PRIMARY KEY (studentid)
);

CREATE TABLE IF NOT EXISTS courses
(
  courseid	INT	NOT NULL AUTO_INCREMENT,
  name VARCHAR(255)	NOT NULL,
  PRIMARY KEY (courseid)
);

CREATE TABLE IF NOT EXISTS studentCourses
(
  studentcoursesid INT NOT NULL AUTO_INCREMENT,
  courseid INT NOT NULL,
  studentid INT NOT NULL,
  PRIMARY KEY(studentcoursesid)
);

ALTER TABLE `studentCourses`
  ADD FOREIGN KEY (`studentid`)
REFERENCES students (studentid)
  ON UPDATE RESTRICT;

ALTER TABLE `studentCourses`
  ADD FOREIGN KEY (`courseid`)
REFERENCES courses (courseid)
  ON UPDATE RESTRICT;

INSERT INTO `dis`.`students`(`email`,`active`,`password`)
VALUES ('admin@admin.dk', TRUE, '5F4DCC3B5AA765D61D8327DEB882CF99'),
  ('christian@cbs.dk', TRUE, '57B7E5A0C19DB434327129979195DFDA'),
  ('mathias@cbs.dk', TRUE, '57B7E5A0C19DB43927129979195DFDA'),
  ('morten@cbs.dk', TRUE, '57B7E5A0DCD9DB434327129979195DFDA')
;

INSERT INTO `dis`.`courses`(`name`)
VALUES ('Distribuerede Systemer'),
  ('VØS 3'),
  ('Organisationsteori'),
  ('Makroøkonomi'),
  ('Projektledelse'),
  ('Big data')
;

INSERT INTO `dis`.`studentCourses`(`studentid`, `courseid`)
VALUES (4, 4),
  (4, 2),
  (4, 3),
  (2, 5),
  (2, 4),
  (2, 2),
  (3, 2),
  (3, 4),
  (3, 3)
;

/*
SELECT `students`.`email`, `courses`.`name` FROM `students`, `courses`
WHERE `courses`.`courseid`=1;

SELECT * FROM studentCourses sc
  INNER JOIN students s ON s.studentid = sc.studentid
  INNER JOIN courses c ON c.courseid = sc.courseid
  */


