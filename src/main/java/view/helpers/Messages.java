package view.helpers;

/**
 * Enum to hold various messages for the Stock Data Viewer application.
 */
public enum Messages {

    /**
     * Help message to guide the user on how to use the application.
     */
    HELP_MESSAGE("""
            Help message:
            - Enter a stock symbol to fetch and display its data.
            """),

    /**
     * Welcome message to greet the user and provide basic instructions.
     */
    WELCOME_MESSAGE("""
            Welcome to the Stock Data Viewer!
            You can fetch and view stock data by entering
            stock symbols.
            Example input:
            - Enter 'AAPL' to view data for Apple Inc.
            - Enter 'GOOGL' to view data for Alphabet Inc.
            Your stock data will be returned based on the
            last 100 traded days.
            """);

    /**
     * The message text associated with the enum constant.
     */
    private final String message;

    /**
     * Constructor for Messages enum.
     *
     * @param message the message text to be associated with the enum constant
     */
    Messages(String message) {
        this.message = message;
    }

    /**
     * Retrieves the message text.
     *
     * @return the message text
     */
    public String getMessage() {
        return message;
    }
}
