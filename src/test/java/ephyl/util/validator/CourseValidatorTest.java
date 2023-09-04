package ephyl.util.validator;

import ephyl.util.ConnectionManager;
import ephyl.dao.CourseDaoJdbc;
import ephyl.model.Course;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CourseValidatorTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    ).withInitScript("init_postgresql.sql");
    CourseDaoJdbc courseDaoJdbc;
    Course course;

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

        courseDaoJdbc = new CourseDaoJdbc(params);
        course = Mockito.mock(Course.class);
    }


    @Test
    void validate() {

        when(course.getName()).thenReturn("CourseName");
        when(course.getId()).thenReturn(1);

        CourseValidator courseValidator = new CourseValidator(courseDaoJdbc);

        assertTrue(courseValidator.validate(course));
    }
    @Test
    void validate_negative_empty_name() {

        when(course.getName()).thenReturn("");
        when(course.getId()).thenReturn(1);

        CourseValidator courseValidator = new CourseValidator(courseDaoJdbc);

        assertFalse(courseValidator.validate(course));
    }
    @Test
    void validate_negative_null_name() {

        when(course.getId()).thenReturn(1);

        CourseValidator courseValidator = new CourseValidator(courseDaoJdbc);

        assertFalse(courseValidator.validate(course));
    }
    @Test
    void validate_negative_not_unique_name() {
        when(course.getName()).thenReturn("Java");
        when(course.getId()).thenReturn(2);

        CourseValidator courseValidator = new CourseValidator(courseDaoJdbc);
        assertFalse(courseValidator.validate(course));
    }
    @Test
    void constructor(){
        CourseValidator courseValidator = new CourseValidator();
        assertEquals(CourseValidator.class, courseValidator.getClass());
    }
}