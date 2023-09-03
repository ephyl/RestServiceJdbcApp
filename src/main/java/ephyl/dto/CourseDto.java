package ephyl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {
    private String name;
    private List<StudentDto> studentDtoList;

    public CourseDto(String name) {
        this.name = name;
    }

    public CourseDto() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StudentDto> getStudentDtoList() {
        return studentDtoList;
    }

    public void setStudentDtoList(List<StudentDto> studentDtoList) {
        this.studentDtoList = studentDtoList;
    }
}
