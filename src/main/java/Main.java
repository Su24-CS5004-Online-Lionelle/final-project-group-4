import controller.Controller;

/**
 * The Main class serves as the entry point for the application.
 * It initializes the Controller with the API key and starts the application
 * logic.
 */
public class Main {
    /**
     * The main method which serves as the entry point for the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // API key used to access the AlphaVantage API
        String apiKey = "9WJQJ49V70I2R1VT";

        Controller controller = new Controller(apiKey);
        controller.run();
    }
}