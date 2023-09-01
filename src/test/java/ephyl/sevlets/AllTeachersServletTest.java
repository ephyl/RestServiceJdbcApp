package ephyl.sevlets;

import ephyl.model.Student;
import ephyl.model.Teacher;
import ephyl.service.AllStudentService;
import ephyl.service.AllTeachersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class AllTeachersServletTest {

    @Test
    void doGet() throws IOException {
        AllTeachersService allTeachersService = mock(AllTeachersService.class);

        AllTeachersServlet servlet = new AllTeachersServlet(allTeachersService);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        PrintWriter printWriter = mock(PrintWriter.class);

        when(allTeachersService.getAllTeachers()).thenReturn(Collections.singletonList(new Teacher()));
        when(response.getWriter()).thenReturn(printWriter);

        servlet.doGet(request, response);
        verify(allTeachersService).getAllTeachers();
        verify(response).getWriter();
    }

    @Test
    void constructor(){
        AllTeachersServlet allTeachersServlet =  new AllTeachersServlet();
        assertNotNull(allTeachersServlet);
    }
}