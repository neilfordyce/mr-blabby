package org.fordyce.mrblabby;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fordyce.mrblabby.exception.PasswordMismatchException;
import org.fordyce.mrblabby.service.RegisterService;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(urlPatterns = {"/register", "/register/*"})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get information from form
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		
		//Pass the info to the register service
		RegisterService register = new RegisterService();
		try {
			register.registerUser(firstname, lastname, email, password, confirmPassword);
		} catch (PasswordMismatchException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			
			return;
		}
		
		//Forward to success page
		request.getRequestDispatcher("/root.jsp").forward(request, response);
	}

}
