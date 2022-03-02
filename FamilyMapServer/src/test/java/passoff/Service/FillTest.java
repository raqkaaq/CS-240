package passoff.Service;

import Data.LocationData;
import Data.Name;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Service.ClearService;
import Service.FillService;
import org.junit.jupiter.api.*;

import java.sql.Connection;

public class FillTest {
    private ClearService clear;
    FillService fill;
    User u = new User("raqkaaq", "raqkaaq", "email@gmail.com", "Raqkaaq", "Raqkaaq", "m", "1234");


    @BeforeEach
    public void start() throws DataAccessException {
        clear = new ClearService();
        FillRequest req = new FillRequest(u.getUsername(), "3");
        try{
            fill = new FillService(req);
        } //tests constructor
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void end() {
        fill = null;
    }

    @Test
    @DisplayName("Testing Generate Random Name")
    public void generateName() throws DataAccessException {
        Assertions.assertDoesNotThrow(() -> fill.getRandomName("m"));
        Assertions.assertDoesNotThrow(() -> fill.getRandomName("f"));
        Assertions.assertThrows(DataAccessException.class, () -> fill.getRandomName("d"));
        try {
            Name name = fill.getRandomName("m");
            System.out.println(name.getFirstName() + " " + name.getLastName());
            name = fill.getRandomName("f");
            System.out.println(name.getFirstName() + " " + name.getLastName());

        } catch (DataAccessException e) {
            e.getMessage();
        }
    }

    @Test
    @DisplayName("Testing Locations")
    public void locationTest() {
        Assertions.assertDoesNotThrow(() -> fill.getRandomLocation());
        try {
            LocationData location = fill.getRandomLocation();
            System.out.println(location.getLatitude() + "  " + location.getLongitude() + "  " + location.getCountry() + "  " + location.getCity());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    @DisplayName("Testing remake")
    public void remakeTest() {
        try {
            System.out.println("Before:");
            System.out.println(u.toString());
            User u2 = fill.remake(u);
            System.out.println("After:");
            System.out.println(u2.toString());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        Assertions.assertDoesNotThrow(() -> fill.remake(u));
    }

    @Test
    @DisplayName("Testing make parents")
    public void makeParents() {
        Assertions.assertDoesNotThrow(() -> fill.remake(u));
        Assertions.assertDoesNotThrow(() -> System.out.println(fill.makePerson(u).toString()));
        Assertions.assertDoesNotThrow(() -> System.out.println(fill.makeFather(fill.makePerson(u)).toString()));
        Assertions.assertDoesNotThrow(() -> System.out.println(fill.makeMother(fill.makePerson(u)).toString()));
    }

    @Test
    @DisplayName("Testing updating spouses")
    public void updateSpouseTest(){
        try{
            User user = fill.remake(u);
            Person p = new Person(user);
            Person mother = fill.makeMother(p);
            Person father = fill.makeFather(p);
            fill.updateSpouse(father, mother, 1932);
            System.out.println(father.toString());
            System.out.println(mother.toString());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test fill generations")
    public void testRun() throws DataAccessException {
        fill = null;
        Database db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();
        UserDAO dao = new UserDAO(conn);
        dao.clear();
        dao.insert(u);
        db.closeConnection(true);
        FillRequest req = new FillRequest(u.getUsername(), "3");
        fill = new FillService(req);
        fill.fillRun();
        System.out.println(fill.getFill().toString());
    }

}
