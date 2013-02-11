package uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean;

import java.io.Serializable;

/**
 *
 * @author Neil
 */
public class User implements Serializable {

    private String email;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }    
}
