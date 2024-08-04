import controller.Controller;

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

        try {
            // Initialize the Controller, which also initializes and shows the View
            Controller.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
