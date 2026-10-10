
package util;

import java.sql.Connection;

public class DBConnectionTest {
    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.println("DATABASE CONNECTION SUCCESSFUL!");
            System.out.println("Connected to: "
                    + con.getCatalog());
        } catch (Exception e) {
            System.out.println("DATABASE CONNECTION FAILED!");
            System.out.println(e.getMessage());
        }
    }
}
