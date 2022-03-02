package Result;

import java.util.Objects;

/**
 * A class that models the load json result
 */
public class LoadResult extends Result {
    /**
     * A constructor that creates the loadResult
     *
     * @param message
     * @param success
     */
    public LoadResult(String message, boolean success) {
        super(message, success);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoadResult that = (LoadResult) o;
        return Objects.equals(getMessage(), that.getMessage()) && getSuccess() == that.getSuccess();
    }
}
