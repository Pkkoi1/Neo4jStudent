package dao;

import entity.Student;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class studentDao {
    private Driver driver;

    public studentDao() {
        driver = util.AppUtils.driver();
    }

    //    close
    public void close() {
        driver.close();
    }


    public List<Student> getAllStudents() {
        String query = "MATCH (s:Student) RETURN s";
        Map<String, Object> params = Map.of();
        try (Session session = driver.session()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("s").asNode())
                        .map(node -> util.AppUtils.nodeToPOJO(node, Student.class))
                        .toList();
            });
        }
    }

    public Student getStudentById(String id) {
        String query = "MATCH (s:Student) WHERE s.studentID = $id RETURN s";
        Map<String, Object> params = Map.of("id", id);
        try (Session session = driver.session()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("s").asNode())
                        .map(node -> util.AppUtils.nodeToPOJO(node, Student.class))
                        .findFirst()
                        .orElse(null);
            });
        }
    }

    public List<String> findStudentName(String id) {
        String query = "MATCH (s:Student) - [:ENROLLED] -> (c:Course) WHERE c.courseID = $id RETURN s.name";
        Map<String, Object> params = Map.of("id", id);
        try (Session session = driver.session()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("s.name").asString())
                        .toList();
            });
        }
    }

    public Map<String, Integer> countStudentEnrolled() {
//        giảm dần
        String query = "MATCH (s:Student) - [:ENROLLED] -> (c:Course) - [:BELONGS_TO] -> (d:Department) RETURN d.deptID, count(s) as count ORDER BY count ASC";
        Map<String, Object> params = Map.of();
        try (Session session = driver.session()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .collect((Collectors.toMap(
                                record -> record.get("d.deptID").asString(),
                                record -> record.get("count").asInt(),
                                (x, y) -> y,
                                LinkedHashMap::new
                        )));
            });
        }
    }

    public Map<String, Integer> countStudentsByDepartmentOrderByDeptID() {
        String query = "MATCH (d:Department)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student) \n" +
                "RETURN d.deptID AS deptID, COUNT(*) AS total \n" +
                "ORDER BY d.deptID";
        try (Session session = driver.session()) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> record.get("deptID").asString(),
                                record -> record.get("total").asInt(),
                                (x, y) -> y,
                                LinkedHashMap::new
                        ));
            });
        }
    }

    public void addStudent(Student student) {
        String query = "CREATE (s:Student{studentID: $studentID, name: $name, gpa: $gpa}) return s";
        Map<String, Object> params = util.AppUtils.pojoToMap(student);
        try (Session session = driver.session()) {
            session.executeWrite(tx -> {
                return tx.run(query, params).consume();
            });
        }
    }

    public void deleteStudent(String id) {
        String query = "MATCH (s:Student) WHERE s.studentID = $id DETACH DELETE s";
        Map<String, Object> params = Map.of("id", id);
        try (Session session = driver.session()) {
            session.executeWrite(tx -> {
                return tx.run(query, params).consume();
            });
        }
    }

    public void updateStudent(Student student) {
        String query = "MATCH (s:Student) WHERE s.studentID = $studentID SET s.name = $name, s.gpa = $gpa";
        Map<String, Object> params = util.AppUtils.pojoToMap(student);
        try (Session session = driver.session()) {
            session.executeWrite(tx -> {
                return tx.run(query, params).consume();
            });
        }
    }

    //    gpa >= 3.2
    public List<Student> getStudentGpa32() {
        String query = "MATCH (s:Student) WHERE s.gpa >= 3.2 RETURN s";
        Map<String, Object> params = Map.of();
        try (Session session = driver.session()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("s").asNode())
                        .map(node -> util.AppUtils.nodeToPOJO(node, Student.class))
                        .toList();
            });
        }
    }

    public List<Student> getStudentGpa32OrderByGpa() {
        String query = "MATCH (s:Student) WHERE s.gpa >= 3.2 RETURN s ORDER BY s.gpa DESC";
        Map<String, Object> params = Map.of();
        try (Session session = driver.session()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("s").asNode())
                        .map(node -> util.AppUtils.nodeToPOJO(node, Student.class))
                        .toList();
            });
        }
    }

    public List<Student> getStudentGpa32OrderByGpaDesc() {
        String query = "MATCH (s:Student) WHERE s.gpa >= 3.2 RETURN s ORDER BY s.gpa ASC";
        Map<String, Object> params = Map.of();
        try (Session session = driver.session()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("s").asNode())
                        .map(node -> util.AppUtils.nodeToPOJO(node, Student.class))
                        .toList();
            });
        }
    }

    public List<Student> getStudentGpa32OrderByGpaDescLimit() {
        String query = "MATCH (s:Student) WHERE s.gpa >= 3.2 RETURN s ORDER BY s.gpa DESC ";
        Map<String, Object> params = Map.of();
        try (Session session = driver.session()) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("s").asNode())
                        .map(node -> util.AppUtils.nodeToPOJO(node, Student.class))
                        .toList();
            });

        }
    }
}