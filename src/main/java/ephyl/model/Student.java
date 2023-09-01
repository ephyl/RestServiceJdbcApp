package ephyl.model;

import ephyl.dto.CourseDto;

import java.util.List;
import java.util.Objects;

public class Student {
    private int id;
    private String name;
    private int age;
    private Gender gender;

    private List<CourseDto> coursesDto;

    public Student() {
    }

    public Student(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public List<CourseDto> getCoursesDto() {
        return coursesDto;
    }

    public void setCoursesDto(List<CourseDto> coursesDto) {
        this.coursesDto = coursesDto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(name, student.name) && gender == student.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, gender);
    }
}
