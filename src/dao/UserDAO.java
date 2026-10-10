
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                return false;
            }
            throw e;
        }
    }

    public int loginJobSeeker(String email, String password)
            throws SQLException {

        String sql = "SELECT user_id FROM users "
                + "WHERE email = ? AND password = ? "
                + "AND role = 'JOB_SEEKER'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email.trim());
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
                return -1;
            }
        }
    }

   
    // Login for any account role
    public int loginUserId(String email, String password)
            throws SQLException {

        String sql = "SELECT user_id FROM users "
                + "WHERE email = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email.trim());
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
                return -1;
            }
        }
    }

    // Get the role assigned to this user
    public String getUserRole(int userId) throws SQLException {

        String sql = "SELECT role FROM users WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
                return null;
            }
        }
    }
}