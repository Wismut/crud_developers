package util;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import repository.pool.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class LiquibaseUtil {
    public static void updateDB() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(LiquibaseUtil.class.getClassLoader().getResource("changelog.yaml").getPath(), new FileSystemResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
            ConnectionUtil.releaseConnection(connection);
        } catch (LiquibaseException | SQLException e) {
            e.printStackTrace();
        }
    }
}
