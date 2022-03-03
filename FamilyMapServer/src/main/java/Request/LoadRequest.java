package Request;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.List;
import java.util.Objects;

/**
 * A class that models the Load json request
 */
public class LoadRequest {
    /**
     * A list of users
     */
    List<User> users;
    /**
     * A list of people
     */
    List<Person> persons;
    /**
     * A list of events
     */
    List<Event> events;

    /**
     * A constructor that creates a LoadRequest
     * @param users
     * @param person
     * @param events
     */
    public LoadRequest(List<User> users, List<Person> person, List<Event> events) {
        this.users = users;
        this.persons = person;
        this.events = events;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Person> getPerson() {
        return persons;
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadRequest that = (LoadRequest) o;
        return Objects.equals(users, that.users) && Objects.equals(persons, that.persons) && Objects.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, persons, events);
    }
}
