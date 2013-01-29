package org.fordyce.mrblabby.service;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.Driver;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DatabaseConnector {
	private DataSource ds;
	private Connection connect;
	private Statement statement;
	
	/*
	 * Loads MySQL Driver and sets DataSource pointer to the 
	 * JNDI for the database in web.xml
	 */
	public DatabaseConnector()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			System.err.println("Error loading MySQL Driver");
			e1.printStackTrace();
		}
		
		String dbname = "jdbc/mr_blabby";
		
	    try {	        
	    	ds = (DataSource) new InitialContext().lookup("java:comp/env/" + dbname);
	    } catch (NamingException e) {
	    	System.err.println(dbname + " is missing from web.xml! : " + e.toString());
	    }
	}

	/*
	 * Establishes a connection to the database
	 */
	public Connection getConnection() throws SQLException {
		try {
			return ds.getConnection();
		}catch(SQLException e)
		{
			System.err.println("Error while connecting to database");
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Connects to the database and issues the command
	 * @param command which is executed by the database
	 */
	public void execute(String command) {

		try { 
			connect = getConnection();
			statement = connect.createStatement();

			statement.execute(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error while executing SQL statement");
			e.printStackTrace();
		}
	}
}
