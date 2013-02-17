package uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean;

import java.io.Serializable;
import java.util.LinkedList;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.MessageService;

/**
 *
 * @author Neil
 */
public class MessageList implements Serializable {

    private LinkedList<Message> message = new LinkedList<>();
    private int startMessageIndex = 0;
    private int querySize = 0;
    private static final int maxMessages = 10;

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
        MessageList messageList = ms.getMessageList(startMessage, maxMessages);
        message = messageList.getMessage();
        querySize = messageList.getQuerySize();
    }

    public MessageList(User user, int startMessage) {
        this.startMessageIndex = startMessage;
        MessageService ms = new MessageService();
        MessageList messageList = ms.getMessageList(user, startMessage, maxMessages);
        message = messageList.getMessage();
        querySize = messageList.getQuerySize();
    }

    public MessageList(String user, int startMessage) {
        this.startMessageIndex = startMessage;
        MessageService ms = new MessageService();
        MessageList messageList = ms.getMessageList(user, startMessage, maxMessages);
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
        return maxMessages;
    }

    /**
     * Decide what messages to put in the message list based on path info
     *
     * @param idParameter email or message id, if it is not valid all messages are selected
     * @param messageListIndex where to start the message list from, null=0
     * @return Message list with contents specified by request idParameter
     */
    public static MessageList getMessageListInstance(String idParameter, String messageListIndex) {
        
        //Take a start index if one is given, otherwise make it 0
        int startIndex = 0;
        if (messageListIndex != null) {
            startIndex = Integer.parseInt(messageListIndex);
        }
        
        //Set up message list
        MessageList messageList = new MessageList();
        idParameter = cleanParameter(idParameter);
        
        //Decide which messages to put in the list based on idParameter
        if (idParameter != null && !idParameter.isEmpty()) {
        
            if (isEmail(idParameter)) {
                messageList = new MessageList(idParameter, startIndex); //get messages of a particular user
            } else if (isNumeric(idParameter)) {
                messageList = new MessageList(idParameter);             //get message with a particular id
            }
            
        } else {
            messageList = new MessageList(startIndex);                  //get all messages
        }
        
        return messageList;
    }
    
    /**
     * Checks if an email address is valid Based on answer from
     * http://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
     *
     * @return true if email address is valid, false otherwise
     */
    private static boolean isEmail(String idParameter) {
        boolean result = true;

        try {
            InternetAddress emailAddr = new InternetAddress(idParameter);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Removes / prefix from parameter
     *
     * @param idParameter
     * @return idParameter with / removed
     */
    private static String cleanParameter(String idParameter) {
        return idParameter.replaceAll("[/]", "");
    }

    /**
     * Determines if id is numeric
     *
     * @param idParameter
     * @return true if the id is numeric
     */
    private static boolean isNumeric(String idParameter) {
        return idParameter.matches("\\d+");
    }
}
