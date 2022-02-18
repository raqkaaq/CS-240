package Result;

import Model.Event;

import java.util.List;
/**
 * A class that models the event result json
 */
public class EventResult extends Result{
    /**
     * A list of events
     */
    private List<Event> event;

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
        this.event = event;
    }

    public List<Event> getEvent() {
        return event;
    }
}
