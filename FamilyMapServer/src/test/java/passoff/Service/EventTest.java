package passoff.Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Model.Event;
import Result.EventResult;
import Service.EventService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventTest {
    Database data = new Database();
    Event e = new Event("I", "am", "a",123, 123,"test", "of", "events", 2022);
    Event e2 = new Event("1", "am", "a",123, 123,"test", "of", "different events", 2022);
    Event e3 = new Event("2", "am", "a",123, 123,"test", "of", "different events", 2022);
    AuthToken a = new AuthToken("cheese", "am");
    String auth = "cheesit";


    Connection conn;
    @BeforeEach
    public void setup() throws DataAccessException {
        conn = data.openConnection();
        EventDAO dao = new EventDAO(conn);
        AuthTokenDAO authd= new AuthTokenDAO(conn);
        data.clearTables();
        Assertions.assertDoesNotThrow(() -> dao.insert(e));
        Assertions.assertDoesNotThrow(() -> dao.insert(e2));
        Assertions.assertDoesNotThrow(() -> dao.insert(e3));
        Assertions.assertDoesNotThrow(() -> authd.insert(a));
        data.closeConnection(true);
    }

    @AfterEach
    public void close() throws DataAccessException{
        data.openConnection();
        data.clearTables();
        data.closeConnection(true);
    }

    /**
     * Allows for testing as to whether the get all function executes
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Get all events for user")
    public void correctEvent()  {
        EventService serv = new EventService(a.getAuthToken());
        EventResult result = serv.post();
        List<Event> list = new ArrayList<>();
        list.add(e);
        list.add(e2);
        list.add(e3);
        EventResult actual = new EventResult(list);
        Assertions.assertTrue(actual.equals(result));
    }

    /**
     * Allows for testing as to whether the server catches an invalid auth
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Get all events for user with invalid auth")
    public void invalidAuthEvent()  {
        EventService serv = new EventService(auth);
        EventResult result = serv.post();
        EventResult actual = new EventResult("Invalid authorization: You do not have access to this resource");
        Assertions.assertTrue(actual.equals(result));
    }
}
