package ephyl.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseDtoTest {

    @Test
    void getName() {
        CourseDto courseDto = new CourseDto("New course" );
        assertEquals("New course", courseDto.getName());

    }

    @Test
    void setName() {
        CourseDto courseDto = new CourseDto();
        courseDto.setName("N");
        assertEquals("N", courseDto.getName());
    }
}