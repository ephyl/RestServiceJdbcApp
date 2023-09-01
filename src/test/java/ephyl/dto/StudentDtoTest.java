package ephyl.dto;

import ephyl.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentDtoTest {
    private StudentDto studentDto;
    private StudentDto emptyStudent;

    @BeforeEach
    void init() {
        studentDto = new StudentDto("StudentName", 32, Gender.MALE);
        emptyStudent = new StudentDto();
    }

    @Test
    void getAge() {
        assertEquals(32, studentDto.getAge());
    }

    @Test
    void setAge() {
        emptyStudent.setAge(55);
        assertEquals(55, emptyStudent.getAge());
    }

    @Test
    void getName() {
        assertEquals("StudentName", studentDto.getName());
    }

    @Test
    void setName() {
        emptyStudent.setName("EmptyName");
        assertEquals("EmptyName", emptyStudent.getName());
    }

    @Test
    void getGender() {
        assertEquals(Gender.MALE,studentDto.getGender());
    }

    @Test
    void setGender() {
        emptyStudent.setGender(Gender.X_GENDER);
        assertEquals(Gender.X_GENDER, emptyStudent.getGender());
    }

    @Test
    void getId() {
        studentDto.setId(1123);
        assertEquals(1123, studentDto.getId());
    }

    @Test
    void setId() {
        studentDto.setId(5);
        assertEquals(5, studentDto.getId());
    }
}