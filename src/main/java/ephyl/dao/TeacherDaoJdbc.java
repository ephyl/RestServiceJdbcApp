package ephyl.dao;

import ephyl.dto.StudentDto;
import ephyl.model.Course;
import ephyl.model.Gender;
import ephyl.model.Student;
import ephyl.model.Teacher;
import ephyl.util.mapper.StudentMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherDaoJdbc implements TeacherDao {
    private final Connection connection;

    public TeacherDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM teacher")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setName(resultSet.getString("name"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachers;
    }

    @Override
    public Optional<Teacher> findById(int id) {
        Teacher teacher = null;
        List<Course> teacherCoursesList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM teacher WHERE id = ?");) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setName(resultSet.getString("name"));
            }
            if (teacher != null) {
                try (PreparedStatement preparedStatementGetCourses = connection.prepareStatement("SELECT Course.id, Course.name, course.teacher_id FROM Course JOIN teacher t on Course.teacher_id = t.id  where t.id=?")) {
                    preparedStatementGetCourses.setInt(1, id);
                    getCourses(teacher, teacherCoursesList, preparedStatementGetCourses);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(teacher);
    }

    private void getCourses(Teacher teacher, List<Course> teacherCoursesList, PreparedStatement preparedStatementGetCourses) throws SQLException {
        ResultSet resultSetOfCourses = preparedStatementGetCourses.executeQuery();
        while (resultSetOfCourses.next()) {
            Course course = new Course();
            List<Student> studentList = new ArrayList<>();
            course.setId(resultSetOfCourses.getInt("id"));
            course.setName(resultSetOfCourses.getString("name"));
            course.setTeacher_id(resultSetOfCourses.getInt("teacher_id"));
            try (PreparedStatement preparedStatementGetStudents = connection.prepareStatement("SELECT S.id, S.name, S.age, S.gender FROM Course JOIN Course_Student CS on Course.id = CS.course_id JOIN Student S on S.id = CS.student_id where course.id=?")) {
                preparedStatementGetStudents.setInt(1, course.getId());
                getStudents(course, studentList, preparedStatementGetStudents);
            }

            teacherCoursesList.add(course);
        }
        teacher.setCourseList(teacherCoursesList);
    }

    private void getStudents(Course course, List<Student> studentList, PreparedStatement preparedStatementGetStudents) throws SQLException {
        ResultSet resultSet = preparedStatementGetStudents.executeQuery();
//        StudentMapper mapper = Mappers.getMapper(StudentMapper.class);
        while (resultSet.next()) {
            Student student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setName(resultSet.getString("name"));
            student.setAge(resultSet.getInt("age"));
            student.setGender(Gender.valueOf(resultSet.getString("gender")));
            studentList.add(student);
        }
        course.setStudents(studentList);
    }


    @Override
    public long addNew(Teacher teacher) {
        long newTeacherId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(("INSERT INTO teacher(name) VALUES (?)"), Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, teacher.getName());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        long id = keys.getLong(1);
                        newTeacherId = id;
                        System.out.println(id);
                    } else {
                        throw new SQLException("No ID obtained");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newTeacherId;
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM teacher WHERE id = ?");){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Teacher teacherToBeUpdated) {
        Teacher teacher = new Teacher();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM teacher WHERE id = ?")) {
            preparedStatement.setInt(1, teacherToBeUpdated.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teacher.setId(resultSet.getInt("id"));
                teacher.setName(resultSet.getString("name"));
            }
            if (teacherToBeUpdated.getName() != null) {
                teacher.setName(teacherToBeUpdated.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int affectedRows = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(("UPDATE teacher SET name = ? WHERE id = ?"), Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setInt(2, teacher.getId());
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows > 0;
    }

    public boolean isNameUnique(Teacher teacher){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM teacher WHERE name=?")) {
            preparedStatement.setString(1, teacher.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id") == teacher.getId();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;

    }
}
