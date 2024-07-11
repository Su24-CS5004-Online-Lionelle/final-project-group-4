package view;

import java.util.List;
import java.util.Scanner;

import model.DataMgmt.Stock;

//This was designed by Kangning and is just a demo so we can see the output and test the API. The messages displayed to
//user, allowable interaction and GUI will be done here by assigned team member.

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