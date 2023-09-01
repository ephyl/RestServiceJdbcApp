package ephyl.sevlets;

import ephyl.model.Course;
import ephyl.service.AllCoursesService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AllCoursesServletTest {

    @Test
    void doGet() throws IOException {
        AllCoursesService allCoursesService = mock(AllCoursesService.class);
        List<Course> list = Collections.singletonList(new Course());

        AllCoursesServlet servlet = new AllCoursesServlet(allCoursesService);

        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        PrintWriter printWriter = mock(PrintWriter.class);

        when(allCoursesService.getAll()).thenReturn(list);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        servlet.doGet(httpServletRequest, httpServletResponse);
        verify(allCoursesService).getAll();
        verify(httpServletResponse).getWriter();
    }
    @Test
    void constructor(){
        AllCoursesServlet allCoursesServlet =  new AllCoursesServlet();
        assertNotNull(allCoursesServlet);
    }
}