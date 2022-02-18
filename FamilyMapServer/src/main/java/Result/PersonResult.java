package Result;

import Model.Person;

import java.util.List;

/**
 * A class that models the person json result
 */
public class PersonResult extends Result{
    /**
     * A list of person objects
     */
    private List<Person> persons;

    /**
     * A constructor that creates a person result success
     * @param persons
     */
    public PersonResult(List<Person> persons) {
        super(null, true);
        this.persons = persons;
    }

    /**
     *  A class that creates a person failed result
     * @param message
     */
    public PersonResult(String message) {
        super(message, false);
    }

    public List<Person> getPersons() {
        return persons;
    }
}
