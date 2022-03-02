package passoff.Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Result.PersonResult;
import Service.PersonService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PersonTest {
    Database data = new Database();
    Person p = new Person("1234", "raqkaaq", "Raqkaaq", "Raqkaaq", "m", "3245", "2342", "3409");
    Person p2 = new Person("12345", "raqkaaq", "Raqkaaq2", "Raqkaaq2", "m", "3245", "2342", "3409");
    Person p3 = new Person("12344", "raqkaaq", "Raqkaaq2", "Raqkaaq2", "m", "3245", "2342", "3409");
    AuthToken a = new AuthToken("cheese", "raqkaaq");
    String auth = "cheesit";


    Connection conn;
    @BeforeEach
    public void setup() throws DataAccessException {
        conn = data.openConnection();
        PersonDAO dao = new PersonDAO(conn);
        AuthTokenDAO authd= new AuthTokenDAO(conn);
        data.clearTables();
        Assertions.assertDoesNotThrow(() -> dao.insert(p));
        Assertions.assertDoesNotThrow(() -> dao.insert(p2));
        Assertions.assertDoesNotThrow(() -> dao.insert(p3));
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
    @DisplayName("Get all persons for user")
    public void correctPerson()  {
        PersonService serv = new PersonService(a.getAuthToken());
        PersonResult result = serv.post();
        List<Person> list = new ArrayList<>();
        list.add(p);
        list.add(p2);
        list.add(p3);
        PersonResult actual = new PersonResult(list);
        Assertions.assertTrue(actual.equals(result));
    }

    /**
     * Allows for testing as to whether the server catches an invalid auth
     * @throws DataAccessException
     */
    @Test
    @DisplayName("Get all persons for user with invalid auth")
    public void invalidAuthPerson()  {
        PersonService serv = new PersonService(auth);
        PersonResult result = serv.post();
        PersonResult actual = new PersonResult("Invalid authorization: You do not have access to this resource");
        Assertions.assertTrue(actual.equals(result));
    }
}
