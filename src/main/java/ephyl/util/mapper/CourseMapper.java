package ephyl.util.mapper;

import ephyl.dto.CourseDto;
import ephyl.model.Course;
import org.mapstruct.Mapper;

@Mapper
public interface CourseMapper {
    CourseDto sourceToDestination(Course course);
    Course destinationToSource(CourseDto courseDtoDto);
}
