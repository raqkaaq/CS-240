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
import Request.RegisterRequest;
import Service.ClearService;
import Service.FillService;
import Service.RegisterService;
import Service.ServicePack;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;
import java.sql.Connection;

public class RegisterTest {
    private ClearService clear;
    User u = new User("raqkaaq", "raqkaaq", "email@gmail.com", "Raqkaaq", "Raqkaaq", "m", "1234");
    RegisterRequest req = new RegisterRequest("raqkaaq", "raqkaaq", "raqkaaq@gmail.com", "Raqkaaq", "Raqkaaq", "m");

    @BeforeEach
    public void start() throws DataAccessException {
        clear = new ClearService();
    }

    @AfterEach
    public void end() {
        clear.clearTables();
    }

    @Test
    @DisplayName("Test of create User")
    public void testCreateUser(){
        RegisterService reg = new RegisterService();
        User user = reg.createUser(req);
        System.out.println(user);
    }
    @Test
    @DisplayName("Testing Constructor")
    public void testingConstructor(){
        RegisterService reg = new RegisterService(req);
        Assertions.assertDoesNotThrow(() -> reg.stopConnection());
    }

    @Test
    @DisplayName("Testing run")
    public void testRun(){
        RegisterService reg = new RegisterService(req);
        reg.registerRun();
        System.out.println(reg.getRegister());
    }

}
