package ephyl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ephyl.model.Course;
import ephyl.model.Gender;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {
    private int id;
    private String name;
    private int age;
    private Gender gender;

    private List<CourseDto> coursesDtoList;

    public List<CourseDto> getCoursesDtoList() {
        return coursesDtoList;
    }

    public void setCoursesDtoList(List<CourseDto> coursesDtoList) {
        this.coursesDtoList = coursesDtoList;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public StudentDto(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public StudentDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
