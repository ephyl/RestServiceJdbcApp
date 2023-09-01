package ephyl.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentUtilTest {

    @Test
    void getStudent_id() {
        int[] coursesIdArray = {1, 4};
        StudentUtil studentUtil = new StudentUtil(1, coursesIdArray);
        assertEquals(1, studentUtil.getStudent_id());
    }

    @Test
    void getCourse_id() {
        int[] coursesIdArray = {1, 4};
        StudentUtil studentUtil = new StudentUtil(1, coursesIdArray);
        assertArrayEquals(coursesIdArray, studentUtil.getCourse_id());
    }
}