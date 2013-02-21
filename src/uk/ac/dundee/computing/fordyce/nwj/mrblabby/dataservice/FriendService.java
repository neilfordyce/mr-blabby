/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;

/**
 * Interface to friend table
 *
 * @author Neil
 */
public class FriendService extends DatabaseConnector {

    /**
     * Creates a record in the database linking two users
     * 
     * @param userEmail
     * @param friendEmail
     * @return true if insert was successful, false otherwise
     */
    public boolean addFriend(String userEmail, String friendEmail){
        
        //TODO Consider removing, you might want to follow your own posts
        if(userEmail.equals(friendEmail)) { //Check your not trying to add yourself as a friend
            return false;                   //Keep the sad people out
        }
        
        try {
            connect = getConnection();
            
            CallableStatement cs = connect.prepareCall("{ ? = call insert_friend(?, ?)}");
            cs.registerOutParameter(1, java.sql.Types.BOOLEAN);
            cs.setString(2, userEmail);
            cs.setString(3, friendEmail);
            cs.executeQuery();
            
        } catch (SQLException e) {
            System.err.println("Database connection unavailable to create message: " + e.toString());
            return false;
        } finally{
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(FriendService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return true;
    }
    
    /**
     * Adds a friend
     * 
     * @param user
     * @param friend
     * @return 
     */
    public boolean addFriend(User user, User friend) {
        String userEmail = user.getEmail();
        String friendEmail = user.getEmail();
        
        return addFriend(userEmail, friendEmail);
    }

    /**
     * Gets the friends for a particular user
     * 
     * @param user to get friend for
     * @return LinkedList containing all the users from the query
     */
    public LinkedList<User> getFriendList(User user) {
        LinkedList<User> friendList = new LinkedList<>();

        String userEmail = user.getEmail();

        try {
            connect = getConnection();

            PreparedStatement ps = connect.prepareStatement("SELECT friend_email "
                    + "FROM friend WHERE user_email = ?;");
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();
            
            //Create user objects for all the friends in the query and add them to a friend list
            UserService us = new UserService();
            while(rs.next()){
                String friendEmail = rs.getString("friend_email");
                User friend = us.getUser(friendEmail);
                friendList.add(friend);
            }

        } catch (SQLException e) {
            System.err.println("Database connection unavailable to create message: " + e.toString());
        }

        return friendList;
    }
}
