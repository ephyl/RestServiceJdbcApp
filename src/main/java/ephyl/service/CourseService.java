package ephyl.service;

import ephyl.util.ConnectionManager;
import ephyl.dao.CourseDaoJdbc;
import ephyl.model.Course;
import ephyl.util.exception.CourseNotFoundException;

import java.util.Optional;

public class CourseService implements CrudService<Course> {
    private CourseDaoJdbc courseDaoJdbc = new CourseDaoJdbc(ConnectionManager.getConnection());

    public CourseService(CourseDaoJdbc courseDaoJdbc) {
        this.courseDaoJdbc = courseDaoJdbc;    }

    public CourseService() {    }

    public Course findById(int id) {
        Optional<Course> courseOptional = courseDaoJdbc.findById(id);
        return courseOptional.orElseThrow(CourseNotFoundException::new);
    }

    public void delete(int id) {
        courseDaoJdbc.delete(id);
    }

    public boolean update(Course course) {
        return courseDaoJdbc.update(course);
    }

    public long addNew(Course course) {
        return courseDaoJdbc.addNew(course);
    }

}
