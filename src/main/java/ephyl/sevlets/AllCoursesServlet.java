package ephyl.sevlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ephyl.dto.CourseDto;
import ephyl.dto.TeacherDto;
import ephyl.model.Course;
import ephyl.service.AllCoursesService;
import ephyl.service.AllTeachersService;
import ephyl.util.mapper.CourseMapper;
import ephyl.util.mapper.TeacherMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class AllCoursesServlet extends HttpServlet {

    private AllCoursesService allCoursesService = new AllCoursesService();
    public AllCoursesServlet(AllCoursesService allCoursesService) {
        this.allCoursesService = allCoursesService;
    }

    public AllCoursesServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CourseMapper mapper
                = Mappers.getMapper(CourseMapper.class);
        List<CourseDto> coursesDtoList = allCoursesService.getAll().stream()
                .map(mapper::sourceToDestination)
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        final String jsonTeachers = objectMapper.writeValueAsString(coursesDtoList);
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(jsonTeachers);
    }
}
