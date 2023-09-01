package ephyl.util.mapper;

import ephyl.dto.StudentDto;
import ephyl.model.Student;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {
StudentDto sourceToDestination(Student student);
Student destinationToSource(StudentDto studentDto);
}
