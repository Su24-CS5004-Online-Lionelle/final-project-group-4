package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import model.DataMgmt.Stock;
import controller.Controller;

import javax.swing.*;

/**
 * The View class handles the user interface interactions. It provides methods to display messages,
 * get user input, and show stock data.
 */
public class View {

    private Controller controller; // Reference to the Controller

    private class Slot {
        Stock stock;

        public Slot(Stock stock, Integer loc) {
            this.stock = stock;
        }
    }

    JFrame frame;
    JTextArea textArea;

    // Static help message displayed when an exception occurs or help is needed
    private static final String helpMessage = """
            Help message:
            - Enter a stock symbol to fetch and display its data.
            """;

    // Static welcome message displayed at the start of the application
    private static final String welcomeMessage = """
            Welcome to the Stock Data Viewer!
            You can fetch and view stock data by entering stock symbols.
            Example input:
            - Enter 'AAPL' to view data for Apple Inc.
            - Enter 'GOOGL' to view data for Alphabet Inc.
            You stock data will be returned based on the last 100 traded days.
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
     * Prompts the user if they want to check more stocks.
     *
     * @return true if the user wants to check more stocks, false otherwise
     */
    public static boolean askForMoreStocks() {
        System.out.print("Do you want to check more stocks? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("yes") || input.equals("y");
    }

    /**
     * Displays a farewell message to the user. Prints "Goodbye! Have a great day." to the standard
     * output.
     */
    public static void goodbye() {
        System.out.println("Goodbye! Have a great day.");
    }

    /**
     * Displays a list of stock records.
     *
     * @param records the list of Stock objects to display
     */
    public void display(List<Stock> records) {
        if (records.isEmpty()) {
            textArea.append("No records found.\n"); // Inform the user if no records are found
        } else {
            for (Stock stock : records) {
                // Append details of each stock to the JTextArea
                textArea.append("Date: " + stock.getDate() + "\n");
                textArea.append("Symbol: " + stock.getSymbol() + "\n");
                textArea.append("Open: " + stock.getOpen() + "\n");
                textArea.append("High: " + stock.getHigh() + "\n");
                textArea.append("Low: " + stock.getLow() + "\n");
                textArea.append("Close: " + stock.getClose() + "\n");
                textArea.append("Volume: " + stock.getVolume() + "\n");
                textArea.append("----\n"); // Separator for each stock
            }
        }
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display
     */
    public void displayError(String message) {
        textArea.append("Error: " + message + "\n"); // Append the error message to the JTextArea
    }

    /**
     * Constructor for the View class. It initializes the JFrame and its components.
     *
     * @param controller the Controller instance to interact with
     */
    public View(Controller controller) {
        this.controller = controller; // Initialize the controller reference
        this.frame = new JFrame("STOCK QUERY");
        build(frame);
    }

    /**
     * Builds the JFrame with its components including text fields, buttons, and text areas.
     *
     * @param frame the JFrame to be built
     */
    private void build(JFrame frame) {
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // create JTextField instance
        JTextField textField = new JTextField();
        textField.setBounds(50, 50, 200, 30);
        frame.add(textField);

        // create Submit Button instance
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(270, 50, 80, 30);
        frame.add(submitButton);

        // create Help Button instance
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(370, 50, 80, 30);
        frame.add(helpButton);

        // create JScrollPane and JTextArea
        textArea = new JTextArea(10, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 100, 400, 400); // Set the position and size of JScrollPane
        frame.add(scrollPane); // Add JScrollPane to the JFrame's content pane

        // set initial welcome text
        textArea.setText(welcomeMessage);

        // add submit button's ActionListener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get content from input field
                String codeInput = textField.getText();

                // Fetch and display stock data using the Controller
                controller.fetchAndDisplayStockData(codeInput);
            }
        });

        // add help button's ActionListener
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(helpMessage);
            }
        });

        // set JFrame visible
        frame.setVisible(true);
    }

    /**
     * Makes the JFrame visible.
     */
    public void show() {
        frame.setVisible(true);
    }
}
