package ephyl.util.validator;

import ephyl.util.ConnectionManager;
import ephyl.dao.TeacherDaoJdbc;
import ephyl.model.Teacher;

public class TeacherValidator {
    private static final TeacherDaoJdbc teacherDaoJdbc = new TeacherDaoJdbc(ConnectionManager.getConnection());

    public static boolean validate(Teacher teacher) {
        if(teacher.getName() == null || teacher.getName().length()<=1) return false;
        return isNameUnique(teacher);
    }
    private static boolean isNameUnique(Teacher teacher) {
        return teacherDaoJdbc.isNameUnique(teacher);
    }

}
