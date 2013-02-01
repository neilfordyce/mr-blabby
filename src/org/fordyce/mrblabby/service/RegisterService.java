package org.fordyce.mrblabby.service;

import org.fordyce.mrblabby.exception.PasswordMismatchException;

public class RegisterService {
	
	public boolean registerUser(String firstname, String lastname, String email, String password, String confirmPassword) throws PasswordMismatchException{
		DatabaseConnector db = new DatabaseConnector();
		
		if(firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() 
				|| password.isEmpty() || confirmPassword.isEmpty())
			return false;
		
		//TODO
		//if(db.execute(command))
			//throw new EmailExistsException();
		
		if(!password.contentEquals(confirmPassword)){
			throw new PasswordMismatchException();
		}
		
		return db.execute("INSERT INTO `mr_blabby`.`user` (`email`, `password`, `firstname`, `lastname`) " +
				"VALUES ('" + email + "', '" + password + "');");
	}
}
