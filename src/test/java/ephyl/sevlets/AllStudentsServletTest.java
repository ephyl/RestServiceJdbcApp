package ephyl.sevlets;

import ephyl.model.Student;
import ephyl.service.AllStudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class AllStudentsServletTest {

    @Test
    void doGet() throws IOException, ServletException {
        AllStudentService allStudentService = mock(AllStudentService.class);

        AllStudentsServlet servlet = new AllStudentsServlet(allStudentService);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        PrintWriter printWriter = mock(PrintWriter.class);

        when(allStudentService.getAllStudents()).thenReturn(Collections.singletonList(new Student()));
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);
        verify(allStudentService).getAllStudents();
        verify(response).getWriter();
    }
    @Test
    void constructor(){
        AllStudentsServlet allStudentsServlet =  new AllStudentsServlet();
        assertNotNull(allStudentsServlet);
    }
}