package ephyl.dao;

import ephyl.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao extends Dao<Student>{
    @Override
    List<Student> getAll();

    @Override
    Optional<Student> findById(int id);

    @Override
    long addNew(Student object);

    @Override
    void delete(int id);

    @Override
    boolean update(Student object);
}
