
package servlet;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class JobSeekerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (name == null || email == null || password == null
                || name.isBlank() || email.isBlank()
                || password.isBlank()) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "All fields are required."
            );
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            boolean registered =
                    userDAO.registerJobSeeker(name, email, password);

            if (registered) {
                response.sendRedirect("pages/login.html?registered=true");
            } else {
                response.sendError(
                    HttpServletResponse.SC_CONFLICT,
                    "Email is already registered."
                );
            }
        } catch (Exception e) {
            throw new ServletException(
                "Job seeker registration failed.", e
            );
        }
    }
}
