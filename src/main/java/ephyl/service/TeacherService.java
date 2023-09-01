package ephyl.service;

import ephyl.util.ConnectionManager;
import ephyl.dao.TeacherDaoJdbc;
import ephyl.model.Teacher;
import ephyl.util.exception.TeacherNotFoundException;

import java.util.Optional;

public class TeacherService implements CrudService<Teacher> {
    private TeacherDaoJdbc teacherDaoJdbc = new TeacherDaoJdbc(ConnectionManager.getConnection());

    public TeacherService(TeacherDaoJdbc teacherDaoJdbc) {
        this.teacherDaoJdbc = teacherDaoJdbc;
    }

    public TeacherService() {
    }

    public Teacher findById(int id) {
        Optional<Teacher> teacherOptional = teacherDaoJdbc.findById(id);
        return teacherOptional.orElseThrow(TeacherNotFoundException::new);
    }

    public boolean update(Teacher teacher) {
        return teacherDaoJdbc.update(teacher);
    }

    public void delete(int id) {
        teacherDaoJdbc.delete(id);
    }

    public long addNew(Teacher teacher) {
        return teacherDaoJdbc.addNew(teacher);
    }
}
