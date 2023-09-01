package ephyl.dao;

import ephyl.model.Course;
import ephyl.model.Student;
import ephyl.util.StudentUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseManagerDaoJdbc implements CourseManagerDao {
    private final Connection connection;

    public CourseManagerDaoJdbc(Connection connection) {
        this.connection = connection;
    }

        @Override
    public int assignCourses(int student_id, int[] courses) {
        int affectedRows = 0;
        for (int course_id : courses
        )
            if (!isAlreadyAssigned(student_id, course_id)) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO course_student(course_id, student_id) VALUES (?, ?)")) {
                    preparedStatement.setInt(1, course_id);
                    preparedStatement.setInt(2, student_id);
                    affectedRows += preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        return affectedRows;
    }

    private boolean isAlreadyAssigned(int student_id, int course_id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1 FROM course_student WHERE course_id=? AND student_id=? ")) {
            preparedStatement.setInt(1, course_id);
            preparedStatement.setInt(2, student_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int unAssignCourses(int student_id, int[] courses) {
        int affectedRows = 0;
        for (int course_id : courses
        )
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM course_student WHERE (course_id = ? AND student_id = ?)")) {
                preparedStatement.setInt(1, course_id);
                preparedStatement.setInt(2, student_id);
                affectedRows += preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return affectedRows;
    }
}
