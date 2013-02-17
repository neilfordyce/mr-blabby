/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby;

import java.io.IOException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
        //System.out.println(request.getPathTranslated());
        //System.out.println(request.getPathInfo().startsWith("/create"));




        //Login check
        if ((User) request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/MrBlabby/login");
        } else {

            int startIndex = 0;
            int maxMessages = 10;
            String messageListIndex = request.getParameter("messageListIndex");
            if (messageListIndex != null) {
                startIndex = Integer.parseInt(messageListIndex);
            }

            MessageList messageList = new MessageList();
            String idParameter = request.getPathInfo();
            idParameter = cleanParameter(idParameter);
            if (idParameter != null && !idParameter.isEmpty()) {
                System.out.println(idParameter);

                if (isEmail(idParameter)) {
                    messageList = new MessageList(idParameter, startIndex, maxMessages);
                } else if (isNumeric(idParameter)) {
                    int id = Integer.parseInt(idParameter);
                    messageList = new MessageList(id);
                }
            } else {
                messageList = new MessageList(startIndex, maxMessages);
            }

            if (messageList.getMessage().isEmpty()) {
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

    /**
     * Checks if an email address is valid Based on answer from
     * http://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
     *
     * @return true if email address is valid, false otherwise
     */
    private boolean isEmail(String idParameter) {
        boolean result = true;

        try {
            InternetAddress emailAddr = new InternetAddress(idParameter);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Removes / prefix from parameter
     *
     * @param idParameter
     * @return idParameter with / removed
     */
    private String cleanParameter(String idParameter) {
        return idParameter.replaceAll("[/]", "");
    }

    /**
     * Determines if id is numeric
     *
     * @param idParameter
     * @return true if the id is numeric
     */
    private boolean isNumeric(String idParameter) {
        return idParameter.matches("\\d+");
    }
}
