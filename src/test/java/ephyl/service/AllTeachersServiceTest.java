package ephyl.service;

import ephyl.dao.StudentDaoJdbc;
import ephyl.dao.TeacherDaoJdbc;
import ephyl.model.Student;
import ephyl.model.Teacher;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AllTeachersServiceTest {

    @Test
    void getAllTeachers() {
        TeacherDaoJdbc teacherDaoJdbc = Mockito.mock(TeacherDaoJdbc.class);
        AllTeachersService allTeacherService = new AllTeachersService();
        allTeacherService.setTeacherDaoJdbc(teacherDaoJdbc);
        List<Teacher> list = Collections.singletonList(new Teacher());
        when(teacherDaoJdbc.getAll()).thenReturn(list);

        assertEquals(1, allTeacherService.getAllTeachers().size());
    }
    @Test
    void emptyConstructor(){
        AllTeachersService allTeachersService =  new AllTeachersService();
        assertEquals(AllTeachersService.class, allTeachersService.getClass());
    }
}