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
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.MessageList;
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

            String messageListIndex = "0";
            if (request.getParameter("messageListIndex") != null) {
                messageListIndex = request.getParameter("messageListIndex");
            }

            //Default selected user profile is same as the session user
            User selectedUser = (User) request.getSession().getAttribute("user");

            //Try to get a user for the requested profile 
            String emailParameter = request.getPathInfo();

            try {
                selectedUser = new User(emailParameter);
            } catch (UserNotFoundException ex) {
                Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            /* Find out if the user whos page was requested can be addedas a friend 
             * by the currentUser */
            User currentUser = (User) request.getSession().getAttribute("user");
            request.setAttribute("friendable", !currentUser.isFriend(selectedUser.getEmail()));
            //Selected user is only friendable when the currentUser is not friends with the selected user

            //Decide wether to display messages of friends or messages of a particular user
            MessageList messageList;
            if (currentUser.equals(selectedUser)) {
                messageList = MessageList.getFriendsMessageList(currentUser, messageListIndex);
            } else {
                messageList = MessageList.getMessageListInstance(emailParameter, messageListIndex);
            }

            request.setAttribute("messageList", messageList);

            if (messageListIndex.equals("0")) {
                //Go to profile
                request.setAttribute("profile", selectedUser);
                request.getRequestDispatcher("/profile.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/messageFragment.jsp").forward(request, response);
            }
        }
    }
}
