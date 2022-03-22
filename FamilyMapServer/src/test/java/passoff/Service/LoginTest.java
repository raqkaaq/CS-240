package passoff.Service;

import DataAccess.*;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import org.junit.jupiter.api.*;

import java.sql.Connection;

public class LoginTest {
    private LoginService log;
    private Connection conn;
    Database db;
    User u = new User("raqkaaq", "raqkaaq", "email@gmail.com", "Raqkaaq", "Raqkaaq", "m", "1234");

    @BeforeEach
    public void start() throws DataAccessException{
        db = new Database();
        db.openConnection();
        conn = db.getConnection();
        db.clearTables();
    }
    @AfterEach
    public void end() throws DataAccessException {
        log = null;
    }
    @Test
    @DisplayName("Testing the login service accept")
    public void goodLoginTest() throws DataAccessException {
        UserDAO ud = new UserDAO(conn);
        Assertions.assertDoesNotThrow(() -> ud.insert(u));
        db.closeConnection(true);
        LoginRequest login = new LoginRequest(u.getUsername(), u.getPassword());
        LoginResult expected = new LoginResult("",u.getUsername(), u.getPersonID());
        log = new LoginService(login);
        LoginResult actual = log.post();
        expected.setAuthToken(actual.getAuthToken());
        Assertions.assertTrue(expected.equals(actual));
    }

    @Test
    @DisplayName("Testing the login service rejection")
    public void badLoginTest() throws DataAccessException {
        UserDAO ud = new UserDAO(conn);
        Assertions.assertDoesNotThrow(() -> ud.insert(u));
        db.closeConnection(true);
        LoginRequest login = new LoginRequest("cheese", "nope");
        LoginResult expected = new LoginResult("error: Invalid username or password");
        log = new LoginService(login);
        LoginResult actual = log.post();
        Assertions.assertTrue(expected.equals(actual));
    }
}
