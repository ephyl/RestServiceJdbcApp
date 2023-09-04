package ephyl.util.validator;

import ephyl.util.ConnectionManager;
import ephyl.dao.CourseDaoJdbc;
import ephyl.model.Course;

public class CourseValidator {
    private  CourseDaoJdbc courseDaoJdbc = new CourseDaoJdbc();

    public CourseValidator(CourseDaoJdbc courseDaoJdbc) {
        this.courseDaoJdbc = courseDaoJdbc;
    }
    public CourseValidator() {
    }

    public  boolean validate(Course course) {
        if(course.getName() == null || course.getName().length()<=1) return false;
        return isNameUnique(course);
    }
    private  boolean isNameUnique(Course course) {
        return courseDaoJdbc.isNameUnique(course);
    }

}
