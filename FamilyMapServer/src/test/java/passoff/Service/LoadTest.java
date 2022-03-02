package passoff.Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.EventResult;
import Result.LoadResult;
import Service.EventService;
import Service.LoadService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class LoadTest {
    Database data = new Database();
    List<User> ul;
    List<Person> pl;
    List<Event> el;
    UserDAO ud;
    EventDAO ed;
    PersonDAO pd;
    User u = new User("raqkaaq", "raqkaaq", "email@gmail.com", "Raqkaaq", "Raqkaaq", "m", "1234");
    User u2 = new User("raqkaaq1", "raqkaaq", "mail@gmail.com", "Raqkaaq", "Raqkaaq", "m", "12345");
    Person p = new Person("1234", "raqkaaq", "Raqkaaq", "Raqkaaq", "m", "3245", "2342", "3409");
    Person p2 = new Person("12345", "raqkaaq1", "Raqkaaq2", "Raqkaaq2", "m", "3245", "2342", "3409");
    Event e = new Event("I", "raqkaaq", "a",123, 123,"test", "of", "events", 2022);
    Event e2 = new Event("1", "raqkaaq1", "a",123, 123,"test", "of", "different events", 2022);
    AuthToken a = new AuthToken("cheese", "am");
    String auth = "cheesit";


    Connection conn;
    @BeforeEach
    public void setup() throws DataAccessException {
        conn = data.openConnection();
        ud = new UserDAO(conn);
        ed = new EventDAO(conn);
        pd = new PersonDAO(conn);
        data.clearTables();
        data.closeConnection(true);
        ul = new ArrayList<>();
        pl = new ArrayList<>();
        el = new ArrayList<>();
        ul.add(u);
        ul.add(u2);
        pl.add(p);
        pl.add(p2);
        el.add(e);
        el.add(e2);
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
    @DisplayName("Insert all")
    public void insertAll()  {
        LoadRequest req = new LoadRequest(ul, pl, el);
        LoadService load = new LoadService(req);
        LoadResult result = new LoadResult("Successfully added 2 users, 2 persons, and 2 events to the database", true);
        LoadResult actual = load.post();
        Assertions.assertTrue(actual.equals(result));
    }
}
