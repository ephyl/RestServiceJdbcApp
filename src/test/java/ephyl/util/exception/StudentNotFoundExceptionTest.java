package ephyl.util.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentNotFoundExceptionTest {

    @Test
    void testToString() {
        StudentNotFoundException studentNotFoundException =  new StudentNotFoundException();
        String ex =  "StudentNotFoundException{" +
                "info='" + "Student not found" + '\'' +
                '}';
        assertEquals( ex, studentNotFoundException.toString());
    }
}