package util;

import com.google.gson.Gson;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.types.Node;

import java.net.URI;
import java.util.Map;

public class AppUtils {
    private static final Gson GSON = new Gson();
    public static Driver driver()
    {
        URI uri = URI.create("neo4j://localhost:7687");
        String user = "neo4j";
        String password = "12345678";
        return GraphDatabase.driver(uri, AuthTokens.basic(user, password));

    }
    public static <T> T nodeToPOJO(Node node, Class<T> clazz) {
        Map<String, Object> properties = node.asMap();
        String json = GSON.toJson(properties);
        T obj = GSON.fromJson(json, clazz);
        return obj;
    }
    public static <T> Map<String, Object> pojoToMap(T obj) {
        String json = GSON.toJson(obj);
        Map<String, Object> map = GSON.fromJson(json, Map.class);
        return map;
    }
}
