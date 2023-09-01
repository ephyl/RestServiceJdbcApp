package ephyl.sevlets;

import ephyl.service.CourseManagerService;
import ephyl.util.StudentUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CourseManagerServletTest {

    @Test
    void doPost() throws ServletException, IOException {

        String string =  "{\"student_id\":9,\"course_id\":[3,4]}";
        int[] a = {3, 4};
        StudentUtil studentUtil = new StudentUtil(9, a);
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(string.toCharArray()));

        CourseManagerService courseManagerService = mock(CourseManagerService.class);
        PrintWriter printWriter = mock(PrintWriter.class);
        CourseManagerServlet courseManagerServlet = new CourseManagerServlet(courseManagerService);


        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        when(courseManagerService.assignCourses(studentUtil)).thenReturn(1);
        when(request.getReader()).thenReturn(bufferedReader);
        when(response.getWriter()).thenReturn(printWriter);

        courseManagerServlet.doPost(request, response);

        verify(courseManagerService).assignCourses(any(StudentUtil.class));
        verify(courseManagerService).assignCourses(studentUtil);

    }

    @Test
    void doDelete() throws IOException, ServletException {
        String string =  "{\"student_id\":9,\"course_id\":[3,4]}";
        int[] a = {3, 4};
        StudentUtil studentUtil = new StudentUtil(9, a);
        BufferedReader bufferedReader = new BufferedReader(new CharArrayReader(string.toCharArray()));

        CourseManagerService courseManagerService = mock(CourseManagerService.class);
        PrintWriter printWriter = mock(PrintWriter.class);
        CourseManagerServlet courseManagerServlet = new CourseManagerServlet(courseManagerService);


        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        when(courseManagerService.assignCourses(studentUtil)).thenReturn(1);
        when(request.getReader()).thenReturn(bufferedReader);
        when(response.getWriter()).thenReturn(printWriter);

        courseManagerServlet.doDelete(request, response);

        verify(courseManagerService).unAssignCourses(any(StudentUtil.class));
        verify(courseManagerService).unAssignCourses(studentUtil);
    }
    @Test
    void constructor(){
        CourseManagerServlet courseManagerServlet =  new CourseManagerServlet();
        assertNotNull(courseManagerServlet);
    }
}