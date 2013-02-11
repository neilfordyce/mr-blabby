package uk.ac.dundee.computing.fordyce.nwj.mrblabby;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.LoginService;

/**
 *
 * @author Neil
 */
@WebServlet(urlPatterns = {"/login", "/login/*"})
public class LoginServlet extends HttpServlet {
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
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
        
        LoginService login = new LoginService();
        if(login.authenticate(email, password)) {
            User user = new User();
            user.setEmail(email);
            request.getSession().setAttribute("user", user);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
        else {
            doGet(request, response);
        }
    }
}
