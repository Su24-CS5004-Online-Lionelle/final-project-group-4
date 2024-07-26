import controller.Controller;

import java.io.IOException;

/**
 * The Main class serves as the entry point for the application. It initializes the Controller with
 * the API key and starts the application logic.
 */
public class Main {
    /**
     * The main method which serves as the entry point for the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // API key used to access the AlphaVantage API
        String apiKey = "SZPBC0GPHK788VZT";

        try {
            // Initialize the Controller, which also initializes and shows the View
            Controller.getInstance(apiKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}