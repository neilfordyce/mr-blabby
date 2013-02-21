/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.MessageList;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;

/**
 *
 * @author Neil
 */
@WebServlet(urlPatterns = {"/friend", "/friend/*"})
public class FriendServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //doPost(request, response);

    }

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
        
        if(user == null){
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
            
        String friendID = request.getPathInfo();

        friendID = MessageList.cleanParameter(friendID);
        request.setAttribute("email", friendID);
        
        if (user.setFriend(friendID)) {
            request.getRequestDispatcher("/friendSuccess.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/userNotFound.jsp").forward(request, response);
            
            //        .getFriendList();
        }
    }
}
