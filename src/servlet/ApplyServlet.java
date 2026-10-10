
package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.DBConnection;

@WebServlet("/apply")
public class ApplyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {
            response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Please log in first."
            );
            return;
        }

        int userId;
        int jobId;

        try {
            userId = Integer.parseInt(
                session.getAttribute("userId").toString()
            );
            jobId = Integer.parseInt(request.getParameter("jobId"));
        } catch (Exception e) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid user or job ID."
            );
            return;
        }

        String sql = "INSERT INTO applications (job_id, user_id) "
                   + "SELECT j.job_id, ? FROM jobs j "
                   + "WHERE j.job_id = ? AND j.status = 'APPROVED'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, jobId);

            if (ps.executeUpdate() == 1) {
                response.setStatus(
                    HttpServletResponse.SC_CREATED
                );
                response.getWriter().write(
                    "Application submitted successfully."
                );
            } else {
                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Approved job not found, or you already applied."
                );
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                response.sendError(
                    HttpServletResponse.SC_CONFLICT,
                    "You have already applied for this job."
                );
            } else {
                throw new ServletException(
                    "Application submission failed.", e
                );
            }
        }
    }
}
