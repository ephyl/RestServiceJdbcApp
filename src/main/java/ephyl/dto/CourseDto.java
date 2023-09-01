package ephyl.dto;

public class CourseDto {
    private String name;

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


}
