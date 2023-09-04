package ephyl.dao;

import ephyl.util.ConnectionManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseManagerDaoJdbcTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    ).withInitScript("init_postgresql.sql");
    CourseManagerDaoJdbc courseManagerDaoJdbc;
    StudentDaoJdbc studentDaoJdbc;

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        postgres.start();
        Map<String, String> params = new HashMap<>();
        params.put("url", postgres.getJdbcUrl());
        params.put("username", postgres.getUsername());
        params.put("password", postgres.getPassword());

        courseManagerDaoJdbc = new CourseManagerDaoJdbc(params);
        studentDaoJdbc = new StudentDaoJdbc(params);
    }

    @Test
    void assignCourses() {
        int studentId = 1;
        int[] courseId = {4, 3};
        assertEquals(2, courseManagerDaoJdbc.assignCourses(studentId, courseId));
    }

    @Test
    void unAssignCourses() {
        int studentId = 3;
        int[] courseId = {3};
        courseManagerDaoJdbc.unAssignCourses(studentId, courseId);

        assertEquals(1, studentDaoJdbc.findById(studentId).get().getCourseList().size());
    }

}