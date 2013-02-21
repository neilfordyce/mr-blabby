/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean;

import java.io.Serializable;
import java.util.LinkedList;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.UserService;

/**
 *
 * @author Neil
 */
public class UserList implements Serializable {

    private LinkedList<User> users = new LinkedList<>();
    private int querySize;
    private static final int MAX_USERS = 10;

    public UserList() {
    }
    
    /**
     * Constructs a UserList with the friends of the input user
     * 
     * @param userWithFriends 
     */
    public UserList(User userWithFriends){
        users = userWithFriends.getFriendList();
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

        searchParameter = MessageList.cleanParameter(searchParameter);

        //Decide which user to put in the list based on idParameter
        if (searchParameter != null && !searchParameter.isEmpty()) {
            UserList ul = us.search(searchParameter, startIndex, MAX_USERS); //get users containing search term
            users = ul.getUsers();
            querySize = ul.querySize;
        }
    }
}
