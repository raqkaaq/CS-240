package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.RegisterRequest;
import Result.RegisterResult;

/**
 * A class that handles the register requests
 */
public class RegisterService {
    /**
     * A register result
     */
    private RegisterResult register;

    /**
     * A constructor that takes a register request
     * @param req a register request
     */
    public RegisterService(RegisterRequest req){
        Database db = new Database();
        try{
            db.openConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * A function that returns a register result
     * @return a register result
     */
    public RegisterResult post(){
        return this.register;
    }
}
