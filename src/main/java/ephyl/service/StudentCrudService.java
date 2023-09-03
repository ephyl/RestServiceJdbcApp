package ephyl.service;

import ephyl.dto.StudentDto;
import ephyl.model.Student;

public interface StudentCrudService extends CrudService<Student>{
    @Override
    StudentDto findById(int id);

    @Override
    long addNew(Student o);

    @Override
    void delete(int id);

    @Override
    boolean update(Student o);
}
