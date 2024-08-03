package view;

import java.awt.*;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

import controller.Controller;
import model.DataMgmt.Stock;
import view.helpers.ViewBuilderHelper;
import view.helpers.Messages;
import view.helpers.ChartHelper;

/**
 * The View class handles the user interface interactions. It provides methods to display messages,
 * get user input, and show stock data.
 */
public class View {
    private JFrame frame;
    private JTextArea textArea;
    private JPanel chartPanel;

    // Scanner for reading user input from the console
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Prints the help message along with the exception message.
     *
     * @param e the exception that triggered the help message
     */
    public static void printHelp(Exception e) {
        System.err.println(e.getMessage()); // Print the exception message to standard error
        System.out.println(Messages.HELP_MESSAGE.getMessage()); // Print the help message to
                                                                // standard output
    }

    /**
     * Displays the welcome message at the start of the application.
     */
    public static void welcome() {
        System.out.println(Messages.WELCOME_MESSAGE.getMessage()); // Print the welcome message
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
     */
    public View(Controller controller) {
        this.textArea = new JTextArea(10, 20);
        this.chartPanel = new JPanel(new BorderLayout());
        this.frame = new JFrame("STOCK QUERY");
        ViewBuilderHelper.build(frame, controller, textArea, chartPanel, this);
    }

    /**
     * Makes the JFrame visible.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Displays a chart with the provided stock data.
     *
     * @param stockData the list of Stock objects to display in the chart
     */
    public void showChart(List<Stock> stockData) {
        JPanel chartPanelWrapper = ChartHelper.createChartPanel(stockData);

        // Clear the existing content and add the new chart panel
        chartPanel.removeAll();
        chartPanel.add(chartPanelWrapper);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new View(Controller.getInstance()).show());
    }
}
