package Service;

import Request.FillRequest;
import Result.FillResult;

/**
 * A class that handles a fill request
 */
public class FillService {
    /**
     * A fill result
     */
    private FillResult fill;

    /**
     * A constructor that takes a fill request
     * @param filler
     */
    public FillService(FillRequest filler){
    }

    /**
     * A function that returns the fill result
     * @return a fill result
     */
    public FillResult post(){
        return this.fill;
    }
    private void fillGenerations(){}
    private void fillEvents(){}
}
