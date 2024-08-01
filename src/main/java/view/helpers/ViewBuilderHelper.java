package view.helpers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import model.DataMgmt.Stock;
import model.Model;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.JDatePickerImpl;
import view.helpers.HintTextFieldHelper.HintTextField;
import view.helpers.BackgroundPanelHelper.BackgroundPanel;
import view.View;

public class ViewBuilderHelper {

    private static Stock mostRecentStock;
    private static DefaultTableModel tableSingle;
    private static DefaultTableModel tableModel;

    private static LocalDate today = LocalDate.now();
    private static JTable table;
    private static String lastSelected;

    public static void build(JFrame frame, Controller controller, final JTextArea textArea,
                             final JPanel chartPanel, final View view) {
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("libs/images/background.png");
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
        JButton addButton = new JButton("ADD");
        addButton.setBounds(310, 170, 80, 30);
        frame.add(addButton);

        // create remove Button instance
        JButton removeButton = new JButton("DEL");
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
        helpButton.setBounds(210, 520, 80, 30);
        frame.add(helpButton);

        // create clear Button instance
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(130, 520, 80, 30);
        frame.add(clearButton);

        // create help Button instance
        JButton pushButton = new JButton("Rand");
        pushButton.setBounds(50, 520, 80, 30);
        frame.add(pushButton);

        // create JComboBox for sort options
        String[] sortOptions = {"Sort by", "Open", "High", "Low", "Close", "Volume"};
        JComboBox<String> sortByComboBox = new JComboBox<>(sortOptions);
        sortByComboBox.setBounds(45, 170, 100, 30);
        frame.add(sortByComboBox);

        // create JScrollPane and JTextArea
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(570, 270, 380, 230); // set position and size of JScrollPane
        frame.add(scrollPane); // put JScrollPane to JFrame

        // create JTable and JScrollPane for multiple records
        String[] columnNames = {"Date", "Symbol", "Open", "High", "Low", "Close", "Volume"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(50, 220, 500, 280); // set position and size of table JScrollPane
        frame.add(tableScrollPane); // put table JScrollPane to JFrame

        // create JTable and JScrollPane for single result of search
        tableSingle = new DefaultTableModel(columnNames, 0);
        JTable SingleTable = new JTable(tableSingle);
        JScrollPane tableScrollPaneSingle = new JScrollPane(SingleTable);
        tableScrollPaneSingle.setBounds(50, 100, 500, 50);
        frame.add(tableScrollPaneSingle);

        // create JPanel for chart
        chartPanel.setBounds(570, 50, 380, 200);
        chartPanel.setBackground(Color.WHITE);
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

                // checkIf the date is valid
                if (model.getValue() != null && !isDateValid(model.getValue(), today)) {
                    // Optionally, you can display a message if no row is selected
                    JOptionPane.showMessageDialog(frame, "Date is not Valid",
                            "InValid Date", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Fetch stock data using the Controller
                controller.cleanCache();
                List<Stock> stockData = controller.fetchStockData(codeInput);
                if (stockData.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "You have reach the 25 times daily limit, please Paypal $50 to Jubal",
                            "No Money Exception", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (model.getValue() != null) {
                    mostRecentStock = controller.fetchSpecificStockDate(model.getValue());
                } else {
                    mostRecentStock = controller.fetchMostRecentStockData();
                }

                // Check if data is available and update the chart
                if (stockData != null && !stockData.isEmpty()) {
                    // Update the chart with the fetched stock data
                    view.showChart(stockData);

                    // update single table
                    updateModelSingle(mostRecentStock, tableSingle);
                } else {
                    // Display an error message if no data is available
                    view.displayError("No data available for the specified symbol: " + codeInput);
                }
            }

        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String content = readFile(selectedFile);
                    Model.getInstance().writeDataToDB(content);
                    updateTableModel();
                }
            }

            private String readFile(File file) {
                StringBuilder fileContent = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return fileContent.toString();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setDialogTitle("Export List");
                fileChooser.setFileFilter(new FileNameExtensionFilter("XML (*.xml)", "xml"));

                int userSelection = fileChooser.showSaveDialog(textArea);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter) fileChooser.getFileFilter();
                    String extension = selectedFilter.getExtensions()[0];
                    String newFileName = fileName.split("\\.")[0] + "." + extension;

                    File outputFile = new File(selectedFile.getParent(), newFileName);
                    try {
                        Controller.getInstance().getStockList().output(outputFile);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });


        // Add the ActionListener to the "Add" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // save the latest one to the database
                if (mostRecentStock != null) {
                    Controller.getInstance().getStockList().addStock(mostRecentStock);
                    updateTableModel();
                } else {
                    // Optionally, you can display a message if no row is selected
                    JOptionPane.showMessageDialog(frame, "You have to search Stock first",
                            "No Search Exception", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Add the ActionListener to the "Clear" button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().getStockList().clearAll();
                updateTableModel();
            }
        });

        // Add the ActionListener to the "Push" button
        pushButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Stock randomTestStock = Model.getRandomStock();
                Controller.getInstance().getStockList().addStock(randomTestStock);
                updateTableModel();
            }
        });

        // Add the ActionListener to the "Remove" button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Get the selected row index
                int selectedRow = table.getSelectedRow();

                // Check if a row is selected
                if (selectedRow != -1) {
                    // Remove the selected row from the table model
                    tableModel.removeRow(selectedRow);
                    Controller.getInstance().getStockList().removeById(selectedRow);
                    updateTableModel();
                } else {
                    // Optionally, you can display a message if no row is selected
                    JOptionPane.showMessageDialog(frame, "Select a row to remove.",
                            "No selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        sortByComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected sorting option
                String selectedOption = (String) sortByComboBox.getSelectedItem();
                if (selectedOption.equals("Sort by")) {
                    view.displayError("Sort by Nothing");
                } else {
                    Controller.getInstance().getStockList().sortBy(selectedOption);
                    highLightSelected(selectedOption, table);
                    updateTableModel();
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

        updateTableModel(1);
    }

    private static void highLightSelected(String selectedOption, JTable table) {
        if (lastSelected != null) {
            table.getColumn(lastSelected).setCellRenderer(table.getDefaultRenderer(Object.class));
        }
        lastSelected = selectedOption;

        table.getColumn(selectedOption).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(Color.YELLOW); // 设置背景色为黄色
                }
                return c;
            }
        });
    }


    private static boolean isDateValid(Date value, LocalDate today) {
        Instant instant = value.toInstant();

        LocalDate inputAsLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        return inputAsLocalDate.isBefore(today) || inputAsLocalDate.isEqual(today);
    }

    public static void updateTableModel() {
        // Update table, clear existing rows
        tableModel.setRowCount(0);

        List<Stock> stockData = Controller.getInstance().getStockList().getStockList();

        // update large panel
        for (Stock stock : stockData) {
            Object[] rowData = {stock.getDate(), stock.getSymbol(), stock.getOpen(),
                    stock.getHigh(), stock.getLow(), stock.getClose(),
                    stock.getVolume()};

            tableModel.addRow(rowData);
        }
    }

    public static void updateTableModel(int delay) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable updateTask = () -> {
            tableModel.setRowCount(0);

            List<Stock> stockData = Controller.getInstance().getStockList().getStockList();

            for (Stock stock : stockData) {
                Object[] rowData = {stock.getDate(), stock.getSymbol(), stock.getOpen(),
                        stock.getHigh(), stock.getLow(), stock.getClose(),
                        stock.getVolume()};

                tableModel.addRow(rowData);
            }
        };

        executor.schedule(updateTask, delay, TimeUnit.SECONDS);
    }

    private static void updateModelSingle(Stock mostRecentStock, DefaultTableModel tableSingle) {
        tableSingle.setRowCount(0);

        // Add the most recent stock data to tableSingle
        if (mostRecentStock != null) {
            Object[] recentRowData = {mostRecentStock.getDate(),
                    mostRecentStock.getSymbol(), mostRecentStock.getOpen(),
                    mostRecentStock.getHigh(), mostRecentStock.getLow(),
                    mostRecentStock.getClose(), mostRecentStock.getVolume()};
            tableSingle.addRow(recentRowData);
        }
    }
}
