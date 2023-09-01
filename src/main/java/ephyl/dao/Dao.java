package ephyl.dao;

import ephyl.model.Student;

import java.util.List;
import java.util.Optional;

public interface Dao <T> {
    List<T> getAll();
    Optional<T> findById(int id);
    long addNew(T object);
    void delete(int id);
    boolean update(T object);

}
