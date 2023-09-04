package ephyl.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ephyl.util.ConnectionManager;
import ephyl.model.Gender;
import ephyl.model.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Testcontainers
public class StudentDaoJdbcTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    ).withInitScript("init_postgresql.sql");
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

        studentDaoJdbc = new StudentDaoJdbc(params);
    }

    @Test
    void shouldAddStudent() throws SQLException {
        Student student = new Student("Tom", 0, Gender.MALE);
        Student newStudent = Mockito.mock(Student.class);
        long id = studentDaoJdbc.addNew(new Student("Tom", 50, Gender.MALE));
        assertEquals(4, id);
    }

    @Test
    void shouldFindStudentById() throws SQLException {
        int id = 1;
        Optional<Student> student = studentDaoJdbc.findById(id);
        assertTrue(student.isPresent());
        assertEquals(35, student.get().getAge());
        assertEquals("Egor Filippov", student.get().getName());
        assertEquals(1, student.get().getId());
    }

    @Test
    void shouldNotFindStudentByWrongId() throws SQLException {
        int id = 4;
        Optional<Student> student = studentDaoJdbc.findById(id);
        assertFalse(student.isPresent());
    }

    @Test
    void shouldDeleteStudent() throws SQLException {
        studentDaoJdbc.delete(3);
        assertFalse(studentDaoJdbc.findById(3).isPresent());
    }

    @Test
    void shouldGetStudentCourses() throws SQLException {
        int studentId = 1;
        Optional<Student> student = studentDaoJdbc.findById(studentId);
        assertTrue(student.isPresent());
        assertEquals(2, student.get().getCourseList().size());
        assertEquals("Java", student.get().getCourseList().get(0).getName());
    }

    @Test
    void update() throws SQLException {
        Student studentToBeUpdated = studentDaoJdbc.findById(1).get();
        studentToBeUpdated.setName("John Malkovich");
        studentToBeUpdated.setAge(66);
        studentToBeUpdated.setGender(Gender.X_GENDER);

       assertTrue(studentDaoJdbc.update(studentToBeUpdated));
        assertEquals("John Malkovich", studentDaoJdbc.findById(1).get().getName());
        assertEquals(66, studentDaoJdbc.findById(1).get().getAge());
        assertEquals("X_GENDER", studentDaoJdbc.findById(1).get().getGender().toString());
    }

    @Test
    void not_update_name_notUnique() throws SQLException {
        Student studentToBeUpdated = studentDaoJdbc.findById(1).get();
        studentToBeUpdated.setName("Darya Kasatkina");
        assertFalse(studentDaoJdbc.update(studentToBeUpdated));
        assertNotEquals("Darya Kasatkina", studentDaoJdbc.findById(1).get().getName());
    }

    @Test
    void isStudentNewNameUnique() {
        Student testStudent = Mockito.mock(Student.class);
        when(testStudent.getName()).thenReturn("Unique name");
        assertTrue(studentDaoJdbc.isStudentNewNameUnique(testStudent));
    }

    @Test
    void isStudentNewNameUnique_name_not_unique() {
        Student testStudent = Mockito.mock(Student.class);
        when(testStudent.getName()).thenReturn("Egor Filippov");
        assertFalse(studentDaoJdbc.isStudentNewNameUnique(testStudent));
    }

    @Test
    void is_Id_In_Db_positive() {
        Student testStudent = Mockito.mock(Student.class);
        when(testStudent.getId()).thenReturn(1);
        assertTrue(studentDaoJdbc.isIdInBase(testStudent));
    }

    @Test
    void is_Id_In_Db_negative() {
        Student testStudent = Mockito.mock(Student.class);
        when(testStudent.getId()).thenReturn(100);
        assertFalse(studentDaoJdbc.isIdInBase(testStudent));
    }
}