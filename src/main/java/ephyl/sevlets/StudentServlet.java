package ephyl.sevlets;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import ephyl.dto.StudentDto;
import ephyl.model.Student;
import ephyl.service.StudentService;
import ephyl.util.exception.NotUniqueNameException;
import ephyl.util.mapper.StudentMapper;
import ephyl.util.exception.StudentNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.io.PrintWriter;

import static ephyl.util.ConverterFromJSON.convertFromJson;

public class StudentServlet extends HttpServlet {
    private StudentService studentService = new StudentService();
    ;

    public StudentServlet() {
    }

    public StudentServlet(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student studentToBeFindById = new Student();
        try {
            studentToBeFindById = convertFromJson(req, studentToBeFindById).orElseThrow(StudentNotFoundException::new);
            int idParam = studentToBeFindById.getId();
            StudentDto studentDto = studentService.findById(idParam);
            final String jsonTask = new ObjectMapper().writeValueAsString(studentDto);
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(jsonTask);
        } catch (StudentNotFoundException sNFE) {
            resp.getWriter().write(sNFE.toString());
        } catch (JsonParseException | InvalidFormatException j) {
            resp.getWriter().write("Wrong format Value cant be parsed" + j.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentDto studentToBeAdded = new StudentDto();

        try {
            studentToBeAdded = convertFromJson(req, studentToBeAdded).get();
            StudentMapper mapper
                    = Mappers.getMapper(StudentMapper.class);
            if (studentService.validate(studentToBeAdded)) {
                long id = studentService.addNew(mapper.destinationToSource(studentToBeAdded));
                if (id > 0) {
                    PrintWriter out = resp.getWriter();
                    out.write("Student *" + studentToBeAdded.getName() + "*  was added, with id = " + id);
                }
            } else {
                resp.getWriter().write("Student not Valid. Please send correct data");
            }
        } catch (IllegalArgumentException | NotUniqueNameException e) {
            resp.getWriter().write(e.toString());
        } catch (JsonParseException | InvalidFormatException j) {
            resp.getWriter().write("Wrong format Value cant be parsed" + j.getMessage());
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StudentDto studentToBeUpdated = new StudentDto();
        try {
            studentToBeUpdated = convertFromJson(req, studentToBeUpdated).orElseThrow(IllegalArgumentException::new);
            StudentMapper mapper
                    = Mappers.getMapper(StudentMapper.class);
            if (studentService.validate(studentToBeUpdated)) {
                if (studentService.update(mapper.destinationToSource(studentToBeUpdated))) {
                    resp.getWriter().write(studentToBeUpdated.getName() + "  updated");
                }
            } else {
                resp.getWriter().write("Student not Valid. Please send correct data");
            }
        } catch (IllegalArgumentException e) {
            resp.getWriter().write(e.toString());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Student studentToBeDeleted = convertFromJson(req, new Student()).orElseThrow(StudentNotFoundException::new);
            studentService.delete(studentToBeDeleted.getId());
            PrintWriter writer = resp.getWriter();
            writer.write("Student was deleted");
        } catch (StudentNotFoundException e) {
            resp.getWriter().write(e.toString());
        }
    }


}
