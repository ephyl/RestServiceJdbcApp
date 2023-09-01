package ephyl.util.exception;

public class NotUniqueNameException  extends RuntimeException{
    @Override
    public String toString() {
        return "NotUniqueNameException{" +
                "INFO='" + INFO + '\'' +
                '}';
    }

    private final String INFO = "Not unique name";


}
