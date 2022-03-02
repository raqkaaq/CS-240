package passoff.Service;

import Data.LocationData;
import Data.Name;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import DataAccess.UserDAO;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Service.ClearService;
import Service.FillService;
import Service.ServicePack;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;
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
        Assertions.assertDoesNotThrow(() -> ServicePack.getRandomName("m", fill.getFillData()));
        Assertions.assertDoesNotThrow(() -> ServicePack.getRandomName("f", fill.getFillData()));
        Assertions.assertThrows(DataAccessException.class, () -> ServicePack.getRandomName("d", fill.getFillData()));
        try {
            Name name = ServicePack.getRandomName("m", fill.getFillData());
            System.out.println(name.getFirstName() + " " + name.getLastName());
            name = ServicePack.getRandomName("f", fill.getFillData());
            System.out.println(name.getFirstName() + " " + name.getLastName());

        } catch (DataAccessException e) {
            e.getMessage();
        }
    }

    @Test
    @DisplayName("Testing Locations")
    public void locationTest() {
        Assertions.assertDoesNotThrow(() -> ServicePack.getRandomLocation(fill.getFillData()));
        try {
            LocationData location = ServicePack.getRandomLocation(fill.getFillData());
            System.out.println(location.getLatitude() + "  " + location.getLongitude() + "  " + location.getCountry() + "  " + location.getCity());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    @DisplayName("Testing remake")
    public void remakeTest() throws DataAccessException {
        try {
            System.out.println("Before:");
            System.out.println(u.toString());
            User u2 = fill.remake(u);
            fill.closeDatabase();
            System.out.println("After:");
            System.out.println(u2.toString());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        FillRequest req = new FillRequest(u.getUsername(), "3");
        fill = new FillService(req);
        Assertions.assertDoesNotThrow(() -> fill.remake(u));
        fill.closeDatabase();
    }

    @Test
    @DisplayName("Testing make parents")
    public void makeParents() throws DataAccessException {
        Assertions.assertDoesNotThrow(() -> fill.remake(u));
        fill.closeDatabase();
        Assertions.assertDoesNotThrow(() -> System.out.println(fill.makePerson(u).toString()));
        Assertions.assertDoesNotThrow(() -> System.out.println(ServicePack.makeFather(fill.makePerson(u), fill.getFillData()).toString()));
        Assertions.assertDoesNotThrow(() -> System.out.println(ServicePack.makeMother(fill.makePerson(u), fill.getFillData()).toString()));
    }

    @Test
    @DisplayName("Testing updating spouses")
    public void updateSpouseTest(){
        try{
            User user = fill.remake(u);
            Person p = new Person(user);
            Person mother = ServicePack.makeMother(p, fill.getFillData());
            Person father = ServicePack.makeFather(p, fill.getFillData());
            ServicePack.updateSpouse(father, mother, 1932, fill.getEd(), fill.getFillData());
            fill.closeDatabase();
            System.out.println(father.toString());
            System.out.println(mother.toString());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test fill 1 generations")
    public void testRun1() throws DataAccessException {
        fill = null;
        Database db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();
        UserDAO dao = new UserDAO(conn);
        dao.clear();
        dao.insert(u);
        db.closeConnection(true);
        FillRequest req = new FillRequest(u.getUsername(), "1");
        fill = new FillService(req);
        fill.fillRun();
        System.out.println(fill.getFill().toString());
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    @DisplayName("Test fill 3 generations")
    public void testRun3() throws DataAccessException {
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
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

}
