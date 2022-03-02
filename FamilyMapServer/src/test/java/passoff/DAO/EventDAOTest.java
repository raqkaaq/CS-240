package passoff.DAO;

import Data.FillData;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.Event;
import Model.User;
import Result.ClearResult;
import Service.ClearService;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventDAOTest {
    Database data = new Database();
    Event e = new Event("I", "am", "a",123, 123,"test", "of", "events", 2022);
    Event e2 = new Event("I", "am", "a",123, 123,"test", "of", "different events", 2022);
    Event e3 = new Event("2", "am", "a",123, 123,"test", "of", "different events", 2022);

    Connection conn;
    @BeforeEach
    public void setup() throws DataAccessException {
        conn = data.openConnection();
        data.clearTables();
    }

    @AfterEach
    public void close() throws DataAccessException{
        data.clearTables();
        data.closeConnection(false);
    }

    /**
     * Allows for testing as to whether the insert function executes
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Insert a Event without error")
    public void insertCorrectEvent()  {
        EventDAO dao = new EventDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(e));
    }

    /**
     * Asserts that inserting a Event with the same primary key throws an error
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Insert 2 people with same Event id")
    public void insert2Events() {
        EventDAO dao = new EventDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(e));
        Assertions.assertThrows(DataAccessException.class, () -> dao.insert(e2));
    }

    /**
     * Asserts that the database finds the correct Event and does not throw and error
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Find a Event in database")
    public void findEvent() throws DataAccessException {
        EventDAO dao = new EventDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(e));
        Assertions.assertDoesNotThrow(() -> dao.find("I"));
        Assertions.assertTrue(e.equals(dao.find("I")));
    }

    /**
     * Asserts that you cannot find a Event that is not in the table
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Fail to find Event that doesn't exist")
    public void findNoEvent() throws DataAccessException {
        EventDAO dao = new EventDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(e));
        Assertions.assertDoesNotThrow(() -> dao.find("3452"));
        Assertions.assertFalse(e.equals(dao.find("3432")));
    }

    /**
     * Asserts that the clear function acts as expected
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Test clear database")
    public void clearEvent() throws DataAccessException {
        EventDAO dao = new EventDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(e));
        Assertions.assertDoesNotThrow(dao::clear);
        Assertions.assertFalse(e.equals(dao.find("1234")));
    }

    @Test
    @DisplayName("Get all events")
    public void getAllEvents() throws DataAccessException {
        EventDAO dao = new EventDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(e));
        Assertions.assertDoesNotThrow(() -> dao.insert(e3));
        List<Event> list = new ArrayList<>();
        list.add(e);
        list.add(e3);
        List<Event> result = dao.getAllEvents("am");
        Assertions.assertTrue(Objects.equals(list, result));
    }
}
