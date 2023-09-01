package ephyl.service;

import ephyl.dao.CourseDaoJdbc;
import ephyl.dao.TeacherDaoJdbc;
import ephyl.model.Course;
import ephyl.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TeacherServiceTest {
    private TeacherService teacherService;
    private TeacherDaoJdbc teacherDaoJdbc;
    private Teacher teacher;

    @BeforeEach
    void init() {
        teacherDaoJdbc = Mockito.mock(TeacherDaoJdbc.class);
        teacherService = new TeacherService(teacherDaoJdbc);
        teacher = Mockito.mock(Teacher.class);
    }

    @Test
    void findById() {
        int id = 1;
        when(teacherDaoJdbc.findById(id)).thenReturn(Optional.of(teacher));
        teacherService.findById(id);
        verify(teacherDaoJdbc).findById(id);
    }

    @Test
    void update() {
        teacherService.update(teacher);
        verify(teacherDaoJdbc).update(teacher);
    }

    @Test
    void delete() {
        int id = 2;
        teacherService.delete(id);
        verify(teacherDaoJdbc).delete(id);
    }

    @Test
    void addNew() {
        teacherService.addNew(teacher);
        verify(teacherDaoJdbc).addNew(teacher);
    }
    @Test
    void emptyConstructor(){
        TeacherService teacherService1 =  new TeacherService();
        assertEquals(TeacherService.class, teacherService1.getClass());
    }
}