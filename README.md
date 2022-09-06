https://docs.google.com/forms/d/e/1FAIpQLSdCBja05ZrrNeqH5pdg1sj2--keNaXoCpAui_P2AOfjCwqbwQ/viewform

## TODO

1. ~~Create students CRUD~~
2. ~~Create courses CRUD~~
3. ~~Create API for students to register to courses~~
4. ~~Create abilities for user to view all relationships between students and courses~~
   1. ~~Filter all students with a specific course~~
   2. ~~Filter all courses for a specific student~~
   3. ~~Filter all courses without any students~~
   4. ~~Filter all students without any courses~~
5. ~~Tests~~
6. ~~Docker Compose~~
7. ~~Karate Tests~~
8. Transaction management

## Technology Stack

1. Spring Boot
2. Lombok
3. PostgreSQL
4. Jackson
5. MapStruct
6. Apache Commons
7. Karate
8. Testcontainers
9. Docker

## How To Run

1. Start database
   - `cd docker/postgres`
   - `docker-compose -f docker-compose.yml -f docker-compose.local.yml up -d --force-recreate`
2. Start application
- With Maven
  - With tests (port 8093)
    - `mvn spring-boot:run`
  - Without tests (port 8093)
    - `mvn -DskipTests spring-boot:run`
- With Docker
    - `docker-compose up -d --force-recreate`

## How To Run Tests

- Invokes all tests
  - `mvn test`

Tests have a maximum of 5 students per course and 3 courses per student.

## Project Setup

Open project in your preferred IDE as a Maven project.

## Endpoints

- Courses
  - `GET /api/courses` - list of courses
  - `GET /api/courses/{id}` - get a courses
  - `POST /api/courses` - add a course
  - `PUT /api/courses` - update a course
  - `DELETE /api/courses` - delete a course

- Students
  - `GET /api/students` - list of students
  - `GET /api/students/{id}` - get a students
  - `POST /api/students` - add a student
  - `PUT /api/students` - update a student
  - `DELETE /api/students` - delete a student

- Register a Student in a course
  - `PUT /api/courses/{courseId}/student/{studentId}` - limited by max loads as defined in properties files
