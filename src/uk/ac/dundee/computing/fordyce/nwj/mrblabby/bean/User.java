package uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean;

import java.io.Serializable;
import java.util.LinkedList;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.FriendService;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.MessageService;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.UserService;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.exception.UserNotFoundException;

/**
 *
 * @author Neil
 */
public class User implements Serializable {

    private String email;
    private String firstname;
    private String lastname;

    public User() {
    }

    public User(String email) throws UserNotFoundException {
        UserService us = new UserService();

        email = MessageList.cleanParameter(email);

        User user = us.getUser(email);

        //Throw an exception if the object cannot be constructed properly
        if (user.getFirstname() == null || user.getLastname() == null) {
            throw new UserNotFoundException();
        }

        this.email = email;
        firstname = user.getFirstname();
        lastname = user.getLastname();
    }

    public User(String email, String firstname, String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LinkedList<User> getFriendList() {
        FriendService fs = new FriendService();
        return fs.getFriendList(this);
    }

    public boolean setFriend(String email) {
        email = MessageList.cleanParameter(email);
        FriendService fs = new FriendService();
        return fs.addFriend(this.email, email);
    }

    /**
     * Finds if an email address if a friend of the user or not
     *
     * @param email to search for
     * @return true if the email is a friend of the user or if the email is the
     * user
     */
    public boolean isFriend(String email) {
        email = MessageList.cleanParameter(email); //Remove any / prefix


        //Check each of the friends
        for (User friend : getFriendList()) {
            if (friend.getEmail().equals(email)) {
                return true;
            }
        }

        //Can't be true, so
        return false;
    }

    /**
     * Determines if this user is an admin or not
     *
     * @return true if they are an admin, false otherwise
     */
    public boolean isAdmin() {
        UserService us = new UserService();
        return us.isAdmin(this);
    }

    /**
     * Determines if a user has permission to delete a message with the given ID.
     * User has permission if they are admin, or if they created the message.
     * 
     * @param messageID
     * @return true if the user has permissions, false otherwise
     */
    public boolean canDelete(int messageID) {
        //check if user is admin
        if (isAdmin()) {
            return true;
        }

        //Check if message was posted by the user
        MessageService ms = new MessageService();
        MessageList messages = ms.getMessageList(String.valueOf(messageID));
        for (Message message : messages.getMessage()) {
            if(message.getSender().equals(email)) {
                return true;
            }
        }
        
        return false;
    }
}
