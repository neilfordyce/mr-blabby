package uk.ac.dundee.computing.fordyce.nwj.mrblabby;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.RegisterService;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.exception.EmailExistsException;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(urlPatterns = {"/register", "/register/*"})
public class RegisterServlet extends HttpServlet {
    
    /*
     * Show the register.jsp page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Get information from form
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        //Pass the info to the register service
        RegisterService register = new RegisterService();
        try {
            register.registerUser(firstname, lastname, email, password);
            //Forward to success page
            request.setAttribute("name", firstname);
            request.getRequestDispatcher("/registerSuccess.jsp").forward(request, response);
            
        } catch (EmailExistsException e) {
            request.setAttribute("email", email);
            request.getRequestDispatcher("/emailExists.jsp").forward(request, response);
        }
    }
}
