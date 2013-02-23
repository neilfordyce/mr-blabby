/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean;

import java.io.Serializable;
import java.util.LinkedList;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.FriendService;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.UserService;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.utility.Utility;

/**
 *
 * @author Neil
 */
public class UserList implements Serializable {

    private LinkedList<User> users = new LinkedList<>();
    private int querySize;
    private static final int MAX_USERS = 8;

    public UserList() {
    }
    
    /**
     * Constructs a UserList with the friends of the input user
     * 
     * @param user who has friends to be added to this list
     * @param startIndex to start off from
     */
    public UserList(User user, int startIndex){
        FriendService fs = new FriendService();
        UserList userList = fs.getFriendList(user, startIndex, MAX_USERS);
        users = userList.users;
        querySize = userList.querySize;
    }

    public LinkedList<User> getUsers() {
        return users;
    }

    public void setUsers(LinkedList<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public int getQuerySize() {
        return querySize;
    }

    public void setQuerySize(int querySize) {
        this.querySize = querySize;
    }
    
    /**
     * 
     * @return size of internal linked list
     */
    public int getSize(){
        return users.size();
    }
    
    /**
     * Create a list of users from a search term
     *
     * @param searchParameter email or message id, if it is not valid all messages
     * are selected
     * @param messageListIndex where to start the message list from, null=0
     * @return Message list with contents specified by request idParameter
     */
    public UserList(String searchParameter, String messageListIndex) {

        UserService us = new UserService();

        //Take a start index if one is given, otherwise make it 0
        int startIndex = 0;
        if (messageListIndex != null) {
            startIndex = Integer.parseInt(messageListIndex);
        }

        searchParameter = Utility.cleanParameter(searchParameter);

        //Decide which user to put in the list based on idParameter
        if (searchParameter != null && !searchParameter.isEmpty()) {
            UserList ul = us.search(searchParameter, startIndex, MAX_USERS); //get users containing search term
            users = ul.getUsers();
            querySize = ul.querySize;
        }
    }
}
