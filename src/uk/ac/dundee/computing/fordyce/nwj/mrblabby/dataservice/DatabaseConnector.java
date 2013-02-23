package uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Resource(name = "jdbc/mr_blabby", type = javax.sql.DataSource.class)
public class DatabaseConnector {

    private DataSource ds;
    protected Connection connect;
    private Statement statement;

    /*
     * Loads MySQL Driver and sets DataSource pointer to the 
     * JNDI for the database in web.xml
     */
    public DatabaseConnector() {
        String dbname = "jdbc/mr_blabby";

        try {
            ds = (DataSource) new InitialContext().lookup("java:comp/env/" + dbname);
        } catch (NamingException e) {
            System.err.println(dbname + " is missing: " + e.toString());
        }
    }

    /*
     * Establishes a connection to the database
     */
    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            System.err.println("Error while connecting to database: " + e.toString());
        }
        return null;
    }

    /*
     * Connects to the database and issues the command
     * @param command which is executed by the database
     */
    public boolean execute(String command) {
        try {
            connect = getConnection();
            statement = connect.createStatement();
            statement.execute(command);
        } catch (SQLException e) {
            System.err.println("Error while executing SQL statement" + e.toString());
            return false;
        } finally {
            closeConnection();
        }
        return true;
    }

    /**
     * Counts the remaining records in a result set so the bean knows how many
     * it's missing
     *
     * @param rs with results to count
     * @return number of results which were in the result set
     * @throws SQLException
     */
    protected int countRemainingResults(ResultSet rs) throws SQLException {
        int querySize = 0;

        while (rs.next()) {
            querySize++;
        }
        return querySize;
    }

    /**
     * closes any connection open in the connect field
     */
    public void closeConnection() {
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(FriendService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
