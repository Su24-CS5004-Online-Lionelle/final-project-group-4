package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;
import controller.Controller;
import model.DataMgmt.Stock;

import javax.swing.*;

/**
 * The View class handles the user interface interactions. It provides methods to display messages,
 * get user input, and show stock data.
 */
public class View {

    private class Slot {
        Stock stock;

        public Slot(Stock stock, Integer loc) {
            this.stock = stock;
        }
    }

    JFrame frame;

    // Static help message displayed when an exception occurs or help is needed
    private static final String helpMessage = """
            Help message:
            - Enter a stock symbol to fetch and display its data.
            - You can fetch data for today or for a specific date.
            """;

    // Static welcome message displayed at the start of the application
    private static final String welcomeMessage = """
            Welcome to the Stock Data Viewer!
            You can fetch and view stock data by entering stock symbols.
            Example input:
            - Enter 'AAPL' to view data for Apple Inc.
            - Enter 'GOOGL' to view data for Alphabet Inc.
            You can fetch data for today or for a specific date.
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
    public static void display(List<Stock> records) {
        if (records.isEmpty()) {
            System.out.println("No records found."); // Inform the user if no records are found
        } else {
            for (Stock stock : records) {
                // Print details of each stock
                System.out.println("Date: " + stock.getDate());
                System.out.println("Symbol: " + stock.getSymbol());
                System.out.println("Open: " + stock.getOpen());
                System.out.println("High: " + stock.getHigh());
                System.out.println("Low: " + stock.getLow());
                System.out.println("Close: " + stock.getClose());
                System.out.println("Volume: " + stock.getVolume());
                System.out.println("----"); // Separator for each stock
            }
        }
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display
     */
    public static void displayError(String message) {
        System.err.println("Error: " + message); // Print the error message to standard error
    }

    public View() {
        this.frame = new JFrame("STOCK QUERY");
        build(frame);
    }

    /**
     * Builds the GUI frame with text fields, buttons, and text areas.
     *
     * @param frame The JFrame to build upon.
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
        JTextArea textArea = new JTextArea(10, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 100, 400, 400); // set JScrollPane's position and size
        frame.add(scrollPane); // add JScrollPane to JFrame's content pane

        // set initial welcome text
        textArea.setText(welcomeMessage);

        // add submit button's ActionListener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get content from input field
                String codeInput = textField.getText();

                refreshBlock(codeInput, textArea); // Refresh the display area with stock data
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
     * Refreshes the display area with stock data for the provided stock symbol.
     *
     * @param codeInput The stock symbol to fetch data for.
     * @param outputLabel The JTextArea to display the stock data.
     */
    private void refreshBlock(String codeInput, JTextArea outputLabel) {
        try {
            // This is the new method that fetches stock data and updates the JTextArea.
            List<Stock> stocks = Controller.getInstance().fetchStockData(codeInput);
            if (stocks.isEmpty()) {
                outputLabel.setText("No records found.");
            } else {
                StringBuilder displayText = new StringBuilder();
                for (Stock stock : stocks) {
                    displayText.append("Date: ").append(stock.getDate()).append("\n");
                    displayText.append("Symbol: ").append(stock.getSymbol()).append("\n");
                    displayText.append("Open: ").append(stock.getOpen()).append("\n");
                    displayText.append("High: ").append(stock.getHigh()).append("\n");
                    displayText.append("Low: ").append(stock.getLow()).append("\n");
                    displayText.append("Close: ").append(stock.getClose()).append("\n");
                    displayText.append("Volume: ").append(stock.getVolume()).append("\n");
                    displayText.append("----\n");
                }
                outputLabel.setText(displayText.toString());
            }
        } catch (Exception exception) {
            outputLabel.setText("Error: " + exception.getMessage());
        }
    }
}
