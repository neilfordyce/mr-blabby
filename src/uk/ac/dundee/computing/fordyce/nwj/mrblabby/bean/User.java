package uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean;

import java.io.Serializable;
import java.util.LinkedList;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.FriendService;
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
        if(user.getFirstname() == null || user.getLastname() == null) {
            throw new UserNotFoundException();
        }
        
        this.email = email;
        firstname = user.getFirstname();
        lastname = user.getLastname();
    }
    
    public User(String email, String firstname, String lastname)  {
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
    
    public boolean setFriend(String email){
        email = MessageList.cleanParameter(email);
        FriendService fs = new FriendService();
        return fs.addFriend(this.email, email);
    }
}
