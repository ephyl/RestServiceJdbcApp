package ephyl.util.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseNotFoundExceptionTest {

    @Test
    void testToString() {
        CourseNotFoundException courseNotFoundException =  new CourseNotFoundException();
        String ex =  "CourseNotFoundException{" +
                "info='Course not found'}";
        assertEquals( ex, courseNotFoundException.toString());
    }
}