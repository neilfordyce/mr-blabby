package uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean;

import java.io.Serializable;

/**
 *
 * @author Neil
 */
public class User implements Serializable {

    private String email;
    private String firstname;
    private String lastname;

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
}
