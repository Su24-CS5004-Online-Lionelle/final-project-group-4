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
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.knowm.xchart.*;
import org.knowm.xchart.style.XYStyler;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.jdatepicker.impl.JDatePickerImpl;
import controller.Controller;
import model.DataMgmt.Stock;

/**
 * The View class handles the user interface interactions. It provides methods to display messages,
 * get user input, and show stock data.
 */
public class View {

    private Controller controller; // Reference to the Controller

    private class Slot<T> {
        T data;

        public Slot(T data) {
            this.data = data;
        }
    }

    JFrame frame;
    JTextArea textArea;
    JPanel chartPanel;

    private static final String helpMessage = """
        Help message:
        - Enter a stock symbol to fetch and display its data.
        """;

    private static final String welcomeMessage = """
        Welcome to the Stock Data Viewer!
        You can fetch and view stock data by entering stock symbols.
        Example input:
        - Enter 'AAPL' to view data for Apple Inc.
        - Enter 'GOOGL' to view data for Alphabet Inc.
        You stock data will be returned based on the last 100 traded days.
        """;

    private static final Scanner scanner = new Scanner(System.in);

    public static void printHelp(Exception e) {
        System.err.println(e.getMessage());
        System.out.println(helpMessage);
    }

    public static void welcome() {
        System.out.println(welcomeMessage);
    }

    public static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static boolean askForMoreStocks() {
        System.out.print("Do you want to check more stocks? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("yes") || input.equals("y");
    }

    public static void goodbye() {
        System.out.println("Goodbye! Have a great day.");
    }

    public void display(List<Stock> records) {
        if (records.isEmpty()) {
            textArea.append("No records found.\n");
        } else {
            for (Stock stock : records) {
                textArea.append("Date: " + stock.getDate() + "\n");
                textArea.append("Symbol: " + stock.getSymbol() + "\n");
                textArea.append("Open: " + stock.getOpen() + "\n");
                textArea.append("High: " + stock.getHigh() + "\n");
                textArea.append("Low: " + stock.getLow() + "\n");
                textArea.append("Close: " + stock.getClose() + "\n");
                textArea.append("Volume: " + stock.getVolume() + "\n");
                textArea.append("----\n");
            }
        }
    }

    public void displayError(String message) {
        textArea.append("Error: " + message + "\n");
    }

    public View(Controller controller) {
        this.controller = controller;
        this.frame = new JFrame("STOCK QUERY");
        build(frame);
    }

    private void build(JFrame frame) {
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        HintTextField textField = new HintTextField("Enter a stock symbol");
        inputPanel.add(textField);

        JButton searchButton = new JButton("Search");
        inputPanel.add(searchButton);

        JButton addButton = new JButton("Add");
        inputPanel.add(addButton);

        JButton removeButton = new JButton("Remove");
        inputPanel.add(removeButton);

        JButton importButton = new JButton("Import");
        inputPanel.add(importButton);

        JButton exportButton = new JButton("Export");
        inputPanel.add(exportButton);

        JButton helpButton = new JButton("Help");
        inputPanel.add(helpButton);

        JButton showBarChartButton = new JButton("Show Bar Chart");
        inputPanel.add(showBarChartButton);

        JButton showLineAreaChartButton = new JButton("Show Line & Area Chart");
        inputPanel.add(showLineAreaChartButton);

        String[] sortOptions = {"Sort by", "Date", "Open", "High", "Low", "Close", "Volume"};
        JComboBox<String> sortByComboBox = new JComboBox<>(sortOptions);
        inputPanel.add(sortByComboBox);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        inputPanel.add(datePicker);

        frame.add(inputPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        chartPanel = new JPanel(new BorderLayout());
        frame.add(chartPanel, BorderLayout.EAST);

        showChart("default");

        searchButton.addActionListener(e -> {
            String codeInput = textField.getText();
            controller.fetchAndDisplayStockData(codeInput);
        });

        helpButton.addActionListener(e -> textArea.setText(helpMessage));

        showBarChartButton.addActionListener(e -> showChart("bar"));

        showLineAreaChartButton.addActionListener(e -> showChart("line_area"));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class HintTextField extends JTextField implements FocusListener {
        private final String hint;
        private boolean showingHint;

        public HintTextField(String hint) {
            super(hint);
            this.hint = hint;
            this.showingHint = true;
            this.setForeground(Color.GRAY);
            this.addFocusListener(this);
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

    private void showChart(String chartType) {
        JPanel chartPanelWrapper = new JPanel(new BorderLayout());

        if (chartType.equals("bar")) {
            chartPanelWrapper.add(new XChartPanel<>(getBarChart()), BorderLayout.CENTER);
        } else if (chartType.equals("line_area")) {
            chartPanelWrapper.add(new XChartPanel<>(getLineAreaChart()), BorderLayout.CENTER);
        } else {
            chartPanelWrapper.add(new XChartPanel<>(getSampleLineChart()), BorderLayout.CENTER);
        }

        chartPanel.removeAll();
        chartPanel.add(chartPanelWrapper);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    private XYChart getSampleLineChart() {
        List<Double> xData = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
        List<Double> yData = Arrays.asList(1.0, 2.4, 3.3, 4.5, 5.0, 3.5, 7.0, 6.2, 8.0, 9.0);

        XYChart chart = new XYChartBuilder().width(280).height(200).title("Sample Line Chart")
                .xAxisTitle("date").yAxisTitle("price").build();

        XYStyler styler = chart.getStyler();
        styler.setLegendVisible(false);
        styler.setChartBackgroundColor(Color.WHITE);
        styler.setPlotBackgroundColor(Color.WHITE);
        styler.setPlotBorderVisible(false);
        styler.setChartTitleVisible(false);

        XYSeries series = chart.addSeries("Series1", xData, yData);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineWidth(1);
        series.setLineColor(Color.RED);

        return chart;
    }

    private CategoryChart getBarChart() {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Score Histogram").xAxisTitle("Score").yAxisTitle("Number").build();

        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        chart.addSeries("test 1", Arrays.asList(0, 1, 2, 3, 4), Arrays.asList(4, 5, 9, 6, 5));

        return chart;
    }

    private XYChart getLineAreaChart() {
        XYChart chart = new XYChartBuilder().width(800).height(600).title("Line & Area Chart").xAxisTitle("Age").yAxisTitle("Amount").build();

        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        //chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
        chart.getStyler().setYAxisDecimalPattern("$ #,###.##");
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        double[] xAges = new double[] {60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87,
                88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100};

        double[] yLiability = new double[] {672234, 691729, 711789, 732431, 753671, 775528, 798018, 821160, 844974, 869478, 907735, 887139, 865486,
                843023, 819621, 795398, 770426, 744749, 719011, 693176, 667342, 641609, 616078, 590846, 565385, 540002, 514620, 489380, 465149, 441817,
                419513, 398465, 377991, 358784, 340920, 323724, 308114, 293097, 279356, 267008, 254873};

        double[] yPercentile75th = new double[] {800000, 878736, 945583, 1004209, 1083964, 1156332, 1248041, 1340801, 1440138, 1550005, 1647728,
                1705046, 1705032, 1710672, 1700847, 1683418, 1686522, 1674901, 1680456, 1679164, 1668514, 1672860, 1673988, 1646597, 1641842, 1653758,
                1636317, 1620725, 1589985, 1586451, 1559507, 1544234, 1529700, 1507496, 1474907, 1422169, 1415079, 1346929, 1311689, 1256114, 1221034};

        double[] yPercentile50th = new double[] {800000, 835286, 873456, 927048, 969305, 1030749, 1101102, 1171396, 1246486, 1329076, 1424666, 1424173,
                1421853, 1397093, 1381882, 1364562, 1360050, 1336885, 1340431, 1312217, 1288274, 1271615, 1262682, 1237287, 1211335, 1191953, 1159689,
                1117412, 1078875, 1021020, 974933, 910189, 869154, 798476, 744934, 674501, 609237, 524516, 442234, 343960, 257025};

        double[] yPercentile25th = new double[] {800000, 791439, 809744, 837020, 871166, 914836, 958257, 1002955, 1054094, 1118934, 1194071, 1185041,
                1175401, 1156578, 1132121, 1094879, 1066202, 1054411, 1028619, 987730, 944977, 914929, 880687, 809330, 783318, 739751, 696201, 638242,
                565197, 496959, 421280, 358113, 276518, 195571, 109514, 13876, 29, 0, 0, 0, 0};

        XYSeries seriesLiability = chart.addSeries("Liability", xAges, yLiability);
        seriesLiability.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        seriesLiability.setMarker(SeriesMarkers.NONE);

        chart.addSeries("75th Percentile", xAges, yPercentile75th);
        chart.addSeries("50th Percentile", xAges, yPercentile50th);
        chart.addSeries("25th Percentile", xAges, yPercentile25th);

        return chart;
    }

    public void show() {
        frame.setVisible(true);
    }
}
