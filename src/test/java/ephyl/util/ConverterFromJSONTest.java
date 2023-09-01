package ephyl.util;

import ephyl.dto.StudentDto;
import ephyl.model.Gender;
import ephyl.util.exception.StudentNotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConverterFromJSONTest {

    @Test
    void convertFromJson() throws IOException {
        String str = "{\"name\":\"Ego\",\"age\":75,\"gender\":\"MALE\"}";
        final HttpServletRequest request = mock(HttpServletRequest.class);
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("POST");
        StudentDto jsonStudentDto = ConverterFromJSON.convertFromJson(request, new StudentDto()).orElseThrow(StudentNotFoundException::new);
        assertEquals( "Ego", jsonStudentDto.getName());
        assertEquals( 75, jsonStudentDto.getAge());
        assertEquals(Gender.MALE, jsonStudentDto.getGender());
    }

    @Test
    void testConvertFromJson() {
    }
}