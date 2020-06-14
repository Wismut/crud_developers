package repository.pool;

import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final ConnectionPool connectionPool;

    static {
        try {
            Properties properties = new Configuration().configure().getProperties();
            connectionPool = BasicConnectionPool.create(properties.getProperty("hibernate.connection.url"),
                    properties.getProperty("hibernate.connection.username"),
                    properties.getProperty("hibernate.connection.password"));
        } catch (SQLException e) {
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
