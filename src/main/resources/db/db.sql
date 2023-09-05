CREATE DATABASE students_database ENCODING 'UTF-8';

---Студенты
CREATE TABLE IF NOT EXISTS students (
    studentId VARCHAR PRIMARY KEY,
    firstName VARCHAR NOT NULL,
    lastName VARCHAR NOT NULL
);

---Списки классов
CREATE TABLE IF NOT EXISTS learningClasses (
    learningClassId VARCHAR PRIMARY KEY,
    title VARCHAR NOT NULL,
    description VARCHAR NOT NULL
);

---Списки записей в классы
CREATE TABLE IF NOT EXISTS enrollments (
    enrollmentId VARCHAR PRIMARY KEY,
    student VARCHAR NOT NULL,
    learningClass VARCHAR NOT NULL,
    FOREIGN KEY (student) REFERENCES students (studentId),
    FOREIGN KEY (learningClass) REFERENCES learningClasses (learningClassId)
);
