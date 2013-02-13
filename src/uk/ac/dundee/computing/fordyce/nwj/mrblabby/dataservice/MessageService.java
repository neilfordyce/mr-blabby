/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice;

import java.sql.CallableStatement;
import java.sql.SQLException;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;

/**
 *
 * @author Neil
 */
public class MessageService extends DatabaseConnector {

    /**
     * Adds message to the database for a given user and message
     * 
     * @param user bean
     * @param message 
     */
    public void createMessage(User user, String message) {
        try {
            connect = getConnection();
            CallableStatement proc = connect.prepareCall("{ ? = call create_message(?, ?)}");
            proc.registerOutParameter(1, java.sql.Types.BOOLEAN);
            proc.setString(2, user.getEmail());
            proc.setString(3, message);
            proc.execute();
            connect.close();
        } catch (SQLException e) {
            System.err.println("Database connection unavailable to create message: " + e.toString());
        }
    }
}
