package passoff.Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Result.ClearResult;
import Service.ClearService;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;
import java.sql.Connection;

public class ClearTest {
    private ClearService clear;
    private Connection conn;
    Database db;
    User u = new User("raqkaaq", "raqkaaq", "email@gmail.com", "Raqkaaq", "Raqkaaq", "m", "1234");
    User u2 = new User("raqkaaq2", "raqkaaq", "mail@gmail.com", "Raqkaaq", "Raqkaaq", "m", "1234");
    Event e = new Event("I", "am", "a",123, 123,"test", "of", "events", 2022);
    Event e2 = new Event("I2", "am", "a",123, 123,"test", "of", "different events", 2022);
    Person p = new Person("1234", "raqkaaq", "Raqkaaq", "Raqkaaq", "m", "3245", "2342", "3409");
    Person p2 = new Person("12345", "raqkaaq", "Raqkaaq2", "Raqkaaq2", "m", "3245", "2342", "3409");
    AuthToken a = new AuthToken("User", "cheese");
    AuthToken a2 = new AuthToken("User2", "cheese2");

    @BeforeEach
    public void start() throws DataAccessException{
        clear = new ClearService();
        db = new Database();
        db.openConnection();
        conn = db.getConnection();
    }
    @AfterEach
    public void end(){
        clear = null;
    }
    @Test
    @DisplayName("Testing the clear service")
    public void clearTest() throws DataAccessException {
        db.clearTables();
        UserDAO ud = new UserDAO(conn);
        PersonDAO pd = new PersonDAO(conn);
        EventDAO ed = new EventDAO(conn);
        AuthTokenDAO ad = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> ud.insert(u));
        Assertions.assertDoesNotThrow(() -> ud.insert(u2));
        Assertions.assertDoesNotThrow(() -> pd.insert(p));
        Assertions.assertDoesNotThrow(() -> pd.insert(p2));
        Assertions.assertDoesNotThrow(() -> ad.insert(a));
        Assertions.assertDoesNotThrow(() -> ad.insert(a2));
        Assertions.assertDoesNotThrow(() -> ed.insert(e));
        Assertions.assertDoesNotThrow(() -> ed.insert(e2));
        db.closeConnection(true);
        String response = "Clear successful";
        ClearResult result = clear.clearTables();
        Assertions.assertEquals(response, result.getMessage());
        int count = 0;
        db.openConnection();
        conn = db.getConnection();
        Assertions.assertEquals(count, db.count("user", conn) + db.count("event", conn) + db.count("person", conn) + db.count("authtoken", conn));
        db.closeConnection(true);
    }
}
