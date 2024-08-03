package view.helpers;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import controller.Controller;

import model.DataMgmt.Stock;
import model.Model.InvalidStockSymbolException;
import model.Model;
import model.Model.ApiLimitReachedException;

import org.jdatepicker.impl.UtilDateModel;

import java.io.File;
import java.io.IOException;

import view.View;

/**
 * Helper class for adding action listeners to UI components.
 */
public class ActionListenersHelper {

    /**
     * The most recent stock data retrieved.
     */
    private static Stock mostRecentStock;

    /**
     * The directory where preset custom lists are stored.
     */
    private static final String PRESET_DIRECTORY = "bin/customlists";

    /**
     * Retrieves the preset directory for storing custom lists.
     *
     * @return A File object representing the preset directory.
     */
    private static File getPresetDirectory() {
        return new File(PRESET_DIRECTORY);
    }

    /**
     * Adds an action listener to the search button to fetch stock data, update the date picker
     * range, and handle the table and chart updates.
     *
     * @param searchButton the JButton to add the action listener to
     * @param textField the JTextField for stock symbol input
     * @param model the UtilDateModel for handling date selection
     * @param today the LocalDate representing today’s date
     * @param controller the Controller to handle data fetching
     * @param view the View to update charts and error messages
     * @param textArea the JTextArea for displaying additional information
     * @param tableSingle the table model for displaying single stock data
     * @param datePicker the PromptDatePicker for date selection
     */
    public static void addSearchButtonListener(JButton searchButton, JTextField textField,
            UtilDateModel model, LocalDate today, Controller controller, View view,
            JTextArea textArea, DefaultTableModel tableSingle, PromptDatePicker datePicker) {

        searchButton.addActionListener(e -> {
            // Get content from input field
            String codeInput = textField.getText();

            // Validate stock symbol
            if (codeInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a stock symbol.", "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Fetch stock data using the Controller
                controller.cleanCache();
                List<Stock> stockData = controller.fetchStockData(codeInput);

                // Handle API limit exception
                if (stockData.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "You have reached the 25 times daily limit, please Paypal $50 to Jubal",
                            "API Limit Reached", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Write the fetched stock data to a temporary XML file
                controller.writeTempStockDataToXML(stockData);

                // Update the date picker range based on stock data from the XML file
                datePicker.updateDateRangeFromXML();

                // Get the most recent data / specific date data
                if (model.getValue() != null) {
                    Date selectedDate = (Date) model.getValue();
                    LocalDate localDate =
                            selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    mostRecentStock =
                            controller.fetchSpecificStockDate(java.sql.Date.valueOf(localDate));
                } else {
                    mostRecentStock = controller.fetchMostRecentStockData();
                }

                // Check if mostRecentStock is null before accessing it
                if (mostRecentStock == null) {
                    throw new InvalidStockSymbolException("Invalid stock symbol: " + codeInput);
                }

                // Uppercase mostRecentStock symbol
                mostRecentStock.setSymbol(mostRecentStock.getSymbol().toUpperCase());

                // Check if data is available and update the chart
                if (stockData != null && !stockData.isEmpty()) {
                    // Update the chart with the fetched stock data
                    view.showChart(stockData);

                    // Update single table
                    TableHelper.updateModelSingle(mostRecentStock, tableSingle);
                } else {
                    // Display an error message if no data is available
                    view.displayError("No data available for the specified symbol: " + codeInput);
                }
            } catch (InvalidStockSymbolException ex) {
                // Handle invalid stock symbol exception
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Stock Symbol",
                        JOptionPane.ERROR_MESSAGE);
            } catch (ApiLimitReachedException ex) {
                // Handle API limit reached exception
                JOptionPane.showMessageDialog(null,
                        "You have reached the 25 times daily limit, please Paypal $50 to Jubal",
                        "API Limit Reached", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                // Handle any other unexpected exceptions
                view.displayError("An unexpected error occurred: " + ex.getMessage());
            }
        });
    }

    /**
     * Adds an action listener to the import button.
     *
     * @param importButton the import button
     * @param textArea the text area to display messages
     * @param tableModel the table model to update the table
     * @param controller the controller to handle data importing
     */
    public static void addImportButtonListener(JButton importButton, JTextArea textArea,
            DefaultTableModel tableModel, Controller controller) {
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser for selecting the import file
                JFileChooser fileChooser = new JFileChooser(PRESET_DIRECTORY);

                // Restrict the file chooser to only allow file selection, not directories
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // Disable the "All files" option in the file chooser
                fileChooser.setAcceptAllFileFilterUsed(false);

                // Show the file chooser dialog and capture the user's choice
                int returnValue = fileChooser.showOpenDialog(null);

                // Check if the user approved the file selection
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();

                    // Call the controller to import the stock data from the selected file
                    controller.importStockDataFromFile(selectedFile, tableModel);
                }
            }
        });
    }

    /**
     * Adds an action listener to the export button to handle exporting stock data to a file.
     *
     * @param exportButton the export button
     * @param textArea the text area to display messages
     */
    public static void addExportButtonListener(JButton exportButton, JTextArea textArea) {
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser for selecting the export location
                JFileChooser fileChooser = new JFileChooser(getPresetDirectory());

                // Restrict the file chooser to only allow file selection, not directories
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // Disable the "All files" option in the file chooser
                fileChooser.setAcceptAllFileFilterUsed(false);

                // Set the dialog title for the file chooser
                fileChooser.setDialogTitle("Export List");

                // Set the file filter to only allow XML files
                fileChooser.setFileFilter(new FileNameExtensionFilter("XML (*.xml)", "xml"));

                // Show the save dialog and capture the user's choice
                int userSelection = fileChooser.showSaveDialog(textArea);

                // Check if the user approved the file selection
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();

                    // Get the file name and the selected filter's extension
                    String fileName = selectedFile.getName();
                    FileNameExtensionFilter selectedFilter =
                            (FileNameExtensionFilter) fileChooser.getFileFilter();
                    String extension = selectedFilter.getExtensions()[0];

                    // Create the new file name with the proper extension
                    String newFileName = fileName.split("\\.")[0] + "." + extension;

                    // Create the output file with the new file name
                    File outputFile = new File(selectedFile.getParent(), newFileName);
                    try {
                        // Export the stock list to the output file
                        Controller.getInstance().getStockList().output(outputFile);
                    } catch (IOException ex) {
                        // Handle any IO exceptions that occur during export
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    /**
     * Adds an action listener to the help button to display help messages.
     *
     * @param helpButton the help button
     * @param textArea the text area to display messages
     */
    public static void addHelpButtonListener(JButton helpButton, JTextArea textArea) {
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the text area with the help message from the Messages class
                textArea.setText(Messages.HELP_MESSAGE.getMessage());
            }
        });
    }

    /**
     * Adds an action listener to the clear button to clear all stock data.
     *
     * @param clearButton the clear button
     * @param tableModel the table model to update the table
     */
    public static void addClearButtonListener(JButton clearButton, DefaultTableModel tableModel) {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear all stock data from the stock list
                Controller.getInstance().getStockList().clearAll();

                // Update the table model to reflect the cleared data
                TableHelper.updateTableModel(tableModel);
            }
        });
    }

    /**
     * Adds an action listener to the push button to add a random stock for testing.
     *
     * @param pushButton the push button
     * @param tableModel the table model to update the table
     */
    public static void addPushButtonListener(JButton pushButton, DefaultTableModel tableModel) {
        pushButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Generate a random test stock
                Stock randomTestStock = Model.getRandomStock();

                // Add the random test stock to the stock list
                Controller.getInstance().getStockList().addStock(randomTestStock);

                // Update the table model to reflect the new stock
                TableHelper.updateTableModel(tableModel);
            }
        });
    }

    /**
     * Adds an action listener to the sort by combo box to handle sorting of the stock list.
     *
     * @param sortByComboBox the combo box for selecting sorting options
     * @param tableModel the table model to update the table
     * @param table the table to be sorted
     */
    public static void addSortByComboBoxListener(JComboBox<String> sortByComboBox,
            DefaultTableModel tableModel, JTable table) {
        sortByComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected sorting option
                String selectedOption = (String) sortByComboBox.getSelectedItem();
                if (selectedOption.equals("Sort by")) {
                    // If the default option is selected, clear the highlight and update the table
                    // model
                    Controller.getInstance().getStockList().sortBy("Sort By");
                    TableHelper.clearLastHighLight(table);
                    TableHelper.updateTableModel(tableModel);
                } else {
                    // Sort the stock list by the selected option and update the table model
                    Controller.getInstance().getStockList().sortBy(selectedOption);
                    TableHelper.highLightSelected(selectedOption, table);
                    TableHelper.updateTableModel(tableModel);
                }
            }
        });
    }

    /**
     * Adds an action listener to the add button to add the most recent stock to the stock list.
     *
     * @param addButton the add button
     * @param frame the frame to display messages
     * @param tableModel the table model to update the table
     */
    public static void addAddButtonListener(JButton addButton, JFrame frame,
            DefaultTableModel tableModel) {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the most recent stock is not null
                if (mostRecentStock != null) {
                    // Add the most recent stock to the stock list and update the table model
                    Controller.getInstance().getStockList().addStock(mostRecentStock);
                    TableHelper.updateTableModel(tableModel);
                } else {
                    // Display a warning message if no stock has been searched
                    JOptionPane.showMessageDialog(frame, "You have to search Stock first",
                            "No Search Exception", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    /**
     * Adds an action listener to the remove button to remove the selected row from the stock list.
     *
     * @param removeButton the remove button
     * @param table the table containing the stock list
     * @param tableModel the table model to update the table
     * @param frame the frame to display messages
     */
    public static void addRemoveButtonListener(JButton removeButton, JTable table,
            DefaultTableModel tableModel, JFrame frame) {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRow = table.getSelectedRow();

                // Check if a row is selected
                if (selectedRow != -1) {
                    // Remove the selected row from the table model and the stock list
                    tableModel.removeRow(selectedRow);
                    Controller.getInstance().getStockList().removeById(selectedRow);
                    TableHelper.updateTableModel(tableModel);
                } else {
                    // Display a warning message if no row is selected
                    JOptionPane.showMessageDialog(frame, "Select a row to remove.", "No selection",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    /**
     * Adds an action listener to the date picker to handle date selection and update the table
     * model.
     *
     * @param datePicker the PromptDatePicker component
     * @param controller the Controller to handle data fetching
     * @param tableSingle the table model for displaying single stock data
     */
    public static void addDatePickerListener(PromptDatePicker datePicker, Controller controller,
            DefaultTableModel tableSingle) {
        datePicker.getModel().addChangeListener(e -> {
            LocalDate selectedDate = datePicker.getSelectedDate();
            if (selectedDate != null) {
                if (!datePicker.isDateWithinRange(selectedDate)) {
                    JOptionPane.showMessageDialog(null,
                            "Selected date is outside the valid range: " + selectedDate,
                            "Date Out of Range", JOptionPane.WARNING_MESSAGE);
                    TableHelper.updateModelSingle(new Stock(), tableSingle); // Clear or reset the
                                                                             // table
                } else {
                    Stock specificStock =
                            controller.fetchSpecificStockDate(java.sql.Date.valueOf(selectedDate));
                    if (specificStock != null) {
                        TableHelper.updateModelSingle(specificStock, tableSingle);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No stock data available for the selected date: " + selectedDate,
                                "Data Not Found", JOptionPane.WARNING_MESSAGE);
                        TableHelper.updateModelSingle(new Stock(), tableSingle); // Clear or reset
                                                                                 // the table
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Selected date is invalid. Please select a valid date.", "Invalid Date",
                        JOptionPane.WARNING_MESSAGE);
                TableHelper.updateModelSingle(new Stock(), tableSingle); // Clear or reset the table
            }
        });
    }

    /**
     * Validates if th
     *
     * @param value the date to validate
     * @param today today's date
     * @return true if the date is before or equal to today, false otherwise
     */
    public static boolean isDateValid(Date value, LocalDate today) {
        // Convert the Date object to an Instant object for easier manipulation
        Instant instant = value.toInstant();

        // Convert the Instant object to a LocalDate object, taking the system's default time zone
        // into account
        LocalDate inputAsLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        // Compare the input date with today's date and return true if the input date is before or
        // equal to today
        return inputAsLocalDate.isBefore(today) || inputAsLocalDate.isEqual(today);
    }
}
