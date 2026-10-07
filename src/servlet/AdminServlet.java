package servlet;

import admin.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin-login")
public class AdminServlet extends HttpServlet {

    private AdminService adminService;

    @Override
    public void init() {
        adminService = new AdminService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (adminService.adminLogin(email, password)) {

            response.sendRedirect("pages/admin-dashboard.html");

        } else {

            response.sendRedirect("pages/login.html?error=1");
        }
    }
}