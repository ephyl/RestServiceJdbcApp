package ephyl.service;

import ephyl.dao.CourseDaoJdbc;
import ephyl.model.Course;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AllCoursesServiceTest {


    @Test
    void getAll() {
        CourseDaoJdbc courseDaoJdbc = Mockito.mock(CourseDaoJdbc.class);
        AllCoursesService allCoursesService =  new AllCoursesService();
        allCoursesService.setCourseDaoJdbc(courseDaoJdbc);

        List<Course> courseList  = new ArrayList<>();
        courseList.add(new Course());
        when(courseDaoJdbc.getAll()).thenReturn(courseList);

        assertEquals(1 , allCoursesService.getAll().size());
    }
    @Test
    void emptyConstructor(){
        AllCoursesService allCoursesService =  new AllCoursesService();
        assertEquals(AllCoursesService.class, allCoursesService.getClass());
    }
}