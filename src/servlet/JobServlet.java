
package servlet;

import java.io.IOException;
import java.util.List;

import dao.JobDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Job;

@WebServlet("/approved-jobs")
public class JobServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try {
            List<Job> jobs = new JobDAO().getApprovedJobs();

            if (jobs.isEmpty()) {
                response.getWriter().write("<p>No approved jobs available yet.</p>");
                return;
            }

            for (Job job : jobs) {
                response.getWriter().write(
                    "<div class=\"job-card\">" +
                    "<h3>" + escape(job.getTitle()) + "</h3>" +
                    "<p>" + escape(job.getDescription()) + "</p>" +
                    "<p>Location: " + escape(job.getLocation()) + "</p>" +
                    "<p>Salary: ₹" + job.getSalary() + "</p>" +
                    "<form action=\"apply\" method=\"post\">" +
                    "<input type=\"hidden\" name=\"jobId\" value=\"" +
                    job.getJobId() + "\">" +
                    "<button type=\"submit\" class=\"apply-link\">Apply Now →</button>" +
                    "</form></div>"
                );
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("<p>Could not load jobs. Please try again.</p>");
            e.printStackTrace();
        }
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
