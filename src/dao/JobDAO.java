
package dao;

import model.Job;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    // Post a new job
    public boolean createJob(Job job) throws SQLException {
        String sql = """
            INSERT INTO jobs
                (company_id, title, description, location, salary, status)
            SELECT company_id, ?, ?, ?, ?, 'PENDING'
            FROM companies
            WHERE user_id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, job.getTitle());
            ps.setString(2, job.getDescription());
            ps.setString(3, job.getLocation());
            ps.setDouble(4, job.getSalary());
            ps.setInt(5, job.getEmployerId());

            return ps.executeUpdate() > 0;
        }
    }

    // Get all jobs belonging to an employer
    public List<Job> getJobsByEmployerId(int employerId)
            throws SQLException {
        List<Job> jobs = new ArrayList<>();

        String sql = """
            SELECT j.job_id, j.title, j.description,
                   j.location, j.salary, j.status
            FROM jobs j
            JOIN companies c ON j.company_id = c.company_id
            WHERE c.user_id = ?
            ORDER BY j.posted_at DESC
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, employerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    jobs.add(new Job(
                        rs.getInt("job_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getDouble("salary"),
                        employerId,
                        rs.getString("status")
                    ));
                }
            }
        }

        return jobs;
    }

    // Update a job owned by the employer
    public boolean updateJob(Job job) throws SQLException {
        String sql = """
            UPDATE jobs j
            JOIN companies c ON j.company_id = c.company_id
            SET j.title = ?,
                j.description = ?,
                j.location = ?,
                j.salary = ?,
                j.status = 'PENDING'
            WHERE j.job_id = ?
              AND c.user_id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, job.getTitle());
            ps.setString(2, job.getDescription());
            ps.setString(3, job.getLocation());
            ps.setDouble(4, job.getSalary());
            ps.setInt(5, job.getJobId());
            ps.setInt(6, job.getEmployerId());

            return ps.executeUpdate() > 0;
        }
    }

    // Delete a job owned by the employer
    public boolean deleteJob(int jobId, int employerId)
            throws SQLException {
        String sql = """
            DELETE j FROM jobs j
            JOIN companies c ON j.company_id = c.company_id
            WHERE j.job_id = ?
              AND c.user_id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, jobId);
            ps.setInt(2, employerId);

            return ps.executeUpdate() > 0;
        }
    }
}
