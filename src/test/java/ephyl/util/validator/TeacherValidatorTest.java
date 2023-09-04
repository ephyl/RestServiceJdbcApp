package ephyl.util.validator;

import ephyl.dao.TeacherDaoJdbc;
import ephyl.model.Teacher;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TeacherValidatorTest {

    @Test
    void validate_empty_name() {
        Teacher teacher = Mockito.mock(Teacher.class);
        TeacherValidator teacherValidator =  new TeacherValidator();

        assertFalse(teacherValidator.validate(teacher));
        when(teacher.getName()).thenReturn("");
        assertFalse(teacherValidator.validate(teacher));
    }
    @Test
    void validate_invoke() {
        Teacher teacher = Mockito.mock(Teacher.class);
        TeacherDaoJdbc teacherDaoJdbc = Mockito.mock(TeacherDaoJdbc.class);
        TeacherValidator teacherValidator =  new TeacherValidator(teacherDaoJdbc);
        when(teacher.getName()).thenReturn("Name");
        when(teacherDaoJdbc.isNameUnique(any(Teacher.class))).thenReturn(true);

        boolean result = teacherValidator.validate(teacher);
        verify(teacherDaoJdbc).isNameUnique(teacher);
        assertTrue(result);

    }
}