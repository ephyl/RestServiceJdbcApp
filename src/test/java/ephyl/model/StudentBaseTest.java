package ephyl.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StudentBaseTest {

    private static Student newStudent;
    private static Student studentToSetNameAgeGender;
    @BeforeAll
    static void beforeAll() {
        newStudent = new Student("Mike", 20, Gender.MALE);
        List<Course> courseList = Mockito.mock(List.class);
        when(courseList.size()).thenReturn(2);

        newStudent.setId(4);
        newStudent.setCourseList(courseList);

        studentToSetNameAgeGender =  new Student();

    }

    @Test
    void getStudentId() {
        assertEquals(4, newStudent.getId());
    }
    @Test
    void getStudentName() {
        assertEquals("Mike", newStudent.getName());
    }
    @Test
    void getStudentAge() {
        assertEquals(20, newStudent.getAge());
    }
    @Test
    void setStudentAge() {
        newStudent.setAge(21);
        assertEquals(21, newStudent.getAge());
    }
    @Test
    void getStudentGender() {
        assertEquals("MALE", newStudent.getGender().name());
    }
    @Test
    void getStudentCourses() {
        assertEquals(2, newStudent.getCourseList().size());
    }
    @Test
    void setNameToEmptyStudent() {
        studentToSetNameAgeGender.setName("Bob");
        assertEquals("Bob",studentToSetNameAgeGender.getName());
    }
    @Test
    void setAgeToEmptyStudent() {
        studentToSetNameAgeGender.setAge(33);
        assertEquals(33,studentToSetNameAgeGender.getAge());
    }
    @Test
    void setGenderToEmptyStudent() {
        studentToSetNameAgeGender.setGender(Gender.FEMALE);
        assertEquals("FEMALE",studentToSetNameAgeGender.getGender().name());
    }

}
