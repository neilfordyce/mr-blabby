package uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Neil
 */
public class LoginService extends DatabaseConnector{
    
    /*
     * 
     * @returns true if the email and password match or false otherwise
     */
    public boolean authenticate(String email, String password)
    {
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
}
