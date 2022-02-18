package Service;

import Request.LoginRequest;
import Result.LoginResult;

/**
 * A class that handles the login result
 */
public class LoginService {
    /**
     * A login result
     */
    private LoginResult login;

    /**
     * A constructor that takes a login request
     * @param req
     */
    public LoginService(LoginRequest req){

    }

    /**
     * A function that returns a login result
     * @return a login result
     */
    public LoginResult post(){
        return this.login;
    }
}
