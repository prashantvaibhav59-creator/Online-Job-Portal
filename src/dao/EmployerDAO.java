
package dao;

import model.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployerDAO {

    // Register a new employer
    public boolean registerEmployer(
            String name, String email, String password)
            throws SQLException {

        String sql = """
            INSERT INTO users (name, email, password, role)
            VALUES (?, ?, ?, 'EMPLOYER')
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            return ps.executeUpdate() > 0;
        }
    }

    // Login an employer using email and password
    public User loginEmployer(String email, String password)
            throws SQLException {

        String sql = """
            SELECT user_id, name, email, password, role
            FROM users
            WHERE email = ?
              AND password = ?
              AND role = 'EMPLOYER'
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                    );

                    return user;
                }
            }
        }

        return null;
    }

    // Find an employer by their user ID
    public User getEmployerById(int employerId)
            throws SQLException {

        String sql = """
            SELECT user_id, name, email, password, role
            FROM users
            WHERE user_id = ?
              AND role = 'EMPLOYER'
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                    );
                }
            }
        }

        return null;
    }
}
