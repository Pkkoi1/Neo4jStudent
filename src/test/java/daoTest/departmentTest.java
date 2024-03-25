package daoTest;
import  dao.departmentDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class departmentTest {
    static departmentDao departmentDao;

    @BeforeAll
    static void setup() {
        departmentDao = new departmentDao();
    }
    @Test
    void testFindDeptName() {
        System.out.println(departmentDao.findDeptName("Math"));
    }
    @Test
    void testUpDateDeptName() {
        departmentDao.upDateDeptName("Math", "Mathematics");

    }
    @Test
    void testUpdateMusic() {
        departmentDao.updateMusic("Music", "Rock and Roll");
    }
    @Test
    void testFindDeptMusic() {
        System.out.println(departmentDao.findDeptName("Music"));
    }
    @Test
    void testFindAllDept() {
        departmentDao.getAllDepartments().forEach(System.out::println);
    }
    @Test
    void getDeanName() {
        System.out.println(departmentDao.getDeanName());
    }
    @Test
    void deanName() {
        System.out.println( departmentDao.findDeanName("CS"));
    }
    @Test
    void getDeanNoStudents() {
        System.out.println(departmentDao.getDeanNoStudent());
    }
    @Test
    void getDeptMostStudent() {
        System.out.println(departmentDao.getDeptMostStudent());
    }

}
