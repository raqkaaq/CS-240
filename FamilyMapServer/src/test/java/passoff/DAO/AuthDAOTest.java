package passoff.DAO;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import org.junit.jupiter.api.*;

import java.sql.Connection;

public class AuthDAOTest {
    Database data = new Database();
    AuthToken a = new AuthToken("User", "cheese");
    AuthToken a2 = new AuthToken("User", "cheese2");

    Connection conn;
    @BeforeEach
    public void setup() throws DataAccessException {
        conn = data.openConnection();
    }

    @AfterEach
    public void close() throws DataAccessException{
        data.closeConnection(false);
    }

    /**
     * Allows for testing as to whether the insert function executes
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Insert a AuthTokenDAOthout error")
    public void insertCorrectAuthToken() {
        AuthTokenDAO dao = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(a));
    }

    /**
     * Asserts that inserting a AuthToken with the same primary key throws an error
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Insert 2 people with same AuthTokenDAO")
    public void insert2AuthToken () {
        AuthTokenDAO dao = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(a));
        Assertions.assertThrows(DataAccessException.class, () -> dao.insert(a2));
    }

    /**
     * Asserts that the database finds the correct AuthToken does not throw and error
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Find a AuthTokenDAO database")
    public void findAuthTokenDAO() throws DataAccessException {
        AuthTokenDAO dao = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(a));
        Assertions.assertDoesNotThrow(() -> dao.find("User"));
        Assertions.assertTrue(a.equals(dao.find("User")));
    }

    /**
     * Asserts that you cannot find a AuthTokenDAOat is not in the table
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Fail to find AuthTokenDAOat doesn't exist")
    public void findNoAuthTokenDAO() throws DataAccessException {
        AuthTokenDAO dao = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(a));
        Assertions.assertDoesNotThrow(() -> dao.find("3452"));
        Assertions.assertFalse(a.equals(dao.find("3432")));
    }

    /**
     * Asserts that the clear function acts as expected
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Test clear database")
    public void clearAuthTokenDAO() throws DataAccessException {
        AuthTokenDAO dao = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(a));
        Assertions.assertDoesNotThrow(dao::clear);
        Assertions.assertFalse(a.equals(dao.find("User")));
    }
}
