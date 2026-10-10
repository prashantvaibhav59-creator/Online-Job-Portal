
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

        if (password == null || password.isEmpty()) {
            throw new SQLException("JOB_PORTAL_DB_PASSWORD is not set.");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found.", e);
        }

        return DriverManager.getConnection(URL, USER, password);
    }
}
