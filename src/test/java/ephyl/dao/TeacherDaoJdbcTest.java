package ephyl.dao;

import ephyl.util.ConnectionManager;
import ephyl.model.Teacher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Testcontainers
class TeacherDaoJdbcTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    ).withInitScript("init_postgresql.sql");
    TeacherDaoJdbc teacherDaoJdbc;

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        postgres.start();

        Connection connection = ConnectionManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        teacherDaoJdbc = new TeacherDaoJdbc(connection);
    }
    @Test
    void getAll() {
        assertEquals(3, teacherDaoJdbc.getAll().size());
    }

    @Test
    void findById() {
        assertTrue(teacherDaoJdbc.findById(1).isPresent());
        assertEquals(1 , teacherDaoJdbc.findById(1).get().getId());
    }

    @Test
    void addNew() {
        Teacher teacher = new Teacher("New Teacher Name");
        assertEquals(4, teacherDaoJdbc.addNew(teacher));
    }

    @Test
    void delete() {
        Teacher teacher = Mockito.mock(Teacher.class);
        when(teacher.getId()).thenReturn(2);
        assertTrue(teacherDaoJdbc.findById(teacher.getId()).isPresent());
        teacherDaoJdbc.delete(teacher.getId());
        Assertions.assertFalse(teacherDaoJdbc.findById(teacher.getId()).isPresent());
    }

    @Test
    void update() {
        Teacher teacher = Mockito.mock(Teacher.class);
        when(teacher.getId()).thenReturn(1);
        when(teacher.getName()).thenReturn("Updated Teacher Name");
        assertTrue(teacherDaoJdbc.update(teacher));
        assertEquals(teacher.getName(), teacherDaoJdbc.findById(teacher.getId()).get().getName());
    }

    @Test
    void isNameUnique_positive() {
        Teacher teacher = Mockito.mock(Teacher.class);
        when(teacher.getId()).thenReturn(1);
        when(teacher.getName()).thenReturn("New Unique Name");
        assertTrue(teacherDaoJdbc.isNameUnique(teacher));
    }
    @Test
    void isNameUnique_negative() {
        Teacher teacher = Mockito.mock(Teacher.class);
        when(teacher.getId()).thenReturn(1);
        when(teacher.getName()).thenReturn("Testing Teacher");
        Assertions.assertFalse(teacherDaoJdbc.isNameUnique(teacher));
    }
    @Test
    void isNameUnique_sameName() {
        Teacher teacher = Mockito.mock(Teacher.class);
        when(teacher.getId()).thenReturn(1);
        when(teacher.getName()).thenReturn("Java Teacher");
        assertTrue(teacherDaoJdbc.isNameUnique(teacher));
    }
}