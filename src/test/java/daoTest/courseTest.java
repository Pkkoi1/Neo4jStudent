package daoTest;
import dao.courseDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class courseTest {

    static courseDao courseDao;

    @BeforeAll
    static void setup() {
        courseDao = new courseDao();
    }
    @Test
    void testGetAllCourses() {
        courseDao.getAllCourses("IE").forEach(System.out::println);
    }
    @Test
    void testGetCourseById() {
        System.out.println(courseDao.getCourseById("CS101"));
    }
    @Test
    void testAddCourse() {
        courseDao.addCourse(new entity.Course("IE202", "Simulation", 3));
    }
    @Test
    void deleteCourse() {
        courseDao.deleteCourse("CS101");
    }

}
