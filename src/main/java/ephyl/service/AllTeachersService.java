package ephyl.service;

import ephyl.util.ConnectionManager;
import ephyl.dao.TeacherDaoJdbc;
import ephyl.model.Teacher;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AllTeachersService {
    private  TeacherDaoJdbc teacherDaoJdbc= new TeacherDaoJdbc(ConnectionManager.getConnection());

    public void setTeacherDaoJdbc(TeacherDaoJdbc teacherDaoJdbc) {
        this.teacherDaoJdbc = teacherDaoJdbc;
    };

    public List<Teacher> getAllTeachers(){
        List<Teacher> all = teacherDaoJdbc.getAll();
        return all.stream().sorted(Comparator.comparing(Teacher::getName)).collect(Collectors.toList());
    }
}
