package ephyl.util.validator;

import ephyl.util.ConnectionManager;
import ephyl.dao.StudentDaoJdbc;
import ephyl.dto.StudentDto;
import ephyl.model.Gender;
import ephyl.util.exception.NotUniqueNameException;
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

class StudentDtoValidatorTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    ).withInitScript("init_postgresql.sql");
    StudentDaoJdbc studentDaoJdbc;
    StudentDto student;

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        postgres.start();
        Map <String, String> params = new HashMap<>();
        params.put("url", postgres.getJdbcUrl());
        params.put("username", postgres.getUsername());
        params.put("password", postgres.getPassword());


        studentDaoJdbc = new StudentDaoJdbc(params);
        student = Mockito.mock(StudentDto.class);
    }


    @Test
    void validate_positive() {
        when(student.getName()).thenReturn("StudentName");
        when(student.getAge()).thenReturn(18);
        when(student.getGender()).thenReturn(Gender.MALE);
        when(student.getId()).thenReturn(1);

        StudentDtoValidator studentDtoValidator = new StudentDtoValidator(studentDaoJdbc);

        assertTrue(studentDtoValidator.validate(student));

    }

    @Test
    void validate_negative_Age() {
        when(student.getName()).thenReturn("StudentName");
        when(student.getAge()).thenReturn(-5);
        when(student.getGender()).thenReturn(Gender.MALE);
        when(student.getId()).thenReturn(1);
        StudentDtoValidator studentDtoValidator = new StudentDtoValidator(studentDaoJdbc);
        assertFalse(studentDtoValidator.validate(student));
    }

    @Test
    void validate_negative_repeat_name() {
        when(student.getName()).thenReturn("Darya Kasatkina");
        when(student.getAge()).thenReturn(45);
        when(student.getGender()).thenReturn(Gender.MALE);
        when(student.getId()).thenReturn(1);
        StudentDtoValidator studentDtoValidator = new StudentDtoValidator(studentDaoJdbc);
        assertThrows(NotUniqueNameException.class, () -> studentDtoValidator.validate(student));
    }

    @Test
    void contsructor() {
        StudentDtoValidator studentDtoValidator = new StudentDtoValidator();
        assertEquals(StudentDtoValidator.class, studentDtoValidator.getClass());
    }

}