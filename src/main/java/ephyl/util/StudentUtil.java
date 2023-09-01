package ephyl.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StudentUtil {
    private int student_id;
    private int[] course_id;

    public StudentUtil(int student_id, int[] course_id) {
        this.student_id = student_id;
        this.course_id = course_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public int[] getCourse_id() {
        return course_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setCourse_id(int[] course_id) {
        this.course_id = course_id;
    }

    public StudentUtil() {
    }

    @Override
    public String toString() {
        return "StudentUtil{" +
                "student_id=" + student_id +
                ", course_id=" + Arrays.toString(course_id) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentUtil that = (StudentUtil) o;
        return student_id == that.student_id && Arrays.equals(course_id, that.course_id);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(student_id);
        result = 31 * result + Arrays.hashCode(course_id);
        return result;
    }
}
