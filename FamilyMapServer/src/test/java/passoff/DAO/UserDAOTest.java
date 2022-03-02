package passoff.DAO;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;

public class UserDAOTest {
    Database data = new Database();
    User u = new User("raqkaaq", "raqkaaq", "email@gmail.com", "Raqkaaq", "Raqkaaq", "m", "1234");
    User u2 = new User("raqkaaq", "raqkaaq", "mail@gmail.com", "Raqkaaq", "Raqkaaq", "m", "1234");
    Person p = new Person("1234", "raqkaaq", "Raqkaaq", "Raqkaaq", "m", "3245", "2342", "3409");
    Event e = new Event("I", "raqkaaq", "a",123, 123,"test", "of", "events", 2022);

    Connection conn;
    @BeforeEach
    public void setup() throws DataAccessException {
        data.openConnection();
        conn = data.getConnection();
    }

    @AfterEach
    public void close() throws DataAccessException{
        data.clearTables();
        data.closeConnection(true);
    }
    /**
     * Allows for testing as to whether the insert function executes
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Insert a user without error")
    public void insertCorrectUser()  {
        UserDAO dao = new UserDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(u));
    }

    /**
     * Asserts that inserting a person with the same primary key throws an error
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Insert 2 users with same person id")
    public void insert2Users() {
        UserDAO dao = new UserDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(u));
        Assertions.assertThrows(DataAccessException.class, () -> dao.insert(u2));
    }

    /**
     * Asserts that the database finds the correct person and does not throw and error
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Find a user in database")
    public void findUser() throws DataAccessException {
        UserDAO dao = new UserDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(u));
        Assertions.assertDoesNotThrow(() -> dao.find("raqkaaq"));
        Assertions.assertTrue(u.equals(dao.find("raqkaaq")));
    }

    /**
     * Asserts that you cannot find a person that is not in the table
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Fail to find user that doesn't exist")
    public void findNoUser() throws DataAccessException {
        UserDAO dao = new UserDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(u));
        Assertions.assertDoesNotThrow(() -> dao.find("3452"));
        Assertions.assertFalse(u.equals(dao.find("3432")));
    }

    /**
     * Asserts that the clear function acts as expected
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Test clear database")
    public void clearUser() throws DataAccessException {
        UserDAO dao = new UserDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(u));
        Assertions.assertDoesNotThrow(dao::clear);
        Assertions.assertFalse(u.equals(dao.find("raqkaaq")));
    }

    /**
     *
     */
    @Test
    @DisplayName("Delete User")
    public void deleteUser() throws DataAccessException {
        UserDAO dao = new UserDAO(conn);
        EventDAO ed = new EventDAO(conn);
        PersonDAO pd = new PersonDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(u));
        Assertions.assertDoesNotThrow(() -> ed.insert(e));
        Assertions.assertDoesNotThrow(() -> pd.insert(p));
        dao.deleteUser(u.getUsername());
        data.closeConnection(true);
        data.openConnection();
        conn = data.getConnection();
        UserDAO dao2 = new UserDAO(conn);
        EventDAO ed2 = new EventDAO(conn);
        PersonDAO pd2 = new PersonDAO(conn);
        User us = dao2.find(u.getUsername());
        Event eve = ed2.find(e.getEventID());
        Person pers = pd2.find(p.getPersonID());
        Assertions.assertNull(us);
        Assertions.assertNull(eve);
        Assertions.assertNull(pers);
    }
}
