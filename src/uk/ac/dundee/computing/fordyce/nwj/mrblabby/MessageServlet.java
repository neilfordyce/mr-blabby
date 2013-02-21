/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby;

import java.io.IOException;
import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.MessageList;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.MessageService;

/**
 *
 * @author Neil
 */
@WebServlet(urlPatterns = {"/message", "/message/*"})
public class MessageServlet extends HttpServlet {

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

        //Login check
        if ((User) request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/MrBlabby/login");
        } else {
        
            /*
             * TODO remove
             * pre-ajax method for deleting a message
             * 
            if(request.getPathInfo() != null && request.getPathInfo().endsWith("/delete")){
                doDelete(request, response);
                return;
            }*/
            
            String messageListIndex = request.getParameter("messageListIndex");

            //Get message list based on path info and index parameter
            MessageList messageList = MessageList.getMessageListInstance(request.getPathInfo(), messageListIndex);

            //Decide which page to forward to based on contents of message list
            if (messageList == null || messageList.getMessage().isEmpty()) {
                request.getRequestDispatcher("/messageNotFound.jsp").forward(request, response);
            } else {

                request.setAttribute("messageList", messageList);

                if (messageListIndex == null) {
                    request.getRequestDispatcher("/messageList.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/messageFragment.jsp").forward(request, response);
                }
            }
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * Creates message
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageService ms = new MessageService();
        ms.createMessage((User) request.getSession().getAttribute("user"), request.getParameter("message"));
        doGet(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User)request.getSession().getAttribute("user");
        String requestParams = request.getPathInfo();
        
        /*
         * TODO remove
         * pre-ajax method of deleting messages*/
        //requestParams = requestParams.replaceAll("/delete", "");
        requestParams = MessageList.cleanParameter(requestParams);
        
        try {
            int messageID = Integer.parseInt(requestParams);
            MessageService ms = new MessageService();
            ms.deleteMessage(messageID, user);
                
        }catch(NumberFormatException nfe){
            //response.sendError(400, "InvalidQueryParameterValue");
        }catch(AuthenticationException ae){
            //response.sendError(403, "InsufficientAccountPermissions");
        }
        
        //TODO remove pre-ajax
        //response.sendRedirect(request.getContextPath() + "/message");
    }
}
