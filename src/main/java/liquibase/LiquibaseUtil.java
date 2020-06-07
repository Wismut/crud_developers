package liquibase;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import repository.connectionpool.ConnectionUtil;

import java.sql.Connection;

public class LiquibaseUtil {
    public static void updateDB() {
        try {
            Connection connection = ConnectionUtil.getConnection();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("dev\\changelog.yaml", new FileSystemResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
