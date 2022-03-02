package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import Data.FillData;

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
    FillService fill;
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
    public RegisterService(RegisterRequest req){
        this.req = req;
        db = new Database();
        try{
            conn = ServicePack.createConnection(db);
            ud = new UserDAO(conn);
            pd = new PersonDAO(conn);
            ed = new EventDAO(conn);
            FillService fill = new FillService();
            fillData = fill.getFillData();
            User user = createUser();
            ud.insert(user);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public void registerRun(){
        try{
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

    public User createUser(){
        User user = new User(req.getUsername(), req.getPassword(), req.getEmail(), req.getFirstName(), req.getLastName(), req.getGender(), UUID.randomUUID().toString());
        return user;
    }
}
