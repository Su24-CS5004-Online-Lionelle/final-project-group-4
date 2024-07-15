package view;

import java.util.List;
import java.util.Scanner;
import model.DataMgmt.Stock;

/**
 * The View class handles the user interface interactions.
 * It provides methods to display messages, get user input, and show stock data.
 */
public class View {

    // Static help message displayed when an exception occurs or help is needed
    private static final String helpMessage = """
            Help message:
            - show [stock | category | all] [-a price] [-o asc]
            - add [stock]
            - rm [stock]
            """;

    // Static welcome message displayed at the start of the application
    private static final String welcomeMessage = """
            Welcome to our FinanceApp!
            Example input:
            - show [stock | category | all] [-a price] [-o asc]
            - add [stock]
            - rm [stock]

            Your input:
            """;

    // Scanner for reading user input from the console
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Prints the help message along with the exception message.
     *
     * @param e the exception that triggered the help message
     */
    public static void printHelp(Exception e) {
        System.err.println(e.getMessage()); // Print the exception message to standard error
        System.out.println(helpMessage); // Print the help message to standard output
    }

    /**
     * Displays the welcome message at the start of the application.
     */
    public static void welcome() {
        System.out.println(welcomeMessage); // Print the welcome message
    }

    /**
     * Prompts the user with a message and returns their input.
     *
     * @param prompt the message to display to the user
     * @return the user's input as a String
     */
    public static String getInput(String prompt) {
        System.out.print(prompt); // Display the prompt message
        return scanner.nextLine(); // Read and return the user's input
    }

    /**
     * Prompts the user if they want to add more stocks.
     *
     * @return true if the user wants to add more stocks, false otherwise
     */
    public static boolean promptAddMoreStocks() {
        System.out.print("Do you want to add more stocks? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("yes") || input.equals("y");
    }

    /**
     * Displays a farewell message to the user.
     * Prints "Goodbye! Have a great day." to the standard output.
     */
    public static void goodbye() {
        System.out.println("Goodbye! Have a great day.");
    }

    /**
     * Displays a list of stock records.
     *
     * @param records the list of Stock objects to display
     */
    public static void display(List<Stock> records) {
        if (records.isEmpty()) {
            System.out.println("No records found."); // Inform the user if no records are found
        } else {
            for (Stock stock : records) {
                // Print details of each stock
                System.out.println("Symbol: " + stock.getSymbol());
                System.out.println("Price: " + stock.getPrice());
                System.out.println("Volume: " + stock.getVolume());
                System.out.println("Date: " + stock.getDate());
                System.out.println("----"); // Separator for each stock
            }
        }
    }
}