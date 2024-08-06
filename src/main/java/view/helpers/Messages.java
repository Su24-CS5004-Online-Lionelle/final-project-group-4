package view.helpers;

/**
 * Enum to hold various messages for the Stock Data Viewer application.
 */
public enum Messages {

    /**
     * Help message to guide the user on how to use the application.
     */
    HELP_MESSAGE("""
            =======================================
            \u2605 HELP MESSAGE \u2605
            =======================================

            \u2192 1. Searching Stock Data:
               \u2022 Enter a stock symbol in the search field and click the
                 'Search' button to fetch and display its data.

            \u2192 2. Viewing Stock Data:
               \u2022 The fetched stock data will be displayed in a table.
               \u2022 Click on any date to see detailed information for that day.
               \u2022 Use the calendar icon \uD83D\uDCC5 to open the date picker and select
                 a date to view stock data for that specific date.
               \u2022 If you want to clear the selected date, click the 'X'
                 button on the calendar.
               \u2022 The table highlights the most recently selected stock for
                 easy reference.

            \u2192 3. Managing Stock Data:
               \u2022 Use the 'Add' button to add the queried stock data for
                 the most recent traded day to the table.
               \u2022 Use the 'Delete' button to remove the selected stock data
                 from the table.
               \u2022 Use the 'Import' button to import stock data from an XML file.
               \u2022 Use the 'Export' button to save the current stock list to
                 an XML file.
               \u2022 The 'Clear' button will remove all stock data from the table.

            \u2192 4. Sorting and Updating:
               \u2022 Use the 'Sort By' dropdown menu to sort the stock list by
                 different criteria such as symbol, date, or price.
               \u2022 Use the 'Push' button to update the displayed stock data
                 to the most recent values.

            \u2192 5. Experimentation:
               \u2022 The 'Rand' button will add randomized stock data to the
                 table for experimentation purposes without exhausting API
                 search queries.

            \u2192 6. Troubleshooting:
               \u2022 If you encounter any issues, ensure your stock symbol is
                 valid and that you have not exceeded the API usage limits.
            """),

    /**
     * Welcome message to greet the user and provide basic instructions.
     */
    WELCOME_MESSAGE("""
            =======================================
            \u2605 WELCOME MESSAGE \u2605
            =======================================

            Welcome to the Stock Data Viewer!

            \u2022 You can fetch and view stock data by entering stock symbols in
              the search field.

            \u2605 Example Inputs:
               \u2022 Enter 'AAPL' to view data for Apple Inc.
               \u2022 Enter 'GOOGL' to view data for Alphabet Inc.

            \u2022 The stock data will be fetched and displayed based on the last
              100 traded days.
            \u2022 Use the calendar icon \uD83D\uDCC5 to pick specific dates for detailed stock
              information.
            \u2022 Use the 'Add' button to add the queried stock data for the most
              recent traded day to the table.
            \u2022 Use the 'Delete' button to remove the selected stock data from
              the table.
            \u2022 Use the 'Rand' button to add randomized stock data to the table
              to experiment with functionality without exhausting API search
              queries.

            If you encounter any issues, please refer to the help message.
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

    public static String apiMessage(String inputText) {
        return String.format("""
            =======================================
            \u2605 LOAD Successful \u2605
            =======================================

            Current API Key: %s
            
            Thank you for using Alpha Vantage!
            Our standard API rate limit is 25 requests per day.
            Please subscribe to any of the premium plans at
            https://www.alphavantage.co/premium/
            to instantly remove all daily rate limits.
            """, inputText);
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
