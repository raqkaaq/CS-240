package Result;

import Model.Event;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A class that models the event result json
 */
public class EventResult extends Result{
    /**
     * A list of events
     */
    private List<Event> data;

    /**
     * A constructor that creates the failed result
     * @param message
     */
    public EventResult(String message) {
        super(message, false);
    }

    /**
     * A constructor that creates the success result
     * @param event
     */
    public EventResult(List<Event> event) {
        super(null, true);
        this.data = event;
    }

    public List<Event> getEvent() {
        return data;
    }

    public Set<Event> getDataAsSet() {
        return new HashSet(this.data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventResult that = (EventResult) o;
        return Objects.equals(getEvent(), that.getEvent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEvent());
    }
}
