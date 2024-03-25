package dao;

import entity.Department;
import org.neo4j.driver.Record;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.types.Node;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class departmentDao {
    private Driver driver;

    public departmentDao() {
        driver = util.AppUtils.driver();
    }

    public void close() {
        driver.close();
    }
    public void upDateDeptName(String id, String deptName)
    {
        String query = "MATCH (d:Department) WHERE d.deptID = $id SET d.name = $deptName";
        Map<String, Object> params = Map.of("id", id, "deptName", deptName);
        try(Session session = driver.session())
        {
            session.executeWrite(tx -> {
                return tx.run(query, params).consume();
            });
        }
    }
    public Department findDeptName(String id)
    {
        String query = "MATCH (d:Department) WHERE d.deptID = $id RETURN d";
        Map<String, Object> params = Map.of("id", id);
        try(Session session = driver.session())
        {
           return session.executeRead(tx -> {
               Result result = tx.run(query, params);
               if(!result.hasNext())
               {
                   return null;
               }
               Record record = result.next();
               Node node = record.get("d").asNode();
               Department department = util.AppUtils.nodeToPOJO(node, Department.class);
                return department;
           });
        }
    }
    public void updateMusic (String id, String music)
    {
        String query = "MATCH (d:Department) WHERE d.deptID = $id SET d.name = $music";
        Map<String, Object> params = Map.of("id", id, "music", music);
        try(Session session = driver.session())
        {
            session.executeWrite(tx -> {
                return tx.run(query, params).consume();
            });
        }
    }
    public List<Department> getAllDepartments() {
        String query = "MATCH (d:Department) RETURN d";
        Map<String, Object> params = Map.of();
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("d").asNode())
                        .map(node1 -> util.AppUtils.nodeToPOJO(node1, Department.class))
                        .toList();
            });
        }
    }
    public List<String> getDeanName()
    {
        String query = "MATCH (d:Department) RETURN d.dean";
        Map<String, Object> params = Map.of();
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("d.dean").asString())
                        .toList();
            });
        }
    }
    public String findDeanName(String id)
    {
        String query = "MATCH (d:Department) WHERE d.deptID = $id RETURN d.dean";
        Map<String, Object> params = Map.of("id", id);
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                if(!result.hasNext())
                {
                    return null;
                }
                return result.next().get("d.dean").asString();
            });
        }
    }

//    Liệt kê danh sách tên caác trưởng khoa không có sinh viên đăng ký

    public List<String> getDeanNoStudent()
    {
        String query = "MATCH (d:Department) WHERE NOT (d)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student) RETURN d.dean";
        Map<String, Object> params = Map.of();
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .map(record -> record.get("d.dean").asString())
                        .toList();
            });
        }
    }
//    Danh sách khoa có sinh viên đăng ký nhieu nhất
    public Map<String, Integer> getDeptMostStudent()
    {
//        max
        String query = "MATCH (d:Department) - [:BELONGS_TO] -> (c:Course) - [:ENROLLED] -> (s:Student) RETURN d.deptID, count(s) as count ORDER BY count DESC";        Map<String, Object> params = Map.of();
        try(Session session = driver.session())
        {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> record.get("d.deptID").asString(),
                                record -> record.get("count").asInt()
                        ));
            });
        }
    }
}
