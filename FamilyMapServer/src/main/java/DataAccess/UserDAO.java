package DataAccess;

import Model.User;

import java.sql.*;
import java.util.Locale;

/**
 * A class that passes a User into the database
 */
public class UserDAO {
    /**
     * An sql connection variable
     */
    private final Connection conn;

    /**
     * A constructor that takes an sql connection variable
     * @param conn an sql connection variable
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Validates the login of a user with a given username and password
     * @param username A username
     * @param password A password
     * @return true if validated successfully, false otherwise
     * @throws DataAccessException
     */
    public boolean validateLogin(String username, String password) throws DataAccessException {
        String sql = "SELECT username FROM user WHERE username = ? AND password = ?;";
        ResultSet rs = null;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if(rs.getString("username") != null)
                return true;
        } catch (SQLException e){
            throw new DataAccessException("User not found");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }

    /**
     * Inserts a User object into the database
     * @param user A user object
     * @throws DataAccessException
     */
    public void insert(User user) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO user (username, password, email, firstName, lastName, " +
                "gender, personID) VALUES(?,?,?,?,?,?,?)";
        if(!user.getGender().toLowerCase(Locale.ROOT).equals("f") && !user.getGender().toLowerCase(Locale.ROOT).equals("m"))
            throw new DataAccessException("Gender not in correct format");
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("error: Invalid entry, could not reregister the user");
        }
    }

    /**
     * Finds a User in the database that has the same username
     * @param username A username
     * @return A User with the username
     * @throws DataAccessException
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * A function that deletes a user from the database
     * @param username
     */
    public void deleteUser(String username) {
        String sql1 = "DELETE FROM event WHERE associatedUsername = ?;";
        String sql2 = "DELETE FROM person WHERE associatedUsername = ?;";
        String sql3 = "DELETE FROM user WHERE username = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql1);
            stmt.setString(1, username);
            stmt.executeUpdate();
            PreparedStatement stmt1 = conn.prepareStatement(sql2);
            stmt1.setString(1, username);
            stmt1.executeUpdate();
            PreparedStatement stmt2 = conn.prepareStatement(sql3);
            stmt2.setString(1, username);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        /**
     * Deletes data from user table
     * @return boolean to signify if the action was successful
     * @throws DataAccessException
     */
    public boolean clear() throws DataAccessException {
        return Database.clear("user", conn);
    }
}
