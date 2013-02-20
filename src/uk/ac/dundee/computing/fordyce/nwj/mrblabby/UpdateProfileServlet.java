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
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.RegisterService;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.exception.UserNotFoundException;

/**
 *
 * @author Neil
 */
@WebServlet(urlPatterns = {"/update/profile", "/update/profile/*"})
public class UpdateProfileServlet extends HttpServlet {

    
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
        }
        else{
            request.getRequestDispatcher("/updateProfile.jsp").forward(request, response);
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
        
        //Get information from form and user bean
        User user = (User) request.getSession().getAttribute("user");
        String email = user.getEmail();
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        
        //Perform update to database
        RegisterService rs = new RegisterService();
        rs.updateUser(firstname, lastname, email, password);
        
        try {
            //update user attribute for session
            request.getSession().setAttribute("user", new User(email));
        } catch (UserNotFoundException ex) {
            Logger.getLogger(UpdateProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        doGet(request, response);
    }
    
}
