package ephyl.service;

import ephyl.dao.StudentDaoJdbc;
import ephyl.dao.TeacherDaoJdbc;
import ephyl.model.Student;
import ephyl.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceTest {
    private StudentService studentService;
    private StudentDaoJdbc studentDaoJdbc;
    private Student student;

    @BeforeEach
    void init() {
        studentDaoJdbc = Mockito.mock(StudentDaoJdbc.class);
        studentService = new StudentService(studentDaoJdbc);
        student = Mockito.mock(Student.class);
    }

    @Test
    void findById() {
        int id = 1;
        when(studentDaoJdbc.findById(id)).thenReturn(Optional.of(student));
        studentService.findById(id);
        verify(studentDaoJdbc).findById(id);
    }

    @Test
    void addNew() {
        studentService.addNew(student);
        verify(studentDaoJdbc).addNew(student);
    }

    @Test
    void delete() {
        int id = 2;
        when(student.getId()).thenReturn(id);
        studentService.delete(student.getId());
        verify(studentDaoJdbc).delete(id);
    }

    @Test
    void update() {
        studentService.update(student);
        verify(studentDaoJdbc).update(student);
    }
    @Test
    void emptyConstructor(){
        StudentService studentService1 =  new StudentService();
        assertEquals(StudentService.class, studentService1.getClass());
    }
}