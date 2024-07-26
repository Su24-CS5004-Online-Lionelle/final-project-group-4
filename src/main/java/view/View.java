package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import controller.Controller;
import model.DataMgmt.Stock;
import model.DataMgmt.StockList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.*;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.markers.SeriesMarkers;


/**
 * The View class handles the user interface interactions. It provides methods to display messages,
 * get user input, and show stock data.
 */
public class View {

    private JFrame frame;
    private JPanel chartPanel;

    private class Slot {
        Stock stock;

        public Slot(Stock stock, Integer loc) {
            this.stock = stock;
        }
    }

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

    private void build(JFrame frame) {
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // create HintTextField instance with a hint
        HintTextField textField = new HintTextField("Enter a stock symbol");
        textField.setBounds(50, 50, 300, 30);
        frame.add(textField);

        // create search Button instance
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(370, 50, 80, 30);
        frame.add(searchButton);

        // create add Button instance
        JButton addButton = new JButton("Add");
        addButton.setBounds(280, 170, 80, 30);
        frame.add(addButton);

        // create remove Button instance
        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(370, 170, 80, 30);
        frame.add(removeButton);

        // create export Button instance
        JButton exportButton = new JButton("Export");
        exportButton.setBounds(680, 520, 80, 30);
        frame.add(exportButton);

        // create help Button instance
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(600, 520, 80, 30);
        frame.add(helpButton);

        // create JComboBox for sort options
        String[] sortOptions = {"Sort by", "Date"};
        JComboBox<String> sortByComboBox = new JComboBox<>(sortOptions);
        sortByComboBox.setBounds(50, 170, 100, 30);
        frame.add(sortByComboBox);

        // create JScrollPane and JTextArea
        JTextArea textArea = new JTextArea(10, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(470, 270, 280, 230); // set position and size of JScrollPane
        frame.add(scrollPane); // put JScrollPane to JFrame

        // create JTable and JScrollPane for all records
        String[] columnNames = {"Date", "Symbol", "Open", "High", "Low", "Close", "Volume"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(50, 220, 400, 280); // set position and size of table JScrollPane
        frame.add(tableScrollPane); // put table JScrollPane to JFrame

        // create JTable and JScrollPane for single result of search
        DefaultTableModel tableSingle = new DefaultTableModel(columnNames, 0);
        JTable SingleTable = new JTable(tableSingle);
        JScrollPane tableScrollPaneSingle = new JScrollPane(SingleTable);
        tableScrollPaneSingle.setBounds(50, 100, 400, 50);
        frame.add(tableScrollPaneSingle);

        // create JPanel for chart
        chartPanel = new JPanel();
        chartPanel.setBounds(470, 50, 280, 200);
        frame.add(chartPanel);
        // Generate and display the chart
        showChart();

        // set initial welcome text
        textArea.setText(welcomeMessage);

        // add search button's ActionListener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get content from input field
                String codeInput = textField.getText();
                refreshBlock(codeInput, textArea);
            }
        });

        // add help button's ActionListener
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(helpMessage);
            }
        });

        // center the frame on screen
        frame.setLocationRelativeTo(null);
        // set JFrame visible
        frame.setVisible(true);
        // set initial focus on searchButton to avoid focusing on textField
        searchButton.requestFocus();
    }

    private void refreshBlock(String codeInput, JTextArea outputLabel) {
        try {
            StockList stocks = Controller.getInstance().fetchAllStock(codeInput);
            outputLabel.setText(stocks.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // custom HintTextField class
    private class HintTextField extends JTextField implements FocusListener {
        private final String hint;
        private boolean showingHint;

        public HintTextField(String hint) {
            super(hint);
            this.hint = hint;
            this.showingHint = true;
            super.addFocusListener((FocusListener) this);
            this.setForeground(Color.GRAY);
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText("");
                this.setForeground(Color.BLACK);
                showingHint = false;
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText(hint);
                this.setForeground(Color.GRAY);
                showingHint = true;
            }
        }

        @Override
        public String getText() {
            return showingHint ? "" : super.getText();
        }
    }

    private void showChart() {
        // Example data
        List<Double> xData = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
        List<Double> yData = Arrays.asList(1.0, 2.4, 3.3, 4.5, 5.0, 3.5, 7.0, 6.2, 8.0, 9.0);

        // Create a chart
        XYChart chart = new XYChartBuilder()
                .width(280)
                .height(200)
                .title("Sample Line Chart")
                .xAxisTitle("X Axis")
                .yAxisTitle("Y Axis")
                .build();
        // Customize the chart style
        XYStyler styler = chart.getStyler();
        styler.setLegendVisible(false); // Hide the legend
        styler.setChartBackgroundColor(Color.WHITE); // Set chart background color to white
        styler.setPlotBackgroundColor(Color.WHITE); // Set plot background color to white
        styler.setPlotBorderVisible(false); // Hide plot border
        styler.setChartTitleVisible(false); // Hide the title
        // Set empty axis titles
        chart.setXAxisTitle(""); // Remove X axis title
        chart.setYAxisTitle(""); // Remove Y axis title

        // Add series to the chart
        XYSeries series = chart.addSeries("Series1", xData, yData);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line); // Set the series to line
        series.setMarker(SeriesMarkers.NONE); // Remove markers from the line
        series.setLineWidth(1); // Adjust line width for better visibility
        series.setLineColor(Color.RED); // Set the line color to red

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



}
