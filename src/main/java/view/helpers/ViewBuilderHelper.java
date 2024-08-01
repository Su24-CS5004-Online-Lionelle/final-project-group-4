package view.helpers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import model.DataMgmt.Stock;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.JDatePickerImpl;
import view.helpers.HintTextFieldHelper.HintTextField;
import view.helpers.BackgroundPanelHelper.BackgroundPanel;
import view.View;

public class ViewBuilderHelper {

    public static void build(JFrame frame, Controller controller, final JTextArea textArea,
            final JPanel chartPanel, final View view) {
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
        HintTextField textField = new HintTextField(" Enter a stock symbol");
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
        chartPanel.setBounds(570, 50, 380, 200);
        frame.add(chartPanel);

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
        textArea.setText(Messages.WELCOME_MESSAGE.getMessage());

        // add search button's ActionListener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get content from input field
                String codeInput = textField.getText();

                // Fetch stock data using the Controller
                List<Stock> stockData = controller.fetchStockData(codeInput);

                // Check if data is available and update the chart
                if (stockData != null && !stockData.isEmpty()) {
                    // Update the chart with the fetched stock data
                    view.showChart(stockData);

                    // Update table, clear existing rows
                    tableModel.setRowCount(0);
                    tableSingle.setRowCount(0);

                    // Add all stock data to tableModel
                    for (Stock stock : stockData) {
                        Object[] rowData = {stock.getDate(), stock.getSymbol(), stock.getOpen(),
                                stock.getHigh(), stock.getLow(), stock.getClose(),
                                stock.getVolume()};
                        tableModel.addRow(rowData);
                    }

                    // Add the most recent stock data to tableSingle
                    Stock mostRecentStock = stockData.stream()
                            .max(Comparator.comparing(Stock::getDate)).orElse(null);
                    if (mostRecentStock != null) {
                        Object[] recentRowData = {mostRecentStock.getDate(),
                                mostRecentStock.getSymbol(), mostRecentStock.getOpen(),
                                mostRecentStock.getHigh(), mostRecentStock.getLow(),
                                mostRecentStock.getClose(), mostRecentStock.getVolume()};
                        tableSingle.addRow(recentRowData);
                    }
                } else {
                    // Display an error message if no data is available
                    view.displayError("No data available for the specified symbol: " + codeInput);
                }
            }
        });

        // add help button's ActionListener
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(Messages.HELP_MESSAGE.getMessage());
            }
        });

        // center the frame on screen
        frame.setLocationRelativeTo(null);
        // set JFrame visible
        frame.setVisible(true);
        // set initial focus on searchButton to avoid focusing on textField
        searchButton.requestFocus();
    }
}
