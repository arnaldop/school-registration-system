CREATE TABLE courses
(
    id          SERIAL PRIMARY KEY,
    identifier  TEXT NOT NULL,
    name        TEXT NOT NULL,
    description TEXT
);

CREATE TABLE STUDENTS
(
    id         SERIAL PRIMARY KEY,
    identifier TEXT NOT NULL,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL
);

CREATE TABLE courses_students
(
    course_id  INTEGER NOT NULL,
    student_id INTEGER NOT NULL
);
