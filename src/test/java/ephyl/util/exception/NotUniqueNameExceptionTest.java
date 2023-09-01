package ephyl.util.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotUniqueNameExceptionTest {

    @Test
    void testToString() {
        NotUniqueNameException notUniqueNameException = new NotUniqueNameException();
        String ex =  "NotUniqueNameException{" +
                "INFO='" + "Not unique name'}";
        assertEquals( ex, notUniqueNameException.toString());
    }
}