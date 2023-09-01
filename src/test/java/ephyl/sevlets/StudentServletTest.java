package ephyl.sevlets;

import ephyl.dto.StudentDto;
import ephyl.model.Course;
import ephyl.model.Gender;
import ephyl.model.Student;
import ephyl.service.CourseService;
import ephyl.service.StudentService;
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

class StudentServletTest {
    StudentService studentService;
    Student student;
    StudentServlet servlet;
    final HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter printWriter = mock(PrintWriter.class);


    @BeforeEach
    void init() {
        studentService = mock(StudentService.class);
        servlet = new StudentServlet(studentService);

        student = new Student();

    }

    @Test
    void doGet() throws IOException, ServletException {

        int id = 1;
        String str = "{\"id\":\"" + id + "\"}";
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("GET");
        when(studentService.findById(id)).thenReturn(student);
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);
        verify(studentService).findById(id);
        verify(response).getWriter();

    }

    @Test
    void doPost() throws IOException, ServletException {
        String str = "{\"name\":\"Student Name\", \"age\":16, \"gender\":\"MALE\"}";
        Student student1 = new Student("Student Name", 16, Gender.MALE);
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("POST");
        when(studentService.validate(any(StudentDto.class))).thenReturn(true);
        when(studentService.addNew(any(Student.class))).thenReturn(1L);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);
        verify(studentService).addNew(student1);
        verify(response).getWriter();
    }
    @Test
    void doPost_different_student() throws IOException, ServletException {
        String str = "{\"name\":\"Student Name\", \"age\":16, \"gender\":\"MALE\"}";
        Student student1 = new Student("Student Name", 90, Gender.MALE);
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("POST");
        when(studentService.validate(any(StudentDto.class))).thenReturn(true);
        when(studentService.addNew(any(Student.class))).thenReturn(1L);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPost(request, response);
        verify(studentService, never()).addNew(student1);
        verify(response).getWriter();
    }

    @Test
    void doPut() throws IOException, ServletException {
        String str = "{\"id\":1,\"name\":\"Student Name\", \"age\":16, \"gender\":\"MALE\"}";
        Student student1 = new Student("Student Name", 16, Gender.MALE);
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("PUT");
        when(studentService.validate(any(StudentDto.class))).thenReturn(true);
        when(studentService.update(student1)).thenReturn(true);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPut(request, response);
        verify(studentService).update(student1);
        verify(response).getWriter();
    }
    @Test
    void doPut_different_Gender() throws IOException, ServletException {
        String str = "{\"id\":1,\"name\":\"Student Name\", \"age\":16, \"gender\":\"MALE\"}";
        Student student1 = new Student("Student Name", 16, Gender.FEMALE);
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(str.toCharArray()));
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getMethod()).thenReturn("PUT");
        when(studentService.validate(any(StudentDto.class))).thenReturn(false);
        when(studentService.update(student1)).thenReturn(true);

        when(response.getWriter()).thenReturn(printWriter);

        servlet.doPut(request, response);
        verify(studentService, never()).update(student1);
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
        verify(studentService).delete(id);
    }
}