package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import Data.FillData;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.UUID;

/**
 * A class that handles the register requests
 */
public class RegisterService {
    /**
     * A register result
     */
    private RegisterResult register;
    private RegisterRequest req;
    FillData fillData;
    Database db;
    Connection conn;
    UserDAO ud;
    PersonDAO pd;
    EventDAO ed;

    /**
     * A constructor that takes a register request
     * @param req a register request
     */
    public RegisterService(RegisterRequest req) throws DataAccessException {
        this.req = req;
        db = new Database();
        try{
            conn = ServicePack.createConnection(db);
            ud = new UserDAO(conn);
            pd = new PersonDAO(conn);
            ed = new EventDAO(conn);
            FillService fill = new FillService();
            fillData = fill.getFillData();
            User user = createUser(req);
            ud.insert(user);
            ServicePack.closeConnection(db, true);
        } catch (DataAccessException e) {
            ServicePack.closeConnection(db, false);
        }
    }

    public RegisterService() {}

    public void registerRun(){
        try{
            conn  = ServicePack.createConnection(db);
            ud = new UserDAO(conn);
            pd = new PersonDAO(conn);
            ed = new EventDAO(conn);
            User user = ud.find(req.getUsername());
            Person p = new Person(user);
            ed.insert(ServicePack.generateUserEvents(user, fillData));
            ServicePack.fillGenerations(p, 4, 1980, pd, ed, fillData);
            pd.insert(p);
            AuthToken auth = new AuthToken(UUID.randomUUID().toString(), user.getUsername());
            AuthTokenDAO authd = new AuthTokenDAO(conn);
            authd.insert(auth);
            register = new RegisterResult(auth.getAuthToken(), user.getUsername(), user.getPersonID());
            ServicePack.closeConnection(db, true);
        } catch (DataAccessException e) {
            register = new RegisterResult(e.getMessage());
            try{
                ServicePack.closeConnection(db, false);
            } catch (DataAccessException ex) {
                register = new RegisterResult(ex.getMessage());
            }
        }
    }

    /**
     * A function that returns a register result
     * @return a register result
     */
    public RegisterResult post(){
        return this.register;
    }

    public User createUser(RegisterRequest req){
        User user = new User(req.getUsername(), req.getPassword(), req.getEmail(), req.getFirstName(), req.getLastName(), req.getGender(), UUID.randomUUID().toString());
        return user;
    }
    public void openDatabase() throws DataAccessException, FileNotFoundException {
        conn = ServicePack.createConnection(db);
        ud = new UserDAO(conn);
        pd = new PersonDAO(conn);
        ed = new EventDAO(conn);
        fillData = new FillData();
    }

    public void stopConnection() throws DataAccessException {
        ServicePack.closeConnection(db, true);
    }

    public RegisterResult getRegister() {
        return register;
    }
}
