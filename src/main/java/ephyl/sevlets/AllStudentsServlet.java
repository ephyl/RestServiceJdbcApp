package ephyl.sevlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ephyl.dto.StudentDto;
import ephyl.service.AllStudentService;
import ephyl.util.mapper.StudentMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class AllStudentsServlet extends HttpServlet {
    private AllStudentService allStudentService = new AllStudentService();

    public AllStudentsServlet(AllStudentService allStudentService) {
        this.allStudentService = allStudentService;
    }

    public AllStudentsServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentMapper mapper
                = Mappers.getMapper(StudentMapper.class);
        List<StudentDto> studentDtoList = allStudentService.getAllStudents().stream()
                .map(mapper::sourceToDestination)
                .collect(Collectors.toList()); ;

        ObjectMapper objectMapper = new ObjectMapper();
        final String jsonTask = objectMapper.writeValueAsString(studentDtoList);
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(jsonTask);
    }
}
