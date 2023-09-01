package ephyl.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testcontainers.shaded.org.bouncycastle.math.raw.Mod;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TeacherTest {
    private static Teacher teacher;
    @BeforeAll
    static void beforeAll(){
       teacher = new Teacher(1, "Klass Gerbershtain");
    }


    @Test
    void set_and_getCourseList() {
    List<Course> courseList = Mockito.mock(List.class);
    when(courseList.size()).thenReturn(3);
    teacher.setCourseList(courseList);
    assertEquals(3,teacher.getCourseList().size());

    }

    @Test
    void getId() {
        teacher.setId(5);
        assertEquals(5, teacher.getId());
    }

    @Test
    void setId() {
    }

    @Test
    void getName() {
        String expected = "Klass Gerbershtain";
        assertEquals(expected, teacher.getName());
    }

    @Test
    void setName() {
        Teacher teacherWithoutName =  new Teacher();
        teacherWithoutName.setName("Elena Buglover");
        assertNotNull(teacherWithoutName);
        assertNotNull(teacherWithoutName.getName());
        assertEquals("Elena Buglover", teacherWithoutName.getName());
    }
}