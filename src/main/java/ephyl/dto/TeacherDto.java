package ephyl.dto;

public class TeacherDto {
    private String name;

    public TeacherDto() {
    }

    public TeacherDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
