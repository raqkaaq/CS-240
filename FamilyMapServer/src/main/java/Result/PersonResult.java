package Result;

import Model.Person;

import java.util.List;
import java.util.Objects;

/**
 * A class that models the person json result
 */
public class PersonResult extends Result{
    /**
     * A list of person objects
     */
    private List<Person> data;

    /**
     * A constructor that creates a person result success
     * @param persons
     */
    public PersonResult(List<Person> persons) {
        super(null, true);
        this.data = persons;
    }

    /**
     *  A class that creates a person failed result
     * @param message
     */
    public PersonResult(String message) {
        super(message, false);
    }

    public List<Person> getPersons() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonResult that = (PersonResult) o;
        return Objects.equals(getPersons(), that.getPersons());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersons());
    }
}
