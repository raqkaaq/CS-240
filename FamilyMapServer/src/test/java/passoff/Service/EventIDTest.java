package passoff.Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Request.EventIDRequest;
import Result.EventIDResult;
import Result.EventResult;
import Service.EventIDService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class EventIDTest {
    private EventIDService event;
    private Connection conn;
    Database db;
    Event e = new Event("I", "raqkaaq", "a",123, 123,"test", "of", "events", 2022);
    Event e2 = new Event("I2", "am", "a",123, 123,"test", "of", "different events", 2022);
    AuthToken a = new AuthToken("cheese", "raqkaaq");
    @BeforeEach
    public void start() throws DataAccessException{
        db = new Database();
        db.openConnection();
        conn = db.getConnection();
    }
    @AfterEach
    public void end() throws DataAccessException {
        event = null;
    }
    @Test
    @DisplayName("Testing the EventId service with correct parameters")
    public void goodEventIDTest() throws DataAccessException {
        db.clearTables();
        EventDAO ed = new EventDAO(conn);
        AuthTokenDAO authd = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> ed.insert(e));
        Assertions.assertDoesNotThrow(() -> authd.insert(a));
        db.closeConnection(true);
        EventIDRequest req = new EventIDRequest(e.getEventID(),a.getAuthToken());
        EventIDResult expected = new EventIDResult(e.getAssociatedUsername(), e.getEventID(), e.getPersonID(),e.getLatitude(), e.getLongitude(), e.getCountry(), e.getCity(), e.getEventType(), e.getYear());
        event = new EventIDService(req);
        EventIDResult actual = event.post();
        Assertions.assertTrue(expected.equals(actual));
    }
    @Test
    @DisplayName("Testing the EventId service with wrong authorization")
    public void wrongAuthorizationTest() throws DataAccessException {
        db.clearTables();
        EventDAO ed = new EventDAO(conn);
        AuthTokenDAO authd = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> ed.insert(e));
        Assertions.assertDoesNotThrow(() -> authd.insert(a));
        db.closeConnection(true);
        EventIDRequest req = new EventIDRequest(e.getEventID(),"not right");
        EventIDResult expected = new EventIDResult("error Invalid authorization: You do not have access to this resource");
        event = new EventIDService(req);
        EventIDResult actual = event.post();
        Assertions.assertTrue(expected.equals(actual));
    }
    @Test
    @DisplayName("Testing the EventId service with wrong event id")
    public void wrongEventIDTest() throws DataAccessException {
        db.clearTables();
        EventDAO ed = new EventDAO(conn);
        AuthTokenDAO authd = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> ed.insert(e));
        Assertions.assertDoesNotThrow(() -> authd.insert(a));
        db.closeConnection(true);
        EventIDRequest req = new EventIDRequest("whoops",a.getAuthToken());
        EventIDResult expected = new EventIDResult("error Invalid event id");
        event = new EventIDService(req);
        EventIDResult actual = event.post();
        Assertions.assertTrue(expected.equals(actual));
    }
    @Test
    @DisplayName("Testing the EventId service with wrong user")
    public void wrongUserTest() throws DataAccessException {
        db.clearTables();
        EventDAO ed = new EventDAO(conn);
        AuthTokenDAO authd = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> ed.insert(e));
        Assertions.assertDoesNotThrow(() -> ed.insert(e2));
        Assertions.assertDoesNotThrow(() -> authd.insert(a));
        db.closeConnection(true);
        EventIDRequest req = new EventIDRequest(e2.getEventID(),a.getAuthToken());
        EventIDResult expected = new EventIDResult("error Invalid authorization: You do not have access to this event");
        event = new EventIDService(req);
        EventIDResult actual = event.post();
        Assertions.assertTrue(expected.equals(actual));
    }


}