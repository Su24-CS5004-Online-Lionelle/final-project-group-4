package view;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;

import controller.Controller;
import model.DataMgmt.Stock;
import view.helpers.ViewBuilderHelper;
import view.helpers.Messages;

import javax.swing.*;

import org.knowm.xchart.*;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.markers.SeriesMarkers;

/**
 * The View class handles the user interface interactions. It provides methods to display messages,
 * get user input, and show stock data.
 */
public class View {


    public void showChart(List<Stock> stockData) {
        if (stockData == null || stockData.isEmpty()) {
            return; // No data to display
        }

        // Extract dates and close prices from the stock data
        List<Date> xData =
                stockData.stream().map(Stock::getDateAsDate).collect(Collectors.toList());
        List<Double> yData = stockData.stream().map(Stock::getClose).collect(Collectors.toList());

        // Create a chart
        XYChart chart = new XYChartBuilder().width(800).height(400).title("Stock Prices")
                .xAxisTitle("Date").yAxisTitle("Close Price").build();

        // Customize the chart style
        XYStyler styler = chart.getStyler();
        styler.setLegendVisible(false); // Hide the legend
        styler.setChartBackgroundColor(Color.WHITE); // Set chart background color to white
        styler.setPlotBackgroundColor(Color.WHITE); // Set plot background color to white
        styler.setPlotBorderVisible(false); // Hide plot border
        styler.setDatePattern("yyyy-MM-dd"); // Set date format on the x-axis

        // Add series to the chart
        XYSeries series = chart.addSeries("Stock Prices", xData, yData);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line); // Set the series to line
        series.setMarker(SeriesMarkers.NONE); // Remove markers from the line
        series.setLineWidth(1); // Adjust line width for better visibility
        series.setLineColor(Color.BLUE); // Set the line color to blue

        // Create a chart panel to display the chart
        JPanel chartPanelWrapper = new JPanel();
        chartPanelWrapper.setLayout(new BorderLayout());
        chartPanelWrapper.add(new XChartPanel<>(chart), BorderLayout.CENTER);

        // Clear the existing content and add the new chart panel
        chartPanel.removeAll();
        chartPanel.add(chartPanelWrapper);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    JFrame frame;
    JTextArea textArea;
    JPanel chartPanel;

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
}
