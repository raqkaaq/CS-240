package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import java.sql.Connection;
import java.util.Objects;

/**
 * A class that handles the login result
 */
public class LoginService {
    /**
     * A login result
     */
    private LoginResult login;

    /**
     * A constructor that takes a login request and creates a login result
     * @param req
     */
    public LoginService(LoginRequest req){
        Database db = new Database();
        try {
            Connection conn = ServicePack.createConnection(db);
            UserDAO usd = new UserDAO(conn);
            AuthTokenDAO authd = new AuthTokenDAO(conn);
            User user = usd.find(req.getUsername());
            if(user != null) {
                if (Objects.equals(user.getPassword(), req.getPassword())) { //username and password are valid
                    AuthToken auth = new AuthToken(ServicePack.createRandomString(), user.getUsername());
                    authd.insert(auth);
                    login = new LoginResult(auth.getAuthToken(), auth.getUserName(), user.getPersonID());
                    ServicePack.closeConnection(db, true);
                }
            } else {
                login = new LoginResult("Invalid username or password");
                ServicePack.closeConnection(db,false);
            }
        } catch (DataAccessException e) {
            login = new LoginResult(e.getMessage());
            try{
                ServicePack.closeConnection(db,false);
            } catch (DataAccessException ex) {
                login = new LoginResult(ex.getMessage());
            }
        }
    }

    /**
     * A function that returns a login result
     * @return a login result
     */
    public LoginResult post(){
        return this.login;
    }
}
