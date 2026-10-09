
package dao;

import model.Company;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDAO {

    // Create a company profile for an existing employer
    public boolean createCompany(Company company) throws SQLException {
        String sql = "INSERT INTO companies " +
                "(user_id, company_name, description, location) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, company.getEmployerId());
            ps.setString(2, company.getCompanyName());
            ps.setString(3, company.getDescription());
            ps.setString(4, company.getLocation());

            return ps.executeUpdate() > 0;
        }
    }

    // Find a company profile using the employer's user ID
    public Company getCompanyByEmployerId(int employerId)
            throws SQLException {

        String sql = "SELECT company_id, company_name, description, " +
                "location, user_id FROM companies WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Company(
                            rs.getInt("company_id"),
                            rs.getString("company_name"),
                            rs.getString("description"),
                            rs.getString("location"),
                            rs.getInt("user_id")
                    );
                }
            }
        }

        return null;
    }

    // Update an existing employer's company profile
    public boolean updateCompany(Company company)
            throws SQLException {

        String sql = "UPDATE companies SET company_name = ?, " +
                "description = ?, location = ? " +
                "WHERE company_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, company.getCompanyName());
            ps.setString(2, company.getDescription());
            ps.setString(3, company.getLocation());
            ps.setInt(4, company.getCompanyId());
            ps.setInt(5, company.getEmployerId());

            return ps.executeUpdate() > 0;
        }
    }
}
