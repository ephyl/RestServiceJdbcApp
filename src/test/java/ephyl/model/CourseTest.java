package ephyl.model;

import ephyl.dto.StudentDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CourseTest {
    private static Course course;
    private static Course courseWithParams;
    private static Course courseWithParamsTeacher_id;

    @BeforeAll
    static void beforeAll() {
        course = new Course();
        courseWithParams = new Course(1, "test name");
        courseWithParamsTeacher_id = new Course("test name", 12);
    }

    @Test
    void setStudents() {
        List<StudentDto> studentList = Mockito.mock(List.class);
        when(studentList.size()).thenReturn(2);
        course.setStudents(studentList);
        courseWithParams.setStudents(studentList);
        assertEquals(2, course.getStudents().size());
        assertEquals(2, courseWithParams.getStudents().size());

    }

    @Test
    void getId() {
        assertEquals(1, courseWithParams.getId());
    }

    @Test
    void setId() {
        course.setId(3);
        assertEquals(3, course.getId());
    }

    @Test
    void getName() {
        assertEquals("test name", courseWithParams.getName());
    }

    @Test
    void setName() {
        assertNull(course.getName());
        String name = "Fine name";
        course.setName(name);
        assertEquals(name, course.getName());
    }
    @Test
    void get_Teacher_id() {
        assertEquals(12, courseWithParamsTeacher_id.getTeacher_id());
    }

    @Test
    void set_Teacher_id() {
        courseWithParamsTeacher_id.setTeacher_id(3);
        assertEquals(3, courseWithParamsTeacher_id.getTeacher_id());
    }

}