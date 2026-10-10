package dao;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    public List<String[]> getApplicantsByEmployerId(int employerId)
            throws SQLException {

        List<String[]> applicants = new ArrayList<>();

        String sql = """
            SELECT
                a.application_id,
                a.job_id,
                j.title,
                u.user_id,
                u.name,
                u.email,
                a.status,
                a.applied_at
            FROM applications a
            JOIN jobs j ON a.job_id = j.job_id
            JOIN companies c ON j.company_id = c.company_id
            JOIN users u ON a.user_id = u.user_id
            WHERE c.user_id = ?
            ORDER BY a.applied_at DESC
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    applicants.add(new String[] {
                        String.valueOf(rs.getInt("application_id")),
                        String.valueOf(rs.getInt("job_id")),
                        rs.getString("title"),
                        String.valueOf(rs.getInt("user_id")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("status"),
                        String.valueOf(rs.getTimestamp("applied_at"))
                    });
                }
            }
        }

        return applicants;
    }

    public boolean updateApplicationStatus(
            int applicationId,
            int employerId,
            String status) throws SQLException {

        if (!List.of(
                "UNDER_REVIEW",
                "SHORTLISTED",
                "INTERVIEW",
                "SELECTED",
                "REJECTED"
        ).contains(status)) {
            throw new IllegalArgumentException("Invalid application status.");
        }

        String sql = """
            UPDATE applications a
            JOIN jobs j ON a.job_id = j.job_id
            JOIN companies c ON j.company_id = c.company_id
            SET a.status = ?
            WHERE a.application_id = ?
              AND c.user_id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, applicationId);
            ps.setInt(3, employerId);

            return ps.executeUpdate() > 0;
        }
    }
}