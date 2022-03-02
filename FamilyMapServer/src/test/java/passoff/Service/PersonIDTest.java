package passoff.Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Request.PersonIDRequest;
import Result.PersonIDResult;
import Service.PersonIDService;
import org.junit.jupiter.api.*;

import java.sql.Connection;

public class PersonIDTest {
    private PersonIDService person;
    private Connection conn;
    Database db;
    Person p = new Person("1234", "raqkaaq", "Raqkaaq", "Raqkaaq", "m", "3245", "2342", "3409");
    Person p2 = new Person("12345", "raqkaaq2", "Raqkaaq2", "Raqkaaq2", "m", "3245", "2342", "3409");
    AuthToken a = new AuthToken("cheese", "raqkaaq");
    @BeforeEach
    public void start() throws DataAccessException{
        db = new Database();
        db.openConnection();
        conn = db.getConnection();
        db.clearTables();
    }
    @AfterEach
    public void end() throws DataAccessException {
        person = null;
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }
    @Test
    @DisplayName("Testing the PersonId service with correct parameters")
    public void goodPersonIDTest() throws DataAccessException {
        db.clearTables();
        PersonDAO pd = new PersonDAO(conn);
        AuthTokenDAO authd = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> pd.insert(p));
        Assertions.assertDoesNotThrow(() -> authd.insert(a));
        db.closeConnection(true);
        PersonIDRequest req = new PersonIDRequest(p.getPersonID(),a.getAuthToken());
        PersonIDResult expected = new PersonIDResult(p.getAssociatedUsername(), p.getPersonID(), p.getFirstName(), p.getLastName(),p.getGender(), p.getFatherID(), p.getMotherID(), p.getSpouseID());
        person = new PersonIDService(req);
        PersonIDResult actual = person.post();
        Assertions.assertTrue(expected.equals(actual));
    }

    @Test
    @DisplayName("Testing the personId service with wrong authorization")
    public void invalidAuthTest() throws DataAccessException {
        db.clearTables();
        PersonDAO pd = new PersonDAO(conn);
        AuthTokenDAO authd = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> pd.insert(p));
        Assertions.assertDoesNotThrow(() -> authd.insert(a));
        db.closeConnection(true);
        PersonIDRequest req = new PersonIDRequest(p.getPersonID(),"cheese2");
        PersonIDResult expected = new PersonIDResult("Invalid authorization: You do not have access to this resource");
        person = new PersonIDService(req);
        PersonIDResult actual = person.post();
        Assertions.assertTrue(expected.equals(actual));
    }
    @Test
    @DisplayName("Testing the personId service with wrong personId")
    public void invalidIdTest() throws DataAccessException {
        db.clearTables();
        PersonDAO pd = new PersonDAO(conn);
        AuthTokenDAO authd = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> pd.insert(p));
        Assertions.assertDoesNotThrow(() -> authd.insert(a));
        db.closeConnection(true);
        PersonIDRequest req = new PersonIDRequest("hey", a.getAuthToken());
        PersonIDResult expected = new PersonIDResult("Invalid person id");
        person = new PersonIDService(req);
        PersonIDResult actual = person.post();
        Assertions.assertTrue(expected.equals(actual));
    }

    @Test
    @DisplayName("Testing the personId service with wrong user")
    public void invalidUserTest() throws DataAccessException {
        db.clearTables();
        PersonDAO pd = new PersonDAO(conn);
        AuthTokenDAO authd = new AuthTokenDAO(conn);
        Assertions.assertDoesNotThrow(() -> pd.insert(p));
        Assertions.assertDoesNotThrow(() -> pd.insert(p2));
        Assertions.assertDoesNotThrow(() -> authd.insert(a));
        db.closeConnection(true);
        PersonIDRequest req = new PersonIDRequest(p2.getPersonID(), a.getAuthToken());
        PersonIDResult expected = new PersonIDResult("Invalid authorization: You do not have access to this person");
        person = new PersonIDService(req);
        PersonIDResult actual = person.post();
        Assertions.assertTrue(expected.equals(actual));
    }
}