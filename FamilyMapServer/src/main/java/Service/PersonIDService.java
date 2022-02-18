package Service;

import Request.personIDRequest;
import Result.PersonIDResult;
import Result.PersonResult;

/**
 * A class that handles the person id requests
 */
public class PersonIDService {
    /**
     * A person id result
     */
    private PersonIDResult person;

    /**
     * A constructor that takes a person id request
     * @param req
     */
    public PersonIDService(personIDRequest req){

    }

    /**
     * A person id result returner
     * @return a person id result
     */
    public PersonIDResult post(){
        return this.person;
    }
}
