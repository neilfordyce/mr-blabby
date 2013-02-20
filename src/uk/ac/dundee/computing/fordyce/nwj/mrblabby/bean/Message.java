package uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Neil
 */
public class Message implements Serializable {
    
    private String message;
    private String sender;
    private int id;
    private Calendar createdTime;
    
    public Message() {
        
    }

    public Message(String message, String sender, Calendar createdTime, int id) {
        this.message = message;
        this.sender = sender;
        this.createdTime = createdTime;
        this.id = id;
    }
    
    public Message(String message, String sender, long createdTimeMillis, int id) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(createdTimeMillis);
        
        this.message = message;
        this.sender = sender;
        this.createdTime = cal;
        this.id = id;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }
    
    /**
     * Get the hours/minutes/seconds since the message was created or the date 
     * it was created depending on whether it was created less of more than 24 
     * hours ago.
     * 
     * Decides what is the most appropriate unit of time to display.
     * 
     * @return Formatted time message created
     */
    public String getFormattedTime() {
        
        //Calculate duration between creating message and now
        Calendar duration = Calendar.getInstance();
        duration.setTimeInMillis(Calendar.getInstance().getTimeInMillis() 
                                            - createdTime.getTimeInMillis());
        
        //Return varying format of date based on duration
        //Divide by 1000 to prevent errors from int type on the right
        if(duration.getTimeInMillis()/1000 >= 60*60*24*175){    //More than 175 days ago
            SimpleDateFormat sdf = new SimpleDateFormat("d MMM YYYY");
            return sdf.format(createdTime.getTime());
        }
        else if(duration.getTimeInMillis() >= 1000*60*60*24){   //More than a day ago
            SimpleDateFormat sdf = new SimpleDateFormat("d MMM");
            return sdf.format(createdTime.getTime());
        }
        else if(duration.getTimeInMillis() >= 1000*60*60) {     //More than an hour ago
            return duration.get(Calendar.HOUR_OF_DAY) + "h";
        }
        else if(duration.getTimeInMillis() >= 1000*60) {        //More than a minute ago
            return duration.get(Calendar.MINUTE) + "m";
        }
        else {                                                  //Less than a minute ago
            return duration.get(Calendar.SECOND) + "s";
        }
    }
}
