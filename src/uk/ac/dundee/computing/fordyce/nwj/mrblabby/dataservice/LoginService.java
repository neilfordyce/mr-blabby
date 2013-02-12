package uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;

/**
 *
 * @author Neil
 */
public class LoginService extends DatabaseConnector {

    /**
     * Queries the database to see if the email and password given matches a 
     * record in the database
     * 
     * @param email 
     * @param password
     * @return true if the email and password match or false otherwise
     */
    private boolean authenticate(String email, String password) {
        connect = getConnection();
        try {
            CallableStatement proc = connect.prepareCall("{ ? = call authenticate(?, ?)}");
            proc.registerOutParameter(1, java.sql.Types.BOOLEAN);
            proc.setString(2, email);
            proc.setString(3, password);
            proc.execute();
            return proc.getBoolean(1);
        } catch (SQLException ex) {
            System.err.println("error authenticating login credentials: " + ex.toString());
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * Authenticates the credentials then creates a User bean 
     * containing email and names for the given email password combo
     * 
     * @param email
     * @param password
     * @return User bean object with email and names
     * @throws LoginException when credentials are wrong
     * @throws SQLException if there is a connection error
     */
    public User getUser(String email, String password) throws LoginException, SQLException {

        if (!authenticate(email, password)) {
            throw new LoginException("Email and password do not match");
        }
        
        connect = getConnection();
        
        CallableStatement proc = connect.prepareCall("{call getNames(?, ?, ?, ?)}");
        proc.setString(1, email);
        proc.setString(2, password);
        proc.registerOutParameter(3, java.sql.Types.VARCHAR);
        proc.registerOutParameter(4, java.sql.Types.VARCHAR);
        proc.execute();
        
        String firstname = proc.getString(3);
        String lastname = proc.getString(4);
        
        connect.close();
        
        User user = new User(email, firstname, lastname);
        return user;
    }
}
