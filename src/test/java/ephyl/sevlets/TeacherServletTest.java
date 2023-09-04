package ephyl.sevlets;

import ephyl.dto.StudentDto;
import ephyl.model.Gender;
import ephyl.model.Student;
import ephyl.model.Teacher;
import ephyl.service.StudentService;
import ephyl.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class TeacherServletTest {
    TeacherService teacherService;
    Teacher teacher;
    TeacherServlet servlet;
    final HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter printWriter = mock(PrintWriter.class);

    @BeforeEach
    void init() {
        teacherService = mock(TeacherService.class);
        servlet = new TeacherServlet(teacherService);

        teacher = new Teacher();
    }

    @Test
    void doGet() throws IOException, ServletException {
        int id = 1;
        String str = "{\"id\":\"" + id + "\"}";
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("GET");
        when(teacherService.findById(id)).thenReturn(teacher);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);
        verify(teacherService).findById(id);
        verify(response).getWriter();
    }

    @Test
    void doPost() throws ServletException, IOException {
        String str = "{\"name\":\"Teacher Name\"}";
        Teacher teacher1 = new Teacher("Teacher Name");
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("POST");
        when(teacherService.addNew(any(Teacher.class))).thenReturn(1L);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);
        verify(teacherService).addNew(teacher1);
        verify(response).getWriter();
    }

    @Test
    void doPut() throws IOException {
        String str = "{\"id\":1,\"name\":\"Teacher Name\"}";
        Teacher teacher1 = new Teacher("Teacher Name");
        teacher1.setId(1);
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("PUT");
        when(teacherService.validate(teacher1)).thenReturn(true);
        when(teacherService.update(teacher1)).thenReturn(true);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPut(request, response);
        verify(teacherService).update(teacher1);
        verify(response).getWriter();
    }

    @Test
    void doDelete() throws IOException, ServletException {
        int id = 1;
        String str = "{\"id\":\"" + id + "\"}";
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("DELETE");
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doDelete(request, response);
        verify(teacherService).delete(id);

    }
}