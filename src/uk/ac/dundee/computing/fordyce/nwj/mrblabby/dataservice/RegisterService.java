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

        return execute("INSERT INTO `mr_blabby`.`user` (`email`, `password`, `firstname`, `lastname`) "
                + "VALUES ('" + email + "', '" + password + "', '" + firstname + "', '" + lastname + "');");
    }

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
}
