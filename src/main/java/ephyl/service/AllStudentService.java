package ephyl.service;

import ephyl.util.ConnectionManager;
import ephyl.dao.StudentDaoJdbc;
import ephyl.model.Student;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AllStudentService {
    private StudentDaoJdbc studentDaoJdbc = new StudentDaoJdbc();

    public void setStudentDaoJdbc(StudentDaoJdbc studentDaoJdbc) {
        this.studentDaoJdbc = studentDaoJdbc;
    }

    public List<Student> getAllStudents(){
        List<Student> all = studentDaoJdbc.getAll();
        return all.stream().sorted(Comparator.comparingInt(Student::getAge)).collect(Collectors.toList());
    }


}
