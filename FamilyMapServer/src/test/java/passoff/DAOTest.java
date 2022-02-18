package passoff;

import DataAccess.*;
import Model.*;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOTest {
    Database data = new Database();
    Person p = new Person("1234", "raqkaaq", "Raqkaaq", "Raqkaaq", "m", "3245", "2342", "3409");
    Person p2 = new Person("1234", "raqkaaq", "Raqkaaq2", "Raqkaaq2", "m", "3245", "2342", "3409");
    User u = new User("raqkaaq", "raqkaaq", "email@gmail.com", "Raqkaaq", "Raqkaaq", "m", "1234");
    User u2 = new User("raqkaaq", "raqkaaq", "mail@gmail.com", "Raqkaaq", "Raqkaaq", "m", "1234");
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
    @DisplayName("Insert a person without error")
    public void insertCorrectPerson()  {
        PersonDAO dao = new PersonDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(p));
    }

    /**
     * Asserts that inserting a person with the same primary key throws an error
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Insert 2 people with same person id")
    public void insert2Persons() {
            PersonDAO dao = new PersonDAO(conn);
            Assertions.assertDoesNotThrow(() -> dao.insert(p));
            Assertions.assertThrows(DataAccessException.class, () -> dao.insert(p2));
    }

    /**
     * Asserts that the database finds the correct person and does not throw and error
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Find a person in database")
    public void findPerson() throws DataAccessException {
        PersonDAO dao = new PersonDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(p));
        Assertions.assertDoesNotThrow(() -> dao.find("1234"));
        Assertions.assertTrue(p.equals(dao.find("1234")));
    }

    /**
     * Asserts that you cannot find a person that is not in the table
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Fail to find person that doesn't exist")
    public void findNoPerson() throws DataAccessException {
        PersonDAO dao = new PersonDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(p));
        Assertions.assertDoesNotThrow(() -> dao.find("3452"));
        Assertions.assertFalse(p.equals(dao.find("3432")));
    }

    /**
     * Asserts that the clear function acts as expected
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Test clear database")
    public void clearPerson() throws DataAccessException {
        PersonDAO dao = new PersonDAO(conn);
        Assertions.assertDoesNotThrow(() -> dao.insert(p));
        Assertions.assertDoesNotThrow(dao::clear);
        Assertions.assertFalse(p.equals(dao.find("1234")));
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


}
