package Service;

import Request.LoadRequest;
import Result.LoadResult;

/**
 * A class that handles the load requests
 */
public class LoadService {
    /**
     * A load result
     */
    private LoadResult load;

    /**
     * A constructor that takes a load request
     * @param req
     */
    public LoadService(LoadRequest req){}

    /**
     * A function that returns a load result
     * @return a load result
     */
    public LoadResult LoadResult(){
        return this.load;
    }
}
