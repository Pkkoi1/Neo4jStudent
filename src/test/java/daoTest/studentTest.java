package daoTest;
import dao.studentDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class studentTest {
    private static studentDao studentDao;

    @BeforeAll
    static void setup() {
        studentDao = new studentDao();
    }
    @Test
    void testGetAllStudents() {
        studentDao.getAllStudents().forEach(System.out::println);
    }
    @Test
    void testGetStudentById() {
        System.out.println(studentDao.getStudentById("11"));
    }
    @Test
    void testFindStudentName() {
        System.out.println(studentDao.findStudentName("MA101"));
    }
    @Test
    void countStudentEnrolled() {
        System.out.println(studentDao.countStudentEnrolled());
    }
    @Test
    void countStudentsByDepartmentOrderByDeptID() {
        System.out.println(studentDao.countStudentsByDepartmentOrderByDeptID());
    }
    @Test
    void countStudentsByDepartmentOrderByDeptIDDesc() {
        System.out.println(studentDao.getStudentGpa32OrderByGpaDescLimit());
    }


}
