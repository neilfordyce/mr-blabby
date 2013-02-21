package uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.sasl.AuthenticationException;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.Message;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.MessageList;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;

/**
 * Service for connecting to database and querying Message table
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
            System.err.println("Database connection unavailable to get message: " + e.toString());
        }
    }

    /**
     * Gets a message list of the most recent messages from a user.
     *
     * @param user account bean
     * @param maxMessages max number of messages to return
     * @return MessageList bean containing most recent messages
     */
    public MessageList getMessageList(User user, int startMessage, int maxMessages) {
        return getMessageList(user.getEmail(), startMessage, maxMessages);
    }

    /**
     * Gets a message list of the most recent messages from a particular user.
     *
     * @param email of user account
     * @param startMessage omit most recent message up until this point
     * @param maxMessages max number of messages to return
     * @return MessageList bean containing most recent messages first
     */
    public MessageList getMessageList(String email, int startMessage, int maxMessages) {
        try {
            connect = getConnection();

            PreparedStatement ps = connect.prepareStatement("SELECT * "
                    + "FROM message WHERE email = ?"
                    + "ORDER BY created_timestamp DESC;");
            ps.setString(1, email);
            MessageList messageList = execute(ps, startMessage, maxMessages);

            return messageList;
        } catch (SQLException e) {
            System.err.println("Database connection unavailable to get message: " + e.toString());
        }

        return null;
    }

    /**
     * Gets a message list for the message with a particular id
     *
     * @param id
     * @return
     */
    public MessageList getMessageList(String id) {
        try {
            connect = getConnection();

            PreparedStatement ps = connect.prepareStatement("SELECT * "
                    + "FROM message WHERE message_id = ?");

            ps.setInt(1, Integer.parseInt(id));

            MessageList messageList = execute(ps, 0, 1);

            return messageList;
        } catch (SQLException e) {
            System.err.println("Database connection unavailable to get message: " + e.toString());
        }

        return null;
    }

    /**
     * Gets a message list for all the messages
     *
     * @param startMessage
     * @param maxMessages
     * @return
     */
    public MessageList getMessageList(int startMessage, int maxMessages) {
        try {
            connect = getConnection();

            PreparedStatement ps = connect.prepareStatement("SELECT * "
                    + "FROM message "
                    + "ORDER BY created_timestamp DESC;");

            MessageList messageList = execute(ps, startMessage, maxMessages);


            return messageList;
        } catch (SQLException e) {
            System.err.println("Database connection unavailable to get message: " + e.toString());
        }

        return null;
    }

    /**
     *
     * Gets a message list for all the messages posted by a users friends in
     * order of most recent first
     *
     * @param user - to find friends of to list messages for
     * @param startMessage
     * @param maxMessages
     * @return
     */
    public MessageList getFriendsMessageList(User user, int startMessage, int maxMessages) {
        String email = user.getEmail();

        try {
            connect = getConnection();

            PreparedStatement ps = connect.prepareStatement("SELECT * "
                    + "FROM message, friend "
                    + "WHERE friend.user_email = ? "
                    + "AND friend.friend_email = email "
                    + "ORDER BY created_timestamp DESC;");

            ps.setString(1, email);

            MessageList messageList = execute(ps, startMessage, maxMessages);

            return messageList;
        } catch (SQLException e) {
            System.err.println("Database connection unavailable to get message: " + e.toString());
        }

        return null;
    }

    /**
     * General method for creating MessageList from the resultset of prepared
     * statement.
     *
     * @param ps - executed to get message from database
     * @param startMessage - index of first message to put in list
     * @param maxMessages - max number of messages to put in the list
     * @return a list of messages from the database
     * @throws SQLException
     */
    private MessageList execute(PreparedStatement ps, int startMessage, int maxMessages) throws SQLException {
        ResultSet rs = ps.executeQuery();
        int querySize = 0;

        //Read off records until you get to the start message
        while (startMessage > 0 && rs.next()) {
            querySize++;
            startMessage--;
        }

        //Create message beans and add them to a message list bean
        MessageList messageList = new MessageList();
        while (maxMessages >= 0 && rs.next()) {
            querySize++;
            Message message = new Message(rs.getString("message"),
                    rs.getString("email"),
                    rs.getTimestamp("created_timestamp").getTime(),
                    rs.getInt("message_id"));

            messageList.addMessage(message);
            maxMessages--;
        }

        querySize += countRemainingResults(rs);
        messageList.setQuerySize(querySize);

        connect.close();

        return messageList;
    }

    /**
     * Deletes a message record from the message table
     *
     * @param id of message to be deleted
     * @param user who is making the request
     * @return true if the delete was successful, false otherwise
     * @throws AuthenticationException when user doesn't have sufficient
     * permissions to delete the message
     */
    public boolean deleteMessage(int id, User user) throws AuthenticationException {

        if (!user.canDelete(id)) {
            throw new AuthenticationException();
        }

        try {
            connect = getConnection();
            CallableStatement proc = connect.prepareCall("{ ? = call delete_message(?)}");
            proc.registerOutParameter(1, java.sql.Types.BOOLEAN);
            proc.setInt(2, id);
            proc.execute();

        } catch (SQLException e) {
            System.err.println("Database connection unavailable to delete message: " + e.toString());
            return false;
        } finally {
            try {
                connect.close();
            } catch (SQLException ex) {
                System.err.println("Unable to close connection: " + ex.toString());
            }
        }

        return true;
    }

    /**
     * Searches the database for messages containing the search term and returns
     * a list of themGets a message list.
     *
     * @param searchKey to look for
     * @param maxMessages to get
     * @param startMessage index to start from
     * @return list of messages containing the search term
     */
    public MessageList search(String searchKey, int startMessage, int maxMessages) {
        try {
            connect = getConnection();

            PreparedStatement ps = connect.prepareStatement("SELECT * "
                    + "FROM message WHERE message LIKE ? "
                    + "ORDER BY created_timestamp DESC;");

            ps.setString(1, "%" + searchKey + "%");

            MessageList messageList = execute(ps, startMessage, maxMessages);

            return messageList;
        } catch (SQLException e) {
            System.err.println("Database connection unavailable to get message: " + e.toString());
        }

        return null;
    }
}
