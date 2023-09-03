package ephyl.dao;

import ephyl.model.Course;
import ephyl.model.Gender;
import ephyl.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDaoJdbc implements StudentDao {
    private final Connection connection;

    public StudentDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    private void getCourses(Student student, List<Course> studentCoursesList, PreparedStatement preparedStatementGetCourses) throws SQLException {
        ResultSet resultSetOfCourses = preparedStatementGetCourses.executeQuery();

        while (resultSetOfCourses.next()) {
            Course course = new Course();
            course.setId(resultSetOfCourses.getInt("id"));
            course.setName(resultSetOfCourses.getString("name"));
            studentCoursesList.add(course);
        }
        student.setCourseList(studentCoursesList);
    }

    @Override
    public long addNew(Student studentToAdd) {
        long newStudentId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(("INSERT INTO Student(name, age, gender) VALUES (?, ?, ?)"), Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, studentToAdd.getName());
            preparedStatement.setInt(2, studentToAdd.getAge());
            preparedStatement.setString(3, String.valueOf(studentToAdd.getGender()));
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Занято строк" + affectedRows);
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        long id = keys.getLong(1);
                        newStudentId = id;
                        System.out.println(id);
                    } else {
                        throw new SQLException("No ID obtained");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newStudentId;
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Student WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Student> findById(int id) {
        Student student = null;
        List<Course> studentCoursesList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Student WHERE id = ?");) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setGender(Gender.valueOf(resultSet.getString("gender")));
            }
            if (student != null) {
                try (PreparedStatement preparedStatementGetCourses = connection.prepareStatement("SELECT Course.id, Course.name FROM Course JOIN Course_Student CS on Course.id = CS.course_id JOIN Student S on S.id = CS.student_id where S.id=?")) {
                    preparedStatementGetCourses.setInt(1, id);
                    getCourses(student, studentCoursesList, preparedStatementGetCourses);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(student);
    }



    @Override
    public List<Student> getAll() throws RuntimeException {
        List<Student> students = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setGender(Gender.valueOf(resultSet.getString("gender")));
                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public boolean isStudentNewNameUnique(Student student) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM student where name=?")) {
            preparedStatement.setString(1, student.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id") == student.getId();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean isIdInBase(Student student) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1 FROM student where id=?")) {
            preparedStatement.setInt(1, student.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public boolean update(Student studentToBeUpdated) {
        int affectedRows=0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(("UPDATE Student SET name = ?,age= ?, gender = ? WHERE id = ?"), Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, studentToBeUpdated.getName());
            preparedStatement.setInt(2, studentToBeUpdated.getAge());
            preparedStatement.setString(3, String.valueOf(studentToBeUpdated.getGender()));
            preparedStatement.setInt(4, studentToBeUpdated.getId());
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows>0;
    }

}

