package ephyl.util.exception;

public class CourseNotFoundException extends RuntimeException {
    private final String info = "Course not found";
    public CourseNotFoundException() {}
    @Override
    public String toString() {
        return "CourseNotFoundException{" +
                "info='" + info + '\'' +
                '}';
    }
}
