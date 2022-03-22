package passoff.Service;

import DataAccess.DataAccessException;
import Model.User;
import Request.RegisterRequest;
import Service.ClearService;
import Service.RegisterService;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

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
    public void testingConstructor() throws DataAccessException, FileNotFoundException {
        RegisterService reg = new RegisterService(req);
        reg.openDatabase();
        Assertions.assertDoesNotThrow(() -> reg.stopConnection());
    }

    @Test
    @DisplayName("Testing run")
    public void testRun() throws DataAccessException {
        RegisterService reg = new RegisterService(req);
        reg.registerRun();
        System.out.println(reg.getRegister());
    }

}
