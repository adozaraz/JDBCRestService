CREATE DATABASE students_database ENCODING 'UTF-8';

---Студенты
CREATE TABLE IF NOT EXISTS students (
    studentId SERIAL PRIMARY KEY,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL
);

SELECT u.studentId, u.firstName, u.lastName FROM students AS u WHERE u.studentId = (?);
SELECT * FROM students;
SELECT * FROM students where firstName = (?);
SELECT * FROM students where lastName = (?);
SELECT * FROM students where firstName = (?) AND lastName = (?);
INSERT INTO students (studentId, firstName, lastName) VALUES ((?), (?), (?)) RETURNING studentId;
UPDATE students SET firstName = (?), lastName = (?) WHERE studentId = (?) RETURNING studentId;
DELETE FROM students WHERE studentId = (?) AND firstName = (?) AND lastName = (?) RETURNING studentId;


---Списки классов
CREATE TABLE IF NOT EXISTS learningClasses (
    learningClassId SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL
);

SELECT learningClassId, title, description FROM learningCLasses WHERE learningClassId = (?);
INSERT INTO learningClasses (learningClassId, title, description) VALUES ((?), (?), (?)) RETURNING learningClassId;
UPDATE learningClasses SET title = (?), description = (?) WHERE learningClassId = (?) RETURNING learningClassId;
DELETE FROM learningClasses WHERE learningClassId = (?) AND title = (?) AND description = (?) RETURNING learningClassId;


---Списки записей в классы
CREATE TABLE IF NOT EXISTS enrollments (
    enrollmentId SERIAL PRIMARY KEY,
    student TEXT NOT NULL,
    learningClass TEXT NOT NULL,
    FOREIGN KEY (student) REFERENCES students (studentId),
    FOREIGN KEY (learningClass) REFERENCES learningClasses (learningClassId)
);

SELECT e.enrollmentId, s.studentId, s.firstName, s.lastName, l.learningClassId, l.title, l.description FROM enrollments AS e JOIN students AS s ON e.student = s.studentId JOIN learningClasses AS l ON e.learningClass = l.learningClassId WHERE e.enrollmentId = (?);
SELECT s.studentId, s.firstName, s.lastName, l.learningClassId, l.title, l.description FROM enrollments AS e JOIN students AS s ON e.student = s.studentId JOIN learningClasses AS l ON e.learningClass = l.learningClassId WHERE e.student = (?);
SELECT s.studentId, s.firstName, s.lastName, l.learningClassId, l.title, l.description FROM enrollments AS e JOIN students AS s ON e.student = s.studentId JOIN learningClasses AS l ON e.learningClass = l.learningClassId WHERE e.learningClass = (?);
INSERT INTO enrollments (enrollmentId, student, learningClass) VALUES ((?), (?), (?)) RETURNING enrollmentId;
UPDATE enrollments SET student = (?), learningClass = (?) WHERE enrollmentId = (?) RETURNING enrollmentId;
DELETE FROM enrollments WHERE enrollmentId = (?) AND learningClass = (?) AND student = (?) RETURNING enrollmentId;
DELETE FROM enrollments WHERE student = (?) RETURNING enrollmentId;
DELETE FROM enrollments WHERE learningClass = (?) RETURNING enrollmentId;
