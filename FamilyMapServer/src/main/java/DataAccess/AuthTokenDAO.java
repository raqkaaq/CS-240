package DataAccess;

import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object that takes a AuthToken and passes it to the database, or finds an AuthToken and returns it from the database
 */
public class AuthTokenDAO {
    private final Connection conn;

    /**
     * Accepts an sql connection
     * @param conn sql connection value
     */
    public AuthTokenDAO(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Function that returns true if an authtoken is found in the database, false otherwise
     * @param authToken
     * @return true if authtoken is in database, false otherwise
     * @throws DataAccessException
     */
    public boolean validateAuthToken(String authToken) throws DataAccessException {
        String sql = "SELECT username FROM authtoken WHERE authtoken = ?;";
        ResultSet rs = null;
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if(rs.getString("username") != null)
                return true;
        } catch (SQLException e){
           // throw new DataAccessException("Token not found");
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
     * Takes an AuthToken and inserts the values into the database
     * @param authToken
     * @throws DataAccessException
     */
    public void insert(AuthToken authToken) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO authtoken (authToken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUserName());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Takes an Authorization token and searches for it in the database
     * @param authTokenID
     * @return A new AuthToken found in the database
     * @throws DataAccessException
     */
    public AuthToken find(String authTokenID) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM authtoken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authTokenID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding authToken");
        } finally {
            if(rs != null) {
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
     * Clears data in authtoken table
     * @return true if successful, else false
     * @throws DataAccessException
     */
    public boolean clear() throws DataAccessException {
        return Database.clear("authtoken", conn);
    }
}
