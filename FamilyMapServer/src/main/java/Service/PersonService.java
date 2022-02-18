package Service;

import Request.personIDRequest;
import Result.PersonResult;

/**
 * A class that handles a person request
 */
public class PersonService {
    /**
     * A persons result
     */
    private PersonResult persons;

    /**
     * A constructor that takes a person id request
     * @param req
     */
    public PersonService(personIDRequest req){}

    /**
     * A function that returns a person result
     * @return a person result
     */
    public PersonResult post(){
        return this.persons;
    }
}
