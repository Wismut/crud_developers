package repository.connectionpool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class ConnectionUtil {
    private static final ConnectionPool connectionPool;

    static {
        try {
            Map<String, String> properties = Files.lines(Paths.get("dev/liquibase.properties"))
                    .filter(l -> !l.trim().startsWith("#"))
                    .collect(Collectors.toMap(l -> l.substring(0, l.indexOf('=')),
                            l -> l.substring(l.indexOf('=') + 1)));
            connectionPool = BasicConnectionPool.create(properties.get("url"),
                    properties.get("username"),
                    properties.get("password"));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connectionPool.getConnection();
    }

    public static void releaseConnection(Connection connection) {
        connectionPool.releaseConnection(connection);
    }
}
