package ephyl.dao;

import ephyl.model.Course;
import ephyl.util.StudentUtil;

import java.util.List;

public interface CourseManagerDao {
    int assignCourses(int student_id, int[] courses_id);

    int unAssignCourses(int student_id, int[] courses_id);
}
