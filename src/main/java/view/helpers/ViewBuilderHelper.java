package view.helpers;

import java.awt.*;

import java.time.LocalDate;

import java.util.Properties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
                // Set frame size and default close operation
                frame.setSize(1400, 840);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(null);

                // Create the dynamic background canvas
                DynamicBackgroundCanvas canvas = new DynamicBackgroundCanvas();
                canvas.setLayout(null);
                frame.setContentPane(canvas);

                // Define larger fonts for text and headers
                Font font = new Font("Arial", Font.PLAIN, 16);
                Font headerFont = new Font("Arial", Font.BOLD, 16);

                // Add a logo to the top left
                JLabel logoLabel = new JLabel(new ImageIcon("libs/images/logo.png"));
                logoLabel.setBounds(50, 50, 500, 80);
                frame.add(logoLabel);

                // Create HintTextField instance with a hint
                HintTextField textField = new HintTextField(" Enter a stock symbol");
                textField.setBounds(50, 148, 510, 34);
                textField.setFont(font);
                frame.add(textField);

                // Create search Button instance
                CustomButton searchButton = new CustomButton("Search");
                searchButton.setBounds(580, 150, 100, 30);
                frame.add(searchButton);

                // Create add Button instance
                CustomButton addButton = new CustomButton("Add");
                addButton.setBounds(410, 270, 100, 30);
                frame.add(addButton);

                // Create remove Button instance
                CustomButton removeButton = new CustomButton("Delete");
                removeButton.setBounds(530, 270, 100, 30);
                frame.add(removeButton);

                // Create clear Button instance
                CustomButton clearButton = new CustomButton("Clear");
                clearButton.setBounds(650, 270, 100, 30);
                frame.add(clearButton);

                // Create import Button instance
                CustomButton importButton = new CustomButton("Import");
                importButton.setBounds(530, 750, 100, 30);
                frame.add(importButton);

                // Create export Button instance
                CustomButton exportButton = new CustomButton("Export");
                exportButton.setBounds(650, 750, 100, 30);
                frame.add(exportButton);

                // Create push Button instance
                CustomButton pushButton = new CustomButton("Rand");
                pushButton.setBounds(50, 750, 100, 30);
                frame.add(pushButton);

                // Create help Button instance
                CustomButton helpButton = new CustomButton("Help");
                helpButton.setBounds(1250, 750, 100, 30);
                frame.add(helpButton);

                // Create JComboBox for sort options
                String[] sortOptions = {"Symbol", "Date", "Open", "High", "Low", "Close", "Volume"};
                JComboBox<String> sortByComboBox = new JComboBox<>(sortOptions);
                sortByComboBox.setBounds(45, 270, 120, 30);
                sortByComboBox.setFont(font);
                frame.add(sortByComboBox);

                // Create JScrollPane and JTextArea
                textArea.setFont(font);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setBounds(800, 480, 550, 240); // Set position and size of JScrollPane
                frame.add(scrollPane); // Add JScrollPane to JFrame

                // Create JTable and JScrollPane for multiple records
                String[] columnNames = {"Symbol", "Date", "Open", "High", "Low", "Close", "Volume"};
                DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                JTable table = new JTable(tableModel);
                table.setFont(font);
                table.setRowHeight(30); // Adjust row height
                table.setBackground(Color.WHITE); // Set table background color
                JTableHeader tableHeader = table.getTableHeader();
                tableHeader.setFont(headerFont); // Set the larger font for header
                tableHeader.setBackground(Color.LIGHT_GRAY); // Set header color
                JScrollPane tableScrollPane = new JScrollPane(table);
                tableScrollPane.setBounds(50, 320, 700, 400); // Set position and size of table
                tableScrollPane.getViewport().setBackground(Color.WHITE); // Set viewport background
                                                                          // color
                frame.add(tableScrollPane); // Add table JScrollPane to JFrame

                // Create JTable and JScrollPane for single result of search
                DefaultTableModel tableSingle = new DefaultTableModel(columnNames, 0);
                JTable singleTable = new JTable(tableSingle);
                singleTable.setFont(font);
                singleTable.setRowHeight(35); // Adjust row height
                singleTable.setBackground(Color.WHITE); // Set table background color
                JTableHeader singleTableHeader = singleTable.getTableHeader();
                singleTableHeader.setFont(headerFont); // Set the larger font for header
                singleTableHeader.setBackground(Color.LIGHT_GRAY); // Set header color
                JScrollPane tableScrollPaneSingle = new JScrollPane(singleTable);
                tableScrollPaneSingle.setBounds(50, 200, 700, 50); // Set position and size of table
                tableScrollPaneSingle.getViewport().setBackground(Color.WHITE); // Set viewport
                                                                                // background color
                frame.add(tableScrollPaneSingle);

                // Clear any existing rows from the table model to ensure it starts empty
                tableModel.setRowCount(0);

                // Create JPanel for chart
                chartPanel.setBounds(800, 150, 550, 300);
                chartPanel.setBackground(Color.WHITE);
                frame.add(chartPanel);

                // Create and add the date picker
                UtilDateModel model = new UtilDateModel();
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                PromptDatePicker datePicker = new PromptDatePicker(datePanel,
                                new DateLabelFormatter(), controller);
                datePicker.setBounds(700, 150, 50, 30);
                datePicker.getJFormattedTextField().setFont(font);
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

                // Update the table model with a delay
                TableHelper.updateTableModel(1, tableModel);

                // set the first column default to gray
                TableHelper.setFirstColumnGray(table);
        }
}
