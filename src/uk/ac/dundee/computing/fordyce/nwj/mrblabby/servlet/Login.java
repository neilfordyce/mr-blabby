package uk.ac.dundee.computing.fordyce.nwj.mrblabby.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.UserService;

/**
 *
 * @author Neil
 */
@WebServlet(urlPatterns = {"/login", "/login/*"})
public class Login extends HttpServlet {

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Go to profile if already logged in
        if(request.getSession().getAttribute("user") == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Get information from form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserService login = new UserService();

        try {
            User user = login.getUserLogin(email, password);
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/MrBlabby/profile");

        } catch (LoginException | SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("loginRetry", true);
            doGet(request, response);
        }
    }
}
