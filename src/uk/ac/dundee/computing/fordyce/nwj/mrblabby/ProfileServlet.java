/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.exception.UserNotFoundException;

/**
 *
 * @author Neil
 */
@WebServlet(urlPatterns = {"/profile", "/profile/*"})
public class ProfileServlet extends HttpServlet {

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
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/MrBlabby/login");
        } else {
            String emailParameter = request.getPathInfo();
            
            User user = null;
            try {
                user = new User(emailParameter);

            } catch (UserNotFoundException ex) {
                Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (user == null) {
                user = (User) request.getSession().getAttribute("user");
            }
            request.setAttribute("profile", user);
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
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
    }
}
