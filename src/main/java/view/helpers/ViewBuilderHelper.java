package view.helpers;

import java.awt.*;
import java.time.LocalDate;
import java.util.Properties;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import view.View;
import view.helpers.HintTextFieldHelper.HintTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * Helper class to build and initialize the view components.
 */
public class ViewBuilderHelper {

        /**
         * The current date used for date-related functionalities.
         */
        private static LocalDate today = LocalDate.now();

        /**
         * Builds and initializes the view components.
         *
         * @param frame the main application frame
         * @param controller the controller to handle interactions
         * @param textArea the text area to display messages
         * @param chartPanel the panel to display charts
         * @param view the view instance to update the UI
         */
        public static void build(JFrame frame, Controller controller, final JTextArea textArea,
                        final JPanel chartPanel, final View view) {
                frame.setSize(1400, 840);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(null);

                // Create the dynamic background canvas
                DynamicBackgroundCanvas canvas = new DynamicBackgroundCanvas();
                canvas.setLayout(null);
                frame.setContentPane(canvas);

                // Create HintTextField instance with a hint
                HintTextField textField = new HintTextField(" Enter a stock symbol");
                textField.setBounds(50, 50, 250, 30);
                frame.add(textField);

                // Create search Button instance
                JButton searchButton = new JButton("Search");
                searchButton.setBounds(470, 50, 80, 30);
                frame.add(searchButton);

                // Create add Button instance
                JButton addButton = new JButton("Add");
                addButton.setBounds(310, 170, 80, 30);
                frame.add(addButton);

                // Create remove Button instance
                JButton removeButton = new JButton("Delete");
                removeButton.setBounds(390, 170, 80, 30);
                frame.add(removeButton);

                // Create import Button instance
                JButton importButton = new JButton("Import");
                importButton.setBounds(390, 520, 80, 30);
                frame.add(importButton);

                // Create export Button instance
                JButton exportButton = new JButton("Export");
                exportButton.setBounds(470, 520, 80, 30);
                frame.add(exportButton);

                // Create help Button instance
                JButton helpButton = new JButton("Help");
                helpButton.setBounds(880, 520, 80, 30);
                frame.add(helpButton);

                // Create clear Button instance
                JButton clearButton = new JButton("Clear");
                clearButton.setBounds(470, 170, 80, 30);
                frame.add(clearButton);

                // Create push Button instance
                JButton pushButton = new JButton("Rand");
                pushButton.setBounds(50, 520, 80, 30);
                frame.add(pushButton);

                // Create JComboBox for sort options
                String[] sortOptions =
                                {"Sort by", "Date", "Open", "High", "Low", "Close", "Volume"};
                JComboBox<String> sortByComboBox = new JComboBox<>(sortOptions);
                sortByComboBox.setBounds(45, 170, 100, 30);
                frame.add(sortByComboBox);

                // Create JScrollPane and JTextArea
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setBounds(570, 270, 380, 230); // Set position and size of JScrollPane
                frame.add(scrollPane); // Add JScrollPane to JFrame

                // Create JTable and JScrollPane for multiple records
                String[] columnNames = {"Symbol", "Date", "Open", "High", "Low", "Close", "Volume"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);
                JScrollPane tableScrollPane = new JScrollPane(table);
                tableScrollPane.setBounds(50, 220, 500, 280); // Set position and size of table
                                                              // JScrollPane
                frame.add(tableScrollPane); // Add table JScrollPane to JFrame

                // Create JTable and JScrollPane for single result of search
                DefaultTableModel tableSingle = new DefaultTableModel(columnNames, 0);
                JTable singleTable = new JTable(tableSingle);
                JScrollPane tableScrollPaneSingle = new JScrollPane(singleTable);
                tableScrollPaneSingle.setBounds(50, 100, 500, 50);
                frame.add(tableScrollPaneSingle);

                // Clear any existing rows from the table model to ensure it starts empty
                tableModel.setRowCount(0);

                // Create JPanel for chart
                chartPanel.setBounds(570, 50, 380, 200);
                chartPanel.setBackground(Color.WHITE);
                frame.add(chartPanel);

                // Create and add the date picker
                // Create and add the date picker
                UtilDateModel model = new UtilDateModel();
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                PromptDatePicker datePicker = new PromptDatePicker(datePanel,
                                new DateLabelFormatter(), "Select a date", controller);
                datePicker.setBounds(310, 50, 150, 30);
                frame.add(datePicker);

                // Set initial welcome text
                textArea.setText(Messages.WELCOME_MESSAGE.getMessage());

                // Add ActionListeners from the ActionListenersHelper
                ActionListenersHelper.addSearchButtonListener(searchButton, textField, model, today,
                                controller, view, textArea, tableSingle, datePicker);
                ActionListenersHelper.addImportButtonListener(importButton, textArea, tableModel,
                                controller);
                ActionListenersHelper.addExportButtonListener(exportButton, textArea);
                ActionListenersHelper.addHelpButtonListener(helpButton, textArea);
                ActionListenersHelper.addClearButtonListener(clearButton, tableModel);
                ActionListenersHelper.addPushButtonListener(pushButton, tableModel);
                ActionListenersHelper.addSortByComboBoxListener(sortByComboBox, tableModel, table);
                ActionListenersHelper.addAddButtonListener(addButton, frame, tableModel);
                ActionListenersHelper.addRemoveButtonListener(removeButton, table, tableModel,
                                frame);
                ActionListenersHelper.addDatePickerListener(datePicker, controller, tableSingle);

                // Center the frame on screen
                frame.setLocationRelativeTo(null);
                // Set JFrame visible
                frame.setVisible(true);
                // Set initial focus on searchButton to avoid focusing on textField
                searchButton.requestFocus();

                TableHelper.updateTableModel(1, tableModel);
        }
}
