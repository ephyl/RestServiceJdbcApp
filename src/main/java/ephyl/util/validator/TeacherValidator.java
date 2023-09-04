package ephyl.util.validator;

import ephyl.util.ConnectionManager;
import ephyl.dao.TeacherDaoJdbc;
import ephyl.model.Teacher;

public class TeacherValidator {
    private TeacherDaoJdbc teacherDaoJdbc = new TeacherDaoJdbc();

    public TeacherValidator() {
    }

    public TeacherValidator(TeacherDaoJdbc teacherDaoJdbc) {
        this.teacherDaoJdbc = teacherDaoJdbc;
    }

    public  boolean validate(Teacher teacher) {
        if(teacher.getName() == null || teacher.getName().length()<=1) return false;
        return teacherDaoJdbc.isNameUnique(teacher);
    }


}
