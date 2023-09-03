package ephyl.service;

import ephyl.dto.CourseDto;
import ephyl.model.Course;

public interface CourseCrudService extends  CrudService<Course> {
    @Override
    CourseDto findById(int id);

    @Override
    long addNew(Course o);

    @Override
    void delete(int id);

    @Override
    boolean update(Course o);
}
