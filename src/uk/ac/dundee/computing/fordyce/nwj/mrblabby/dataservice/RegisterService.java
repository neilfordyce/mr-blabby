package uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice;

import java.sql.CallableStatement;
import java.sql.SQLException;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.exception.EmailExistsException;

public class RegisterService extends DatabaseConnector {

    public boolean registerUser(String firstname, String lastname, String email, String password) throws EmailExistsException {

        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return false;
        }

        if (emailExists(email)) {
            throw new EmailExistsException();
        }

        try {
            connect = getConnection();
            CallableStatement proc = connect.prepareCall("{ ? = call insert_user(?, ?, ?, ?)}");
            proc.registerOutParameter(1, java.sql.Types.BOOLEAN);
            proc.setString(2, email);
            proc.setString(3, password);
            proc.setString(4, firstname);
            proc.setString(5, lastname);
            proc.execute();
            connect.close();
        } catch (SQLException e) {
            System.err.println("Database connection unavailable: " + e.toString());
            return false;
        }

        return true;
    }

    /**
     * Queries the database for an email address to check if it is in the
     * database or not
     *
     * @param email to look for
     * @return true if the email is found, false otherwise
     */
    public boolean emailExists(String email) {
        try {
            connect = getConnection();
            CallableStatement proc = connect.prepareCall("{ ? = call email_exists(?)}");
            proc.registerOutParameter(1, java.sql.Types.BOOLEAN);
            proc.setString(2, email);
            proc.execute();
            return proc.getBoolean(1);
        } catch (SQLException e) {
            System.err.println("Database connection unavailable: " + e.toString());
        }

        return false;
    }

    /**
     * Updates a users details
     *
     * @param firstname - new firstname
     * @param lastname - new lastname
     * @param email - email address the account is registered to
     * @param password - new password
     * @return true if update was successful, false otherwise
     */
    public boolean updateUser(String firstname, String lastname, String email, String password) {
        try {
            connect = getConnection();
            CallableStatement proc = connect.prepareCall("{ ? = call update_user(?, ?, ?, ?)}");
            proc.registerOutParameter(1, java.sql.Types.BOOLEAN);
            proc.setString(2, email);
            proc.setString(3, password);
            proc.setString(4, firstname);
            proc.setString(5, lastname);
            proc.execute();
            connect.close();
        } catch (SQLException e) {
            System.err.println("Database connection unavailable: " + e.toString());
            return false;
        }

        return true;
    }
}
