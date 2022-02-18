package Service;

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

    }

    /**
     * A function that returns a register result
     * @return a register result
     */
    public RegisterResult post(){
        return this.register;
    }
}
