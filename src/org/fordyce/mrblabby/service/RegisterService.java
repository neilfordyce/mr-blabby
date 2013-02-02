package org.fordyce.mrblabby.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.fordyce.mrblabby.exception.EmailExistsException;

public class RegisterService {
	DatabaseConnector db = new DatabaseConnector();
	
	public boolean registerUser(String firstname, String lastname, String email, String password) throws EmailExistsException{		
		
		if(firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() 
				|| password.isEmpty())
			return false;
		
		if(emailExists(email))
			throw new EmailExistsException();
		
		return db.execute("INSERT INTO `mr_blabby`.`user` (`email`, `password`) " +
				"VALUES ('" + email + "', '" + password + "');");
	}
	
	public boolean emailExists(String email){
		try{
			Connection connect = db.getConnection();
			CallableStatement proc = connect.prepareCall("{ ? = call email_exists(?)}");
			proc.registerOutParameter(1, java.sql.Types.BOOLEAN);
			proc.setString(2, email);
			proc.execute();
			return proc.getBoolean(1);
		}catch(SQLException e){
			System.err.println("Database connection unavailable");
			e.printStackTrace();
		}
		
		return false;
	}
}
