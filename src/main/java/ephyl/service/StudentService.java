package ephyl.service;

import ephyl.model.Course;
import ephyl.util.ConnectionManager;
import ephyl.dao.StudentDaoJdbc;
import ephyl.dto.StudentDto;
import ephyl.model.Student;
import ephyl.util.exception.StudentNotFoundException;
import ephyl.util.mapper.CourseMapper;
import ephyl.util.mapper.StudentMapper;
import ephyl.util.validator.StudentDtoValidator;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentService implements StudentCrudService {
    private StudentDaoJdbc studentDaoJdbc = new StudentDaoJdbc(ConnectionManager.getConnection());

    public StudentService() {
    }

    public StudentService(StudentDaoJdbc studentDaoJdbc) {
        this.studentDaoJdbc = studentDaoJdbc;
    }

    @Override
    public StudentDto findById(int id) {

        Optional<Student> teacherOptional = studentDaoJdbc.findById(id);
        StudentDto studentDto = new StudentDto();

        if (teacherOptional.isPresent()) {
            StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);
            Student student = teacherOptional.get();
            studentDto = studentMapper.sourceToDestination(student);
            CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);
            List<Course> courseList = student.getCourseList();
            studentDto.setCoursesDtoList(courseList.stream().map(courseMapper::sourceToDestination).collect(Collectors.toList()));
        } else {
            throw new StudentNotFoundException();
        }
        return studentDto;
//        return teacherOptional.orElseThrow(StudentNotFoundException::new);
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

    public boolean validate(StudentDto studentDto) {
        StudentDtoValidator validator = new StudentDtoValidator();
        return validator.validate(studentDto);
    }
}


