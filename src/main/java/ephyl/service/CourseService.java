package ephyl.service;

import ephyl.dto.CourseDto;
import ephyl.model.Student;
import ephyl.util.ConnectionManager;
import ephyl.dao.CourseDaoJdbc;
import ephyl.model.Course;
import ephyl.util.exception.CourseNotFoundException;
import ephyl.util.mapper.CourseMapper;
import ephyl.util.mapper.StudentMapper;
import ephyl.util.validator.CourseValidator;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseService implements CourseCrudService {
    private CourseDaoJdbc courseDaoJdbc = new CourseDaoJdbc();

    public CourseService(CourseDaoJdbc courseDaoJdbc) {
        this.courseDaoJdbc = courseDaoJdbc;
    }

    public CourseService() {
    }

    public CourseDto findById(int id) {
        CourseDto courseDto = new CourseDto();
        Optional<Course> courseOptional = courseDaoJdbc.findById(id);
        if (courseOptional.isPresent()) {

            Course course = courseOptional.get();

            CourseMapper mapper = Mappers.getMapper(CourseMapper.class);
            courseDto = mapper.sourceToDestination(courseOptional.get());

            List<Student> studentList = course.getStudents();
            StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);
            courseDto.setStudentDtoList(studentList.stream().map(studentMapper::sourceToDestination).collect(Collectors.toList()));
        } else {
            throw new CourseNotFoundException();
        }

        return courseDto;
    }

    public void delete(int id) {
        courseDaoJdbc.delete(id);
    }

    public boolean update(Course course) {
        return courseDaoJdbc.update(course);
    }

    public long addNew(Course course) {
        return courseDaoJdbc.addNew(course);
    }
    public boolean validate(Course course){
    CourseValidator courseValidator = new CourseValidator();
        return courseValidator.validate(course);
    }

}
