package dao;

import entity.Course;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.types.Node;

import java.util.List;
import java.util.Map;

public class courseDao {
    private Driver driver;

    public courseDao() {
        driver = util.AppUtils.driver();
    }

    public void close() {
        driver.close();
    }
    public List<Course> getAllCourses(String id) {
        String query = "MATCH (c:Course) -[:BELONGS_TO]-> (d:Department) where toUpper(d.deptID) = $id RETURN c";
        Map<String, Object> params = Map.of("id", id);
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                           .map(record -> record.get("c").asNode())
                           .map(node -> util.AppUtils.nodeToPOJO(node, Course.class))
                           .toList();
            });
        }
    }
    public void addCourse (Course course)
    {
        String query = "CREATE (c:Course{courseID: $courseID, name: $name, hours: $hours}) return c";
        Map<String, Object> params = util.AppUtils.pojoToMap(course);
        try(Session session = driver.session())
        {
            session.executeWrite(tx -> {
                return tx.run(query, params).consume();
            });
        }
    }
    public Course getCourseById(String id) {
        String query = "MATCH (c:Course) WHERE c.courseID = $id RETURN c";
        Map<String, Object> params = Map.of("id", id);
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                if (!result.hasNext())
                {
                    return null;
                }
                Record record = result.next();
                Node node = record.get("c").asNode();
                Course course = util.AppUtils.nodeToPOJO(node, Course.class);
                return course;

            });
        }
    }
//    Chỉ xóa node không có relationship
//            hoăặc gọi detach để ngắc kết nối và xóa node
//
    public void deleteCourse(String id)
    {
        String query = "MATCH (c:Course) WHERE c.courseID = $id detach DELETE c";
        Map<String, Object> params = Map.of("id", id);
        try(Session session = driver.session())
        {
            session.executeWrite(tx -> {
                return tx.run(query, params).consume();
            });
        }
    }
//    Liệt kê tất cả khóa học của CS và IE
    public List<Course> getAllCourses() {
        String query = "MATCH (c:Course) RETURN c";
        Map<String, Object> params = Map.of();
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("c").asNode())
                        .map(node -> util.AppUtils.nodeToPOJO(node, Course.class))
                        .toList();
            });
        }
    }
}
