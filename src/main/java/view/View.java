package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import controller.Controller;
import model.DataMgmt.Stock;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.knowm.xchart.*;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.jdatepicker.impl.JDatePickerImpl;
import java.awt.geom.RoundRectangle2D;


/**
 * The View class handles the user interface interactions. It provides methods to display messages,
 * get user input, and show stock data.
 */
public class View {

    private Controller controller; // Reference to the Controller

    // will use for semi-permance and line graph
    private class Slot {
        Stock stock;

        public Slot(Stock stock, Integer loc) {
            this.stock = stock;
        }
    }

    JFrame frame;
    JTextArea textArea;
    JPanel chartPanel;

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
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("src/background_test2.png");
        Image image = backgroundImage.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(image);
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);

        // create HintTextField instance with a hint
        View.HintTextField textField = new View.HintTextField(" Enter a stock symbol");
        textField.setBounds(50, 50, 400, 30);
        frame.add(textField);

        // create search Button instance
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(470, 50, 80, 30);
        frame.add(searchButton);

        // create add Button instance
        JButton addButton = new JButton("Add");
        addButton.setBounds(310, 170, 80, 30);
        frame.add(addButton);

        // create remove Button instance
        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(390, 170, 80, 30);
        frame.add(removeButton);

        // create import Button instance
        JButton importButton = new JButton("Import");
        importButton.setBounds(470, 170, 80, 30);
        frame.add(importButton);

        // create export Button instance
        JButton exportButton = new JButton("Export");
        exportButton.setBounds(880, 520, 80, 30);
        frame.add(exportButton);

        // create help Button instance
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(800, 520, 80, 30);
        frame.add(helpButton);

        // create JComboBox for sort options
        String[] sortOptions = {"Sort by", "Open", "High", "Low", "Close", "Volume"};
        JComboBox<String> sortByComboBox = new JComboBox<>(sortOptions);
        sortByComboBox.setBounds(45, 170, 100, 30);
        frame.add(sortByComboBox);

        // create JScrollPane and JTextArea
        textArea = new JTextArea(10, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(570, 270, 380, 230); // set position and size of JScrollPane
        frame.add(scrollPane); // put JScrollPane to JFrame

        // create JTable and JScrollPane for all records
        String[] columnNames = {"Date", "Symbol", "Open", "High", "Low", "Close", "Volume"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(50, 220, 500, 280); // set position and size of table JScrollPane
        frame.add(tableScrollPane); // put table JScrollPane to JFrame

        // create JTable and JScrollPane for single result of search
        DefaultTableModel tableSingle = new DefaultTableModel(columnNames, 0);
        JTable SingleTable = new JTable(tableSingle);
        JScrollPane tableScrollPaneSingle = new JScrollPane(SingleTable);
        tableScrollPaneSingle.setBounds(50, 100, 500, 50);
        frame.add(tableScrollPaneSingle);

        // create JPanel for chart
        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBounds(570, 50, 380, 200);
        frame.add(chartPanel);
        // Generate and display the chart
        showChart();

        // create and add the date picker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(150, 170, 150, 30);
        frame.add(datePicker);


        // set initial welcome text
        textArea.setText(welcomeMessage);

        // add search button's ActionListener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get content from input field
                String codeInput = textField.getText();

                // Fetch and display stock data using the Controller
                controller.fetchAndDisplayStockData(codeInput);
                // Fetch stock data using the Controller
                List<Stock> stockData = controller.fetchStockData(codeInput);

                // Update table, clear existing rows
                tableModel.setRowCount(0);
                tableSingle.setRowCount(0);


                // Find the most recent stock data entry (assuming the list is sorted by date)
                Stock mostRecentStock = stockData.get(0);

                // Add all stock data to tableModel
                for (Stock stock : stockData) {
                    Object[] rowData = {stock.getDate(), stock.getSymbol(), stock.getOpen(),
                            stock.getHigh(), stock.getLow(), stock.getClose(), stock.getVolume()};
                    tableModel.addRow(rowData);
                }

                // Add the most recent stock data to tableSingle
                Object[] recentRowData = {mostRecentStock.getDate(), mostRecentStock.getSymbol(),
                        mostRecentStock.getOpen(), mostRecentStock.getHigh(),
                        mostRecentStock.getLow(), mostRecentStock.getClose(),
                        mostRecentStock.getVolume()};
                tableSingle.addRow(recentRowData);
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
            this.setBorder(new RoundedBorder(10)); // Apply the rounded border with a radius of 15
            // this.setOpaque(false); // Ensure background is not opaque
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

    // class DateLabelFormatter for the calendar date picker
    private class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private static final String DATE_PATTERN = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    // class for background picture panel
    public class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Custom rounded border class
    public class RoundedBorder extends AbstractBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.GRAY); // Border color
            g2d.setStroke(new BasicStroke(2)); // Border thickness

            // Draw rounded rectangle
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2d.dispose();
        }
    }

    /**
     * Show the XChart.
     */
    private void showChart() {
        // Example data
        java.util.List<Double> xData =
                Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
        List<Double> yData = Arrays.asList(1.0, 2.4, 3.3, 4.5, 5.0, 3.5, 7.0, 6.2, 8.0, 9.0);

        // Create a chart
        XYChart chart = new XYChartBuilder().width(280).height(200).title("Sample Line Chart")
                .xAxisTitle("X Axis").yAxisTitle("Y Axis").build();
        // Customize the chart style
        XYStyler styler = chart.getStyler();
        styler.setLegendVisible(false); // Hide the legend
        styler.setChartBackgroundColor(Color.WHITE); // Set chart background color to white
        styler.setPlotBackgroundColor(Color.WHITE); // Set plot background color to white
        styler.setPlotBorderVisible(false); // Hide plot border
        styler.setChartTitleVisible(false); // Hide the title
        // Set empty axis titles
        chart.setXAxisTitle("date"); // Remove X axis title
        chart.setYAxisTitle("price"); // Remove Y axis title

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

    /**
     * Makes the JFrame visible.
     */
    public void show() {
        frame.setVisible(true);
    }

}
