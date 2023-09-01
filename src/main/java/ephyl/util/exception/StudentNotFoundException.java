package ephyl.util.exception;

public class StudentNotFoundException extends RuntimeException {

    private final String info = "Student not found";
    public StudentNotFoundException() {

    }

    @Override
    public String toString() {
        return "StudentNotFoundException{" +
                "info='" + info + '\'' +
                '}';
    }
}
