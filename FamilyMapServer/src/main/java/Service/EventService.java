package Service;

import Result.EventResult;
import Request.eventIDRequest;

/**
 * A class that handles the event request
 */
public class EventService {
    /**
     * An event result
     */
    private EventResult events;

    /**
     * A constructor that takes and eventIdRequest
     * @param req
     */
    public EventService(eventIDRequest req){

    }

    /**
     * A function that returns an event result object
     * @return an event result
     */
    public EventResult post(){
        return this.events;
    }
}
