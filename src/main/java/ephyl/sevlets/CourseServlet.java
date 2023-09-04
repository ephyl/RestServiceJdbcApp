package ephyl.sevlets;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import ephyl.dto.CourseDto;
import ephyl.model.Course;
import ephyl.model.Teacher;
import ephyl.service.CourseService;
import ephyl.util.ConverterFromJSON;
import ephyl.util.exception.TeacherNotFoundException;
import ephyl.util.mapper.CourseMapper;
import ephyl.util.validator.CourseValidator;
import ephyl.util.exception.CourseNotFoundException;
import ephyl.util.exception.NotUniqueNameException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.io.PrintWriter;

import static ephyl.util.ConverterFromJSON.convertFromJson;

public class CourseServlet extends HttpServlet {
    private CourseService courseService = new CourseService();

    public CourseServlet(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Course courseToBeFindById = new Course();
        try {
            courseToBeFindById = convertFromJson(req, courseToBeFindById).orElseThrow(CourseNotFoundException::new);
            int idParam = courseToBeFindById.getId();

            final String jsonTask = new ObjectMapper().writeValueAsString(courseService.findById(idParam));

            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(jsonTask);
        } catch (CourseNotFoundException cNFE) {
            resp.getWriter().write(cNFE.toString());
        } catch (JsonParseException | InvalidFormatException j) {
            resp.getWriter().write("Wrong format of id. Value cant be parsed" + j.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Course courseToBeAdded = ConverterFromJSON.convertFromJson(req, new Course()).orElseThrow(IllegalArgumentException::new);
            long id = courseService.addNew(courseToBeAdded);
            PrintWriter out = resp.getWriter();
            out.write("Course *" + courseToBeAdded.getName() + "*  was added, with id = " + id);
        } catch (IllegalArgumentException e) {
            resp.getWriter().write(e.toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Course courseToBeUpdated = new Course();
        boolean isUpdated = false;
        try {
            courseToBeUpdated = convertFromJson(req, courseToBeUpdated).orElseThrow(CourseNotFoundException::new);
            if (courseService.validate(courseToBeUpdated)) {
                isUpdated = courseService.update(courseToBeUpdated);
                final String jsonTask = new ObjectMapper().writeValueAsString(isUpdated);
                resp.setContentType("application/json; charset=UTF-8");
                resp.getWriter().write(jsonTask);
            } else {
                throw new NotUniqueNameException();
            }
        } catch (CourseNotFoundException cNFE) {
            resp.getWriter().write(cNFE.toString());
        } catch (JsonParseException | InvalidFormatException j) {
            resp.getWriter().write("Wrong format of id. Value cant be parsed" + j.getMessage());
        } catch (NotUniqueNameException notUniqueNameException) {
            resp.getWriter().write(notUniqueNameException.toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Course courseToBeDeleted = ConverterFromJSON.convertFromJson(req, new Course()).orElseThrow(CourseNotFoundException::new);
            courseService.delete(courseToBeDeleted.getId());
            PrintWriter writer = resp.getWriter();
            writer.write("Course was deleted");
        } catch (CourseNotFoundException e) {
            resp.getWriter().write(e.toString());
        }
    }
}
