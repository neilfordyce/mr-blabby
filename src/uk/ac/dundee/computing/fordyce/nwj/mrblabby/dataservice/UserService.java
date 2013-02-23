package uk.ac.dundee.computing.fordyce.nwj.mrblabby.dataservice;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.User;
import uk.ac.dundee.computing.fordyce.nwj.mrblabby.bean.UserList;

/**
 *
 * @author Neil
 */
public class UserService extends DatabaseConnector {

    /**
     * Queries the database to see if the email and password given matches a
     * record in the database
     *
     * @param email
     * @param password
     * @return true if the email and password match or false otherwise
     */
    private boolean authenticate(String email, String password) {
        connect = getConnection();
        try {
            CallableStatement proc = connect.prepareCall("{ ? = call authenticate(?, ?)}");
            proc.registerOutParameter(1, java.sql.Types.BOOLEAN);
            proc.setString(2, email);
            proc.setString(3, password);
            proc.execute();
            return proc.getBoolean(1);
        } catch (SQLException ex) {
            System.err.println("error authenticating login credentials: " + ex.toString());
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        return false;
    }

    /**
     * Authenticates the credentials then creates a User bean containing email
     * and names for the given email
     *
     * @param email
     * @param password
     * @return User bean object with email and names
     * @throws LoginException when credentials are wrong
     * @throws SQLException if there is a connection error
     */
    public User getUserLogin(String email, String password) throws LoginException, SQLException {

        if (!authenticate(email, password)) {
            throw new LoginException("Email and password do not match");
        }

        return getUser(email);
    }

    /**
     * Gets a User object for user account with corresponding email
     *
     * @param email
     * @return User populated with email and names associated with that email
     */
    public User getUser(String email) {
        String firstname = "";
        String lastname = "";

        try {
            connect = getConnection();

            CallableStatement proc = connect.prepareCall("{call get_names(?, ?, ?)}");
            proc.setString(1, email);
            proc.registerOutParameter(2, java.sql.Types.VARCHAR);
            proc.registerOutParameter(3, java.sql.Types.VARCHAR);
            proc.execute();

            firstname = proc.getString(2);
            lastname = proc.getString(3);

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }

        User user = new User(email, firstname, lastname);
        return user;
    }

    /**
     * Checks if a user is an admin or not
     *
     * @param user
     * @return
     */
    public boolean isAdmin(User user) {
        String email = user.getEmail();

        try {
            connect = getConnection();

            CallableStatement proc = connect.prepareCall("{? = call is_admin(?)}");

            proc.registerOutParameter(1, java.sql.Types.BOOLEAN);
            proc.setString(2, email);
            proc.execute();

            return proc.getBoolean(1);

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            closeConnection();
        }

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
    public UserList search(String searchKey, int startUser, int maxUsers) {
        try {
            connect = getConnection();

            PreparedStatement ps = connect.prepareStatement("SELECT email, firstname, lastname "
                    + "FROM user WHERE email LIKE ?;");

            ps.setString(1, "%" + searchKey + "%");

            UserList userList = execute(ps, startUser, maxUsers);

            return userList;
        } catch (SQLException e) {
            System.err.println("Database connection unavailable to get message: " + e.toString());
        }

        return null;
    }

    /**
     * General method for creating UserList from the resultset of prepared
     * statement.
     *
     * @param ps - executed to get users from database
     * @param startUser - index of first user to put in list
     * @param maxUsers - max number of user to put in the list
     * @return a list of users from the database
     * @throws SQLException
     */
    private UserList execute(PreparedStatement ps, int startUser, int maxUsers) throws SQLException {
        ResultSet rs = ps.executeQuery();
        int querySize = 0;

        //Read off records until you get to the start message
        while (startUser > 0 && rs.next()) {
            querySize++;
            startUser--;
        }

        //Create user beans and add them to a uesr list bean
        UserList userList = new UserList();
        while (maxUsers >= 0 && rs.next()) {
            querySize++;
            User user = new User(rs.getString("email"),
                    rs.getString("firstname"),
                    rs.getString("lastname"));

            userList.addUser(user);
            maxUsers--;
        }
        
        querySize += countRemainingResults(rs);
        
        userList.setQuerySize(querySize);

        connect.close();

        return userList;
    }
}
