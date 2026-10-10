
package servlet;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/session-status")
public class SessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        boolean loggedIn = session != null
                && session.getAttribute("userId") != null;

        String role = loggedIn
                ? (String) session.getAttribute("userRole")
                : "";

        if (role == null) {
            role = "";
        }

        response.getWriter().write(
                "{\"loggedIn\":" + loggedIn
                + ",\"role\":\"" + role + "\"}"
        );
    }
}
