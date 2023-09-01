package ephyl.service;

import ephyl.util.ConnectionManager;
import ephyl.dao.CourseManagerDao;
import ephyl.dao.CourseManagerDaoJdbc;
import ephyl.util.StudentUtil;

public class CourseManagerService {
    private CourseManagerDao courseManagerDaoJdbc = new CourseManagerDaoJdbc(ConnectionManager.getConnection());

    public CourseManagerService(CourseManagerDao courseManagerDaoJdbc) {
        this.courseManagerDaoJdbc = courseManagerDaoJdbc;
    }

    public CourseManagerService() {
    }

    public int assignCourses(StudentUtil studentUtil){
        int student_id = studentUtil.getStudent_id();
        int [] courses_id = studentUtil.getCourse_id();
       return courseManagerDaoJdbc.assignCourses(student_id, courses_id);
    }
    public int unAssignCourses(StudentUtil studentUtil){
        int student_id = studentUtil.getStudent_id();
        int [] courses_id = studentUtil.getCourse_id();
        return courseManagerDaoJdbc.unAssignCourses(student_id, courses_id);
    };


}
