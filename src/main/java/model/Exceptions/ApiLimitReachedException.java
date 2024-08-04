package model.Exceptions;

/**
 * Exception thrown when the API limit is reached.
 */
public class ApiLimitReachedException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public ApiLimitReachedException(String message) {
        super(message);
    }
}
