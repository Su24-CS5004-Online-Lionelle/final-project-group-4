package model.Exceptions;

/**
 * Exception thrown when an invalid stock symbol is encountered.
 */
public class InvalidStockSymbolException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidStockSymbolException(String message) {
        super(message);
    }
}
