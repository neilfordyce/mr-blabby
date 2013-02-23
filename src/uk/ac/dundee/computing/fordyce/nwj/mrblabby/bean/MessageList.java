package uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean;

import java.io.Serializable;
import java.util.LinkedList;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.MessageService;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.utility.Utility;

/**
 *
 * @author Neil
 */
public class MessageList implements Serializable {

    private LinkedList<Message> message = new LinkedList<>();
    private int startMessageIndex = 0;
    private int querySize = 0;
    private static final int MAX_MESSAGES = 10;

    public MessageList() {
    }

    public MessageList(String messageID) {
        this.startMessageIndex = 0;
        MessageService ms = new MessageService();
        MessageList messageList = ms.getMessageList(messageID);
        message = messageList.getMessage();
        querySize = 1;
    }

    public MessageList(int startMessage) {
        this.startMessageIndex = startMessage;
        MessageService ms = new MessageService();
        MessageList messageList = ms.getMessageList(startMessage, MAX_MESSAGES);
        message = messageList.getMessage();
        querySize = messageList.getQuerySize();
    }

    public MessageList(User user, int startMessage) {
        this.startMessageIndex = startMessage;
        MessageService ms = new MessageService();
        MessageList messageList = ms.getMessageList(user, startMessage, MAX_MESSAGES);
        message = messageList.getMessage();
        querySize = messageList.getQuerySize();
    }

    public MessageList(String user, int startMessage) {
        this.startMessageIndex = startMessage;
        MessageService ms = new MessageService();
        MessageList messageList = ms.getMessageList(user, startMessage, MAX_MESSAGES);
        message = messageList.getMessage();
        querySize = messageList.getQuerySize();
    }

    public void setQuerySize(int querySize) {
        this.querySize = querySize;
    }

    /**
     * A message list contains a portion of the total database query.
     *
     * @return querySize specifies the size of the entire query.
     */
    public int getQuerySize() {
        return querySize;
    }

    public int getSize() {
        return message.size();
    }

    public int getStartIndex() {
        return startMessageIndex;
    }

    public int getEndIndex() {
        return message.size() + startMessageIndex;
    }

    public LinkedList<Message> getMessage() {
        return message;
    }

    public void setMessage(LinkedList<Message> message) {
        this.message = message;
    }

    public void addMessage(Message message) {
        this.message.add(message);
    }

    public static int getMaxMessages() {
        return MAX_MESSAGES;
    }

    /**
     * Decide what messages to put in the message list based on path info
     *
     * @param idParameter email or message id, if it is not valid all messages
     * are selected
     * @param messageListIndex where to start the message list from, null=0
     * @return Message list with contents specified by request idParameter
     */
    public static MessageList getMessageListInstance(String idParameter, String messageListIndex) {

        MessageService ms = new MessageService();

        //Take a start index if one is given, otherwise make it 0
        int startIndex = 0;
        if (messageListIndex != null) {
            startIndex = Integer.parseInt(messageListIndex);
        }

        //Set up message list
        MessageList messageList;
        idParameter = Utility.cleanParameter(idParameter);

        //Decide which messages to put in the list based on idParameter
        if (idParameter != null && !idParameter.isEmpty()) {

            if (Utility.isEmail(idParameter)) {
                messageList = new MessageList(idParameter, startIndex); //get messages of a particular user
            } else if (Utility.isNumeric(idParameter)) {
                messageList = new MessageList(idParameter);             //get message with a particular id
            } else {
                messageList = ms.search(idParameter, startIndex, MAX_MESSAGES); //get messages containing search term
            }

        } else {
            messageList = new MessageList(startIndex);                  //get all messages
        }

        return messageList;
    }

    /**
     * Gets a message list which contains only messages from friends
     * 
     * @param user who has friends
     * @param messageListIndex start from this record
     * @return messages from friends
     */
    public static MessageList getFriendsMessageList(User user, String messageListIndex) {

        int startIndex = 0;
        try {
            startIndex = Integer.parseInt(messageListIndex);
        } catch (NumberFormatException nfe) {
        }

        MessageService ms = new MessageService();
        
        MessageList messageList;
        messageList = ms.getFriendsMessageList(user, startIndex, MAX_MESSAGES);
        
        return messageList;
    }
}
