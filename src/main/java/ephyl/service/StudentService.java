package ephyl.service;

import ephyl.util.ConnectionManager;
import ephyl.dao.StudentDaoJdbc;
import ephyl.dto.StudentDto;
import ephyl.model.Student;
import ephyl.util.exception.StudentNotFoundException;
import ephyl.util.validator.StudentDtoValidator;

import java.util.Optional;

public class StudentService implements CrudService<Student> {
    private  StudentDaoJdbc studentDaoJdbc = new StudentDaoJdbc(ConnectionManager.getConnection());

    public StudentService() {
    }

    public StudentService(StudentDaoJdbc studentDaoJdbc) {
        this.studentDaoJdbc = studentDaoJdbc;
    }

    @Override
    public Student findById(int id) {
        Optional<Student> teacherOptional = studentDaoJdbc.findById(id);
        return teacherOptional.orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public long addNew(Student o) {
        return studentDaoJdbc.addNew(o);
    }

    @Override
    public void delete(int id) {
        studentDaoJdbc.delete(id);
    }

    @Override
    public boolean update(Student o) {
        return studentDaoJdbc.update(o);
    }

    public boolean validate(StudentDto studentDto){
        StudentDtoValidator validator =  new StudentDtoValidator();
        return validator.validate(studentDto);
    }
}


