package ephyl.dao;


import ephyl.util.ConnectionManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CourseManagerDaoJdbc implements CourseManagerDao {
    private Map<String, String> connectionParams;

    public CourseManagerDaoJdbc(Map<String, String> connectionParams) {
        this.connectionParams = connectionParams;
    }

    public CourseManagerDaoJdbc() {
    }

    @Override
    public int assignCourses(int student_id, int[] courses) {
        int affectedRows = 0;
        try (Connection connectionDB = ConnectionManager.getConnection(connectionParams)) {
            for (int course_id : courses
            )
                if (!isAlreadyAssigned(student_id, course_id)) {
                    try (PreparedStatement preparedStatement = connectionDB.prepareStatement("INSERT INTO course_student(course_id, student_id) VALUES (?, ?)")) {
                        preparedStatement.setInt(1, course_id);
                        preparedStatement.setInt(2, student_id);
                        affectedRows += preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }


    private boolean isAlreadyAssigned(int student_id, int course_id) throws SQLException {
        try (Connection connectionDB = ConnectionManager.getConnection(connectionParams)) {
            try (PreparedStatement preparedStatement = connectionDB.prepareStatement("SELECT 1 FROM course_student WHERE course_id=? AND student_id=? ")) {
                preparedStatement.setInt(1, course_id);
                preparedStatement.setInt(2, student_id);
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int unAssignCourses(int student_id, int[] courses) {
        int affectedRows = 0;
        try (Connection connectionDB = ConnectionManager.getConnection(connectionParams)) {
            for (int course_id : courses
            )
                try (PreparedStatement preparedStatement = connectionDB.prepareStatement("DELETE FROM course_student WHERE (course_id = ? AND student_id = ?)")) {
                    preparedStatement.setInt(1, course_id);
                    preparedStatement.setInt(2, student_id);
                    affectedRows += preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
}