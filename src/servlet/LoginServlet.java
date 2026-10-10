
package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()
                || password == null || password.isEmpty()) {
            response.sendRedirect("pages/login.html?error=1");
            return;
        }

        try {
            // Find the account regardless of its role
            int userId = userDAO.loginUserId(email, password);

            if (userId == -1) {
                response.sendRedirect("pages/login.html?error=1");
                return;
            }

            // Get the actual role from the database
            String role = userDAO.getUserRole(userId);

            if (role == null) {
                response.sendRedirect("pages/login.html?error=1");
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            session.setAttribute("userRole", role);

            System.out.println(
                "LOGIN SUCCESS: userId=" + userId
                + ", role=" + role
            );

            response.sendRedirect("index.html");

        } catch (SQLException e) {
            throw new ServletException("Login failed.", e);
        }
    }
}
