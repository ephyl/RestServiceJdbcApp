package ephyl.sevlets;

import ephyl.dto.CourseDto;
import ephyl.model.Course;
import ephyl.model.Student;
import ephyl.service.CourseService;
import ephyl.util.ConverterFromJSON;
import ephyl.util.exception.CourseNotFoundException;
import ephyl.util.validator.CourseValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class CourseServletTest {
    CourseService courseService;
    Course course;
    CourseDto courseDto;
    CourseServlet servlet;
    final HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter printWriter = mock(PrintWriter.class);


    @BeforeEach
    void init() {
        courseService = mock(CourseService.class);
        servlet = new CourseServlet(courseService);
        course = new Course();
        courseDto = new CourseDto();

    }

    @Test
    void doGet() throws IOException, ServletException {
        int id = 1;
        String str = "{\"id\":\"" + id + "\"}";
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("GET");
        when(courseService.findById(id)).thenReturn(courseDto);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);
        verify(courseService).findById(id);
        verify(response).getWriter();
    }

    @Test
    void doPost() throws IOException, ServletException {
        String str = "{\"name\":\"Course Name\", \"teacher_id\":1}";
        Course course1 = new Course("Course Name", 1);
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("POST");
//        when(courseService.addNew(course)).thenReturn(1L);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);
        verify(courseService).addNew(course1);

        verify(response).getWriter();
    }

    @Test
    void doPut() throws IOException, ServletException {
        String str = "{ \"id\":1, \"name\":\"Course Name\", \"teacher_id\":1}";
        Course course1 = new Course("Course Name", 1);
        course1.setId(1);
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("PUT");
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPut(request, response);
        verify(courseService).update(course1);

        verify(response).getWriter();
    }

    @Test
    void doDelete() throws ServletException, IOException {
        int id = 1;
        String str = "{\"id\":\"" + id + "\"}";
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("DELETE");
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doDelete(request, response);
        verify(courseService).delete(id);
    }
}