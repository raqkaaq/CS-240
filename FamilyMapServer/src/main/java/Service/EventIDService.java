package Service;

import Request.eventIDRequest;
import Result.EventIDResult;

/**
 * A class that handles the event id request
 */
public class EventIDService {
    /**
     * An event id result
     */
    private EventIDResult event;

    /**
     * A class that takes an event id request
     * @param req
     */
    public EventIDService(eventIDRequest req){}

    /**
     * A class that returns the result
     * @return an event id request
     */
    public EventIDResult post(){
        return this.event;
    }
}
