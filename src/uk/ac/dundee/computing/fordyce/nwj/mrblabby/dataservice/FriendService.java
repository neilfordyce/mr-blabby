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
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.UserList;

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
    public boolean addFriend(String userEmail, String friendEmail) {

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
        } finally {
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
     * Compares two email addresses for to check if users are friends
     * 
     * @param user
     * @param friend
     * @return true if the users are friends, false otherwise
     */
    public boolean isFriend(String user, String friend){
        
         try {
            connect = getConnection();

            CallableStatement cs = connect.prepareCall("{? = call is_friend(?, ?)}");
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            cs.setString(2, user);
            cs.setString(3, friend);
            cs.execute();
            
            return (cs.getBoolean(1));
            
        } catch (SQLException e) {
            System.err.println("Database connection unavailable to create message: " + e.toString());
            return false;
        } 
    }

    /**
     * Gets the friends for a particular user
     *
     * @param user to get friend for
     * @return LinkedList containing all the users from the query
     */
    public UserList getFriendList(User user, int startMessage, int maxMessages) {
        UserList friendList = new UserList();

        String userEmail = user.getEmail();

        try {
            connect = getConnection();

            PreparedStatement ps = connect.prepareStatement("SELECT friend_email "
                    + "FROM friend WHERE user_email = ?;");
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            int querySize = 0;
            
            //Read off records until you get to the start message
            while (startMessage > 0 && rs.next()) {
                querySize++;
                startMessage--;
            }

            //Create user objects for all the friends in the query and add them to a friend list
            UserService us = new UserService();
            while (maxMessages >= 0 && rs.next()) {
                String friendEmail = rs.getString("friend_email");
                User friend = us.getUser(friendEmail);
                friendList.addUser(friend);
                querySize++;
                maxMessages--;
            }
            
            querySize += countRemainingResults(rs);
            friendList.setQuerySize(querySize);

        } catch (SQLException e) {
            System.err.println("Database connection unavailable to create message: " + e.toString());
        }

        return friendList;
    }

    /**
     * Removes a friendship between two users
     *
     * @param user who is trying to delete their friend
     * @param friend who is being deleted
     * @return true if the friendship no longer exists, false otherwise
     */
    public boolean deleteFriend(String userEmail, String friendEmail) {

        try {
            connect = getConnection();

            CallableStatement cs = connect.prepareCall("{ ? = call delete_friend(?, ?)}");
            cs.registerOutParameter(1, java.sql.Types.BOOLEAN);
            cs.setString(2, userEmail);
            cs.setString(3, friendEmail);
            cs.executeQuery();

        } catch (SQLException e) {
            System.err.println("Database connection unavailable to create message: " + e.toString());
            return false;
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                Logger.getLogger(FriendService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return true;
    }
}
