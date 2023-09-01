package ephyl.service;

import ephyl.dao.CourseDaoJdbc;
import ephyl.model.Course;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseServiceTest {
    private CourseDaoJdbc courseDaoJdbc;
    private CourseService courseService;
    private Course course;

    @BeforeEach
    void init() {
        courseDaoJdbc = Mockito.mock(CourseDaoJdbc.class);
        courseService = new CourseService(courseDaoJdbc);
        course = Mockito.mock(Course.class);
    }

    @Test
    void findById() {
        int id = 1;
        when(courseDaoJdbc.findById(id)).thenReturn(Optional.of(course));
        courseService.findById(id);
        verify(courseDaoJdbc).findById(id);
    }

    @Test
    void delete() {
        int id = 2;
        courseService.delete(id);
        verify(courseDaoJdbc).delete(id);
    }

    @Test
    void update() {
        courseService.update(course);
        verify(courseDaoJdbc).update(course);
    }

    @Test
    void addNew() {
        courseService.addNew(course);
        verify(courseDaoJdbc).addNew(course);
    }
    @Test
    void emptyConstructor(){
        CourseService courseService1 =  new CourseService();
        assertEquals(CourseService.class, courseService1.getClass());
    }
}