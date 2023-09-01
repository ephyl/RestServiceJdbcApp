package ephyl.util.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherNotFoundExceptionTest {

    @Test
    void testToString() {
        TeacherNotFoundException teacherNotFoundException =  new TeacherNotFoundException();
        String ex =  "TeacherNotFoundException{" +
                "info='" + "Teacher not found" + '\'' +
                '}';
        assertEquals( ex, teacherNotFoundException.toString());
    }
}