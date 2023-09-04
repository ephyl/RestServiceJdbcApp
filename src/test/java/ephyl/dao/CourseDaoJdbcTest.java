package ephyl.dao;


import ephyl.util.ConnectionManager;
import ephyl.model.Course;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;



@Testcontainers
class CourseDaoJdbcTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    ).withInitScript("init_postgresql.sql");
    CourseDaoJdbc courseDaoJdbc;

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
    }

    @Test
    void getAll() {
        assertEquals(4, courseDaoJdbc.getAll().size());
        System.out.println(postgres.getExposedPorts());
    }

    @Test
    void addNew() {
        Course course  = new Course("Test Course",1 );
        assertEquals(5 , courseDaoJdbc.addNew(course));
    }
    @Test
    void findById() {
       assertTrue(courseDaoJdbc.findById(1).isPresent());
        assertEquals(1 , courseDaoJdbc.findById(1).get().getId());
    }

    @Test
    void delete() {
        Course course = Mockito.mock(Course.class);
        when(course.getId()).thenReturn(3);
        courseDaoJdbc.delete(course.getId());
        assertFalse( courseDaoJdbc.findById(3).isPresent());
    }

    @Test
    void update() {
        Course course = Mockito.mock(Course.class);
        when(course.getId()).thenReturn(2);
        when(course.getName()).thenReturn("New Course Name");
        when(course.getTeacher_id()).thenReturn(3);
        assertTrue(courseDaoJdbc.update(course));
        assertEquals(course.getName(), courseDaoJdbc.findById(course.getId()).get().getName());
        assertEquals(course.getTeacher_id(), courseDaoJdbc.findById(course.getId()).get().getTeacher_id());
    }



    @Test
    void isNameUnique_positive() {
        Course course = Mockito.mock(Course.class);
        when(course.getId()).thenReturn(2);
        when(course.getName()).thenReturn("New Unique Name");
        assertTrue(courseDaoJdbc.isNameUnique(course));

    }
    @Test
    void isNameUnique_negative() {
        Course course = Mockito.mock(Course.class);
        when(course.getId()).thenReturn(2);
        when(course.getName()).thenReturn("Java");
        assertFalse(courseDaoJdbc.isNameUnique(course));

    }
}