package ephyl.sevlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ephyl.dto.TeacherDto;
import ephyl.service.AllTeachersService;
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

public class AllTeachersServlet extends HttpServlet {
    private AllTeachersService allTeachersService = new AllTeachersService();

    public AllTeachersServlet(AllTeachersService allTeachersService) {
        this.allTeachersService = allTeachersService;
    }

    public AllTeachersServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TeacherMapper mapper
                = Mappers.getMapper(TeacherMapper.class);
        List<TeacherDto> teacherDtoList = allTeachersService.getAllTeachers().stream()
                .map(mapper::sourceToDestination)
                .collect(Collectors.toList());
        ;

        ObjectMapper objectMapper = new ObjectMapper();
        final String jsonTeachers = objectMapper.writeValueAsString(teacherDtoList);
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(jsonTeachers);
    }
}