
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/online_job_portal";
    private static final String USER = "root";

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("JOB_PORTAL_DB_PASSWORD");

        if (password == null) {
            throw new SQLException("JOB_PORTAL_DB_PASSWORD is not set.");
        }

        return DriverManager.getConnection(URL, USER, password);
    }
}
