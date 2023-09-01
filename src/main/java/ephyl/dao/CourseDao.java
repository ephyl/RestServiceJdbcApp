package ephyl.dao;

import ephyl.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao extends Dao<Course> {
    @Override
    List<Course> getAll();

    @Override
    Optional<Course> findById(int id);

    @Override
    long addNew(Course object);

    @Override
    void delete(int id);

}
