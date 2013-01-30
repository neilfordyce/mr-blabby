package org.fordyce.mrblabby;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fordyce.mrblabby.service.RegisterService;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    enum Parameter {email, password};
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] parameter = new String[Parameter.values().length];
				
		for(Parameter para : Parameter.values())
			parameter[para.ordinal()] = request.getParameter(para.toString());
		
		RegisterService register = new RegisterService();
		register.registerUser(parameter[Parameter.email.ordinal()], parameter[Parameter.password.ordinal()]);
	}

}
