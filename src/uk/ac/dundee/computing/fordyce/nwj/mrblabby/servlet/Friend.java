/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.FriendService;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.utility.Utility;

/**
 *
 * @author Neil
 */
@WebServlet(urlPatterns = {"/friend", "/friend/*"})
public class Friend extends HttpServlet {

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * Adds friends
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        String friendID = request.getPathInfo();

        request.setAttribute("email", friendID);

        if (user.setFriend(friendID)) {
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            request.getRequestDispatcher("/userNotFound.jsp").forward(request, response);
        }
    }

    /**
     * Removes a friendship between the current user and the user with the email
     * passed in the url
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        pathInfo = Utility.cleanParameter(pathInfo);
        
        User user = (User) request.getSession().getAttribute("user");
        
        FriendService fs = new FriendService();
        fs.deleteFriend(user.getEmail(), pathInfo);
    }
}
