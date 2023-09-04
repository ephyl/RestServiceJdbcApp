package ephyl.dao;

import ephyl.dto.StudentDto;
import ephyl.model.Course;
import ephyl.model.Gender;
import ephyl.model.Student;
import ephyl.model.Teacher;
import ephyl.util.ConnectionManager;
import ephyl.util.mapper.CourseMapper;
import ephyl.util.mapper.StudentMapper;
import org.mapstruct.factory.Mappers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDaoJdbc implements CourseDao {
    private Connection connection = ConnectionManager.getConnection();

    public CourseDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    public CourseDaoJdbc() {
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM course")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("name"));
                course.setTeacher_id(resultSet.getInt("teacher_id"));
                courses.add(course);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    @Override
    public long addNew(Course course) {
        long newCourseId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(("INSERT INTO course(name) VALUES (?)"), Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, course.getName());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        long id = keys.getLong(1);
                        newCourseId = id;
                        System.out.println(id);
                    } else {
                        throw new SQLException("No ID obtained");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCourseId;
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM course WHERE id = ?");) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Course courseToBeUpdated) {
        Course course = new Course();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM course WHERE id = ?")) {
            preparedStatement.setInt(1, courseToBeUpdated.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("name"));
                course.setTeacher_id(resultSet.getInt("teacher_id"));
            }
            if (courseToBeUpdated.getName() != null) {
                course.setName(courseToBeUpdated.getName());
            }
            if (courseToBeUpdated.getTeacher_id() > 0) {
                course.setTeacher_id(courseToBeUpdated.getTeacher_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int affectedRows = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(("UPDATE course SET name = ?, teacher_id =? WHERE id = ?"), Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setInt(2, course.getTeacher_id());
            preparedStatement.setInt(3, course.getId());
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows > 0;
    }

    @Override
    public Optional<Course> findById(int id) {
        Course course = null;
        List<Student> studentsList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM course WHERE id = ?");) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                course = new Course();
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("name"));
                course.setTeacher_id(resultSet.getInt("teacher_id"));
            }
            if (course != null) {
                try (PreparedStatement preparedStatementGetCourses = connection.prepareStatement("SELECT S.id, S.name, S.age, S.gender FROM Course JOIN Course_Student CS on Course.id = CS.course_id JOIN Student S on S.id = CS.student_id where course.id=?")) {
                    preparedStatementGetCourses.setInt(1, id);
                    getStudents(course, studentsList, preparedStatementGetCourses);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(course);
    }

    private void getStudents(Course course, List<Student> studentList, PreparedStatement preparedStatementGetCourses) throws SQLException {
        ResultSet resultSetOfCourses = preparedStatementGetCourses.executeQuery();
        StudentMapper mapper = Mappers.getMapper(StudentMapper.class);
        while (resultSetOfCourses.next()) {
            Student student = new Student();
            student.setId(resultSetOfCourses.getInt("id"));
            student.setName(resultSetOfCourses.getString("name"));
            student.setAge(resultSetOfCourses.getInt("age"));
            student.setGender(Gender.valueOf(resultSetOfCourses.getString("gender")));
            studentList.add(student);
        }
        course.setStudents(studentList);
    }
    public boolean isNameUnique(Course course){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM course WHERE name=?")) {
            preparedStatement.setString(1, course.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id") == course.getId();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;

    }

}
