
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DBConnection;

public class UserDAO {

    public boolean registerJobSeeker(
            String name, String email, String password)
            throws SQLException {

        String sql = "INSERT INTO users "
                + "(name, email, password, role) "
                + "VALUES (?, ?, ?, 'JOB_SEEKER')";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name.trim());
            ps.setString(2, email.trim());
            ps.setString(3, password);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                return false; // Email already exists
            }
            throw e;
        }
    }
}
