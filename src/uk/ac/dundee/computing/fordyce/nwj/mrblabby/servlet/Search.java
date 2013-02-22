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
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.MessageList;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.UserList;

/**
 *
 * @author Neil
 */
@WebServlet(urlPatterns = {"/search", "/search/*"})
public class Search extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Get search from search box
        String searchTerm = request.getParameter("search");
        
        //Get message list based on path info and index parameter
        String messageListIndex = request.getParameter("messageListIndex");
        
        //TODO if request is has length > \ if(request.getPathInfo()>)

        MessageList messageList = MessageList.getMessageListInstance(searchTerm, messageListIndex);
                
        UserList userList = new UserList(searchTerm, messageListIndex);
        
        request.setAttribute("messageList", messageList);
        request.setAttribute("userList", userList);
        request.setAttribute("searchTerm", searchTerm);
        
        request.getRequestDispatcher("searchResult.jsp").forward(request, response);
    }
}