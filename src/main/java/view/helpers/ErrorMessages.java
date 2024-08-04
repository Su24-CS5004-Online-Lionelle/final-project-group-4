package view.helpers;

/**
 * Enum to hold various error messages for the Stock Data Viewer application.
 */
public enum ErrorMessages {

    /**
     * Error message for invalid stock symbol input.
     */
    INVALID_INPUT("Please enter a stock symbol."),

    /**
     * Error message for reaching the API limit.
     */
    API_LIMIT_REACHED("You have reached the 25 times daily limit, please Paypal $50 to Jubal"),

    /**
     * Error message for date out of range.
     */
    DATE_OUT_OF_RANGE("Selected date is outside the valid range: "),

    /**
     * Error message for no data found for the selected date.
     */
    DATA_NOT_FOUND("No stock data available for the selected date: "),

    /**
     * Error message for invalid date selection.
     */
    INVALID_DATE("Selected date is invalid. Please select a valid date."),

    /**
     * Error message for no data available for the specified symbol.
     */
    NO_DATA_AVAILABLE("No data available for the specified symbol: ");

    /**
     * The error message text associated with the enum constant.
     */
    private final String message;

    /**
     * Constructor for ErrorMessages enum.
     *
     * @param message the error message text to be associated with the enum constant
     */
    ErrorMessages(String message) {
        this.message = message;
    }

    /**
     * Retrieves the error message text.
     *
     * @return the error message text
     */
    public String getMessage() {
        return message;
    }
}
