package ephyl.service;

import ephyl.model.Course;
import ephyl.util.exception.CourseNotFoundException;

import java.util.Optional;

public interface CrudService <T>{
    public T findById(int id);
    public long addNew(T o);
    public void delete(int id);
    public boolean update(T o);
}
