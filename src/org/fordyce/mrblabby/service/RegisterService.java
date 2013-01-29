package org.fordyce.mrblabby.service;

public class RegisterService {
	
	public boolean registerUser(String email, String password){
		DatabaseConnector db = new DatabaseConnector();
		
		db.execute("INSERT INTO `mr_blabby`.`user` (`email`, `password`) " +
				"VALUES ('" + email + "', '" + password + "');");

		return true;
	}
}
