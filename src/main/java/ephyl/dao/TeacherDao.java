package ephyl.dao;

import ephyl.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherDao extends Dao<Teacher>{
    @Override
    List<Teacher> getAll() ;
    @Override
    Optional<Teacher> findById(int id);
    @Override
    long addNew(Teacher object);

    @Override
    void delete(int id);

    @Override
    boolean update(Teacher object);
}
