package ephyl.sevlets;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import ephyl.model.Teacher;
import ephyl.service.TeacherService;
import ephyl.util.ConverterFromJSON;
import ephyl.util.exception.NotUniqueNameException;
import ephyl.util.exception.TeacherNotFoundException;
import ephyl.util.validator.TeacherValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


import static ephyl.util.ConverterFromJSON.convertFromJson;

public class TeacherServlet extends HttpServlet {
    private TeacherService teacherService = new TeacherService();

    public TeacherServlet() {
    }

    public TeacherServlet(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Teacher teacherToBeFindById = new Teacher();
        try {
            teacherToBeFindById = convertFromJson(req, teacherToBeFindById).orElseThrow(TeacherNotFoundException::new);
            int idParam = teacherToBeFindById.getId();
            Teacher teacher = teacherService.findById(idParam);
            final String jsonTask = new ObjectMapper().writeValueAsString(teacher);
            resp.setContentType("application/json; charset=UTF-8");
            resp.getWriter().write(jsonTask);
        } catch (TeacherNotFoundException tNFE) {
            resp.getWriter().write(tNFE.toString());
        } catch (JsonParseException | InvalidFormatException j) {
            resp.getWriter().write("Wrong format of id. Value cant be parsed" + j.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Teacher teacherToBeAdded = ConverterFromJSON.convertFromJson(req, new Teacher()).orElseThrow(IllegalArgumentException::new);
            long id = teacherService.addNew(teacherToBeAdded);
            PrintWriter out = resp.getWriter();
            resp.setStatus(201);
            out.write("Teacher *" + teacherToBeAdded.getName() + "*  was added, with id = " + id);
        } catch (IllegalArgumentException e) {
            resp.getWriter().write(e.toString());
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Teacher tacherToBeUpdated = new Teacher();
        boolean isUpdated = false;
        try {
            tacherToBeUpdated = convertFromJson(req, tacherToBeUpdated).orElseThrow(TeacherNotFoundException::new);
            if (TeacherValidator.validate(tacherToBeUpdated)) {
                isUpdated = teacherService.update(tacherToBeUpdated);
                final String jsonTask = new ObjectMapper().writeValueAsString(isUpdated);
                resp.setContentType("application/json; charset=UTF-8");
                resp.getWriter().write(jsonTask);
            } else {
                throw new NotUniqueNameException();
            }
        } catch (TeacherNotFoundException tNFE) {
            resp.getWriter().write(tNFE.toString());
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
            Teacher teacherToBeDeleted = ConverterFromJSON.convertFromJson(req, new Teacher()).orElseThrow(TeacherNotFoundException::new);
            teacherService.delete(teacherToBeDeleted.getId());
            PrintWriter writer = resp.getWriter();
            writer.write("Teacher was deleted");
        } catch (TeacherNotFoundException e) {
            resp.getWriter().write(e.toString());
        }
    }
}