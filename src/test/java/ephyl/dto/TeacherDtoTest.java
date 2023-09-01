package ephyl.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherDtoTest {

    @Test
    void getName() {
        TeacherDto teacherDto = new TeacherDto("TeacherName");
        assertEquals("TeacherName", teacherDto.getName());
    }

    @Test
    void setName() {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setName("NewName");
        assertEquals("NewName", teacherDto.getName());
    }
}