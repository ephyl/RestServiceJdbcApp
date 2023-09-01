package ephyl.service;

import ephyl.util.ConnectionManager;
import ephyl.dao.CourseDaoJdbc;
import ephyl.model.Course;

import java.util.List;

public class AllCoursesService {
    private  CourseDaoJdbc courseDaoJdbc = new CourseDaoJdbc(ConnectionManager.getConnection());
    public void setCourseDaoJdbc(CourseDaoJdbc courseDaoJdbc) {
        this.courseDaoJdbc = courseDaoJdbc;
    }

    public List<Course> getAll(){
        return courseDaoJdbc.getAll();
    }
}
