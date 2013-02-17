package uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean;

import java.io.Serializable;
import java.util.LinkedList;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice.MessageService;

/**
 *
 * @author Neil
 */
public class MessageList implements Serializable {
    
    private LinkedList<Message> message = new LinkedList<>();
    private int startMessageIndex = 0;
    private int querySize = 0;
    
    public MessageList() {
        
    }
    
    public MessageList(int messageID) {
        this.startMessageIndex = 0;
        MessageService ms = new MessageService();
        MessageList messageList = ms.getMessageList(messageID);
        message = messageList.getMessage();
        querySize = 1;
    }
    
    public MessageList(int startMessage, int maxMessages) {
        this.startMessageIndex = startMessage;
        MessageService ms = new MessageService();
        MessageList messageList = ms.getMessageList(startMessage, maxMessages);
        message = messageList.getMessage();
        querySize = messageList.getQuerySize();
    }

    public MessageList(User user, int startMessage, int maxMessages) {
        this.startMessageIndex = startMessage;
        MessageService ms = new MessageService();
        MessageList messageList = ms.getMessageList(user, startMessage, maxMessages);
        message = messageList.getMessage();
        querySize = messageList.getQuerySize();
    }
    
    public MessageList(String user, int startMessage, int maxMessages) {
        this.startMessageIndex = startMessage;
        MessageService ms = new MessageService();
        MessageList messageList = ms.getMessageList(user, startMessage, maxMessages);
        message = messageList.getMessage();
        querySize = messageList.getQuerySize();
    }
    
    public void setQuerySize(int querySize){
        this.querySize = querySize;
    }

    /**
     * A message list contains a portion of the total database query.  
     * @return querySize specifies the size of the entire query.
     */
    public int getQuerySize(){
        return querySize;
    }
    
    public int getSize() {
        return message.size();
    }
    
    public int getStartIndex(){
        return startMessageIndex;
    }
    
    public int getEndIndex(){
        return message.size() + startMessageIndex;
    }

    public LinkedList<Message> getMessage() {
        return message;
    }

    public void setMessage(LinkedList<Message> message) {
        this.message = message;
    }
    
    public void addMessage(Message message){
        this.message.add(message);
    }
}
