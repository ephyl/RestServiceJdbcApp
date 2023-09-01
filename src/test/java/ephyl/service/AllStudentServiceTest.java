package ephyl.service;

import ephyl.dao.CourseDaoJdbc;
import ephyl.dao.StudentDaoJdbc;
import ephyl.model.Course;
import ephyl.model.Student;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AllStudentServiceTest {

    @Test
    void getAllStudents() {

        StudentDaoJdbc studentDaoJdbc = Mockito.mock(StudentDaoJdbc.class);
        AllStudentService allStudentService = new AllStudentService();
        allStudentService.setStudentDaoJdbc(studentDaoJdbc);
        List<Student> list = Arrays.asList(new Student(),new Student());
        when(studentDaoJdbc.getAll()).thenReturn(list);

        assertEquals(2, allStudentService.getAllStudents().size());

    }
    @Test
    void emptyConstructor(){
        AllStudentService allStudentService =  new AllStudentService();
        assertEquals(AllStudentService.class, allStudentService.getClass());
    }
}