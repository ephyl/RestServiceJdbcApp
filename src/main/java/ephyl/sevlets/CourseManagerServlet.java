package ephyl.sevlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ephyl.dao.StudentDaoJdbc;
import ephyl.service.CourseManagerService;
import ephyl.service.CourseService;
import ephyl.util.StudentUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class CourseManagerServlet extends HttpServlet {
    private CourseManagerService courseManagerService = new CourseManagerService();

    public CourseManagerServlet(CourseManagerService courseService) {
        this.courseManagerService = courseService;
    }

    public CourseManagerServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentUtil studentUtilFromReq = getStudentUtil(req);
        int coursesAdded = courseManagerService.assignCourses(studentUtilFromReq);

        System.out.println(studentUtilFromReq);
        resp.getWriter().write(coursesAdded + " course(s) added!");

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentUtil studentDataToWithdrawFromCourses = getStudentUtil(req);
        int coursesUnAssignedFromStudent = 0;
        coursesUnAssignedFromStudent = courseManagerService.unAssignCourses(studentDataToWithdrawFromCourses);

        resp.getWriter().write("student left " + coursesUnAssignedFromStudent + " course(s)");


    }

    private static StudentUtil getStudentUtil(HttpServletRequest req) throws IOException {
        StringBuilder data = new StringBuilder();
        BufferedReader bf = req.getReader();
        while (bf.ready()) {
            data.append(bf.readLine());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(data.toString(), StudentUtil.class);
    }

}
