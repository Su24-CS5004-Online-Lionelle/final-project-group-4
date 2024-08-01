package view.helpers;

public enum Messages {
    HELP_MESSAGE("""
            Help message:
            - Enter a stock symbol to fetch and display its data.
            """),

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

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
