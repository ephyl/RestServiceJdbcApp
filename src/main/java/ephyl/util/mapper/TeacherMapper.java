package ephyl.util.mapper;

import ephyl.dto.TeacherDto;
import ephyl.model.Teacher;
import org.mapstruct.Mapper;

@Mapper
public interface TeacherMapper {
    TeacherDto sourceToDestination(Teacher teacher);
    Teacher destinationToSource(TeacherDto teacherDto);
}
