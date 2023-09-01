package ephyl.service;

import ephyl.dao.CourseDaoJdbc;
import ephyl.dao.CourseManagerDao;
import ephyl.dao.CourseManagerDaoJdbc;
import ephyl.model.Course;
import ephyl.util.StudentUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseManagerServiceTest {
    private CourseManagerDaoJdbc courseManagerDaoJdbc;
    private CourseManagerService courseManagerService;
    private StudentUtil studentUtil;

    @BeforeEach
    void init() {
        courseManagerDaoJdbc = Mockito.mock(CourseManagerDaoJdbc.class);
        courseManagerService = new CourseManagerService(courseManagerDaoJdbc);
        studentUtil = Mockito.mock(StudentUtil.class);
    }
    @Test
    void assignCourses() {
        int[] arr = {4, 3};
        when(studentUtil.getStudent_id()).thenReturn(1);
        when(studentUtil.getCourse_id()).thenReturn(arr);
        when(courseManagerDaoJdbc.assignCourses(studentUtil.getStudent_id(), studentUtil.getCourse_id())).thenReturn(2);
        int result = courseManagerService.assignCourses(studentUtil);
        verify(courseManagerDaoJdbc).assignCourses(studentUtil.getStudent_id(), studentUtil.getCourse_id());
    }

    @Test
    void unAssignCourses() {
        int[] arr = {4, 3};
        when(studentUtil.getStudent_id()).thenReturn(1);
        when(studentUtil.getCourse_id()).thenReturn(arr);
        when(courseManagerDaoJdbc.unAssignCourses(studentUtil.getStudent_id(), studentUtil.getCourse_id())).thenReturn(2);
        int result = courseManagerService.unAssignCourses(studentUtil);
        verify(courseManagerDaoJdbc).unAssignCourses(studentUtil.getStudent_id(), studentUtil.getCourse_id());
    }
    @Test
    void emptyConstructor(){
        CourseManagerService courseManagerService1 =  new CourseManagerService();
        assertNotNull(courseManagerService1);
        assertEquals(CourseManagerService.class, courseManagerService1.getClass());
    }
}