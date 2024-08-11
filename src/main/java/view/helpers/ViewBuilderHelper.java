package view.helpers;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;
import java.util.Properties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controller.Controller;
import model.Model;
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
                canvas.putClientProperty("name", "dynamicBackgroundCanvas"); // set name for testing
                frame.setContentPane(canvas);

                // Define larger fonts for text and headers
                Font font = new Font("Arial", Font.PLAIN, 16);
                Font headerFont = new Font("Arial", Font.BOLD, 16);

                // Add a logo to the top left
                JLabel logoLabel = new JLabel(new ImageIcon("libs/images/logo.png"));
                logoLabel.setBounds(50, 30, 500, 80);
                logoLabel.putClientProperty("name", "logoLabel"); // set name for testing
                frame.add(logoLabel);

                // Create HintTextField instance with a hint
                HintTextField textField = new HintTextField(" Enter a stock symbol");
                textField.setBounds(50, 128, 510, 34);
                textField.setFont(font);
                textField.putClientProperty("name", "textField"); // set name for testing
                frame.add(textField);

                // Create search Button instance
                CustomButton searchButton = new CustomButton("Search");
                searchButton.setBounds(580, 130, 100, 30);
                searchButton.putClientProperty("name", "searchButton"); // set name for testing
                frame.add(searchButton);

                // Create add Button instance
                CustomButton addButton = new CustomButton("Add");
                addButton.setBounds(410, 250, 100, 30);
                addButton.putClientProperty("name", "addButton"); // set name for testing
                frame.add(addButton);

                // Create remove Button instance
                CustomButton removeButton = new CustomButton("Delete");
                removeButton.setBounds(530, 250, 100, 30);
                removeButton.putClientProperty("name", "removeButton"); // set name for testing
                frame.add(removeButton);

                // Create clear Button instance
                CustomButton clearButton = new CustomButton("Clear");
                clearButton.setBounds(650, 250, 100, 30);
                clearButton.putClientProperty("name", "clearButton"); // set name for testing
                frame.add(clearButton);

                // Create import Button instance
                CustomButton importButton = new CustomButton("Import");
                importButton.setBounds(530, 730, 100, 30);
                importButton.putClientProperty("name", "importButton"); // set name for testing
                frame.add(importButton);

                // Create export Button instance
                CustomButton exportButton = new CustomButton("Export");
                exportButton.setBounds(650, 730, 100, 30);
                exportButton.putClientProperty("name", "exportButton"); // set name for testing
                frame.add(exportButton);

                // Create push Button instance
                CustomButton pushButton = new CustomButton("Rand");
                pushButton.setBounds(50, 730, 100, 30);
                pushButton.putClientProperty("name", "pushButton"); // set name for testing
                frame.add(pushButton);

                // Create help Button instance
                CustomButton helpButton = new CustomButton("Help");
                helpButton.setBounds(1250, 730, 100, 30);
                helpButton.putClientProperty("name", "helpButton"); // set name for testing
                frame.add(helpButton);

                // Create API key Button instance
                CustomButton apiDialogButton = new CustomButton("API Key");
                apiDialogButton.setBounds(1130, 730, 100, 30);
                apiDialogButton.putClientProperty("name", "apiDialogButton"); // set name for
                                                                              // testing
                frame.add(apiDialogButton);

                // Create JComboBox for sort options
                String[] sortOptions = {"Symbol", "Date", "Open", "High", "Low", "Close", "Volume"};
                JComboBox<String> sortByComboBox = new JComboBox<>(sortOptions);
                sortByComboBox.setBounds(45, 250, 120, 30);
                sortByComboBox.setFont(font);
                sortByComboBox.putClientProperty("name", "sortByComboBox"); // set name for testing
                frame.add(sortByComboBox);

                // For the JTextArea
                textArea.putClientProperty("name", "textArea"); // set name for testing

                // For the JScrollPane that contains the JTextArea
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setBounds(800, 460, 550, 240); // Set position and size of JScrollPane
                scrollPane.putClientProperty("name", "scrollPane"); // set name for testing
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
                tableScrollPane.setBounds(50, 300, 700, 400); // Set position and size of table
                tableScrollPane.getViewport().setBackground(Color.WHITE); // Set viewport background
                                                                          // color
                tableScrollPane.putClientProperty("name", "tableScrollPane"); // set name for
                                                                              // testing
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
                tableScrollPaneSingle.setBounds(50, 180, 700, 50); // Set position and size of table
                tableScrollPaneSingle.getViewport().setBackground(Color.WHITE); // Set viewport
                                                                                // background color
                tableScrollPaneSingle.putClientProperty("name", "tableScrollPaneSingle"); // set
                                                                                          // name
                                                                                          // for
                                                                                          // testing
                frame.add(tableScrollPaneSingle);

                // Clear any existing rows from the table model to ensure it starts empty
                tableModel.setRowCount(0);

                // Create JPanel for chart
                chartPanel.setBounds(800, 130, 550, 300);
                chartPanel.setOpaque(false); // Make the chart panel non-opaque
                chartPanel.putClientProperty("name", "chartPanel"); // set name for testing
                frame.add(chartPanel);

                // Create the background image panel for chart
                ImagePanel backgroundPanel = new ImagePanel("libs/images/image_chart.png");
                backgroundPanel.setBounds(800, 130, 550, 300);
                backgroundPanel.putClientProperty("name", "backgroundPanel"); // set name for
                                                                              // testing
                frame.add(backgroundPanel);

                // Create and add the date picker
                UtilDateModel model = new UtilDateModel();
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                PromptDatePicker datePicker = new PromptDatePicker(datePanel,
                                new DateLabelFormatter(), controller);
                datePicker.setBounds(700, 130, 50, 30);
                datePicker.getJFormattedTextField().setFont(font);
                datePicker.putClientProperty("name", "datePicker"); // set name for testing
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

                // API String dialog
                apiDialogButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                openInputDialog(frame, textArea);
                        }
                });

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

        private static void openInputDialog(JFrame frame, JTextArea textArea) {
                // create a JDialog
                JDialog dialog = new JDialog(frame, "API Key String", true);

                // set size and close function
                dialog.setSize(300, 100);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setLocationRelativeTo(frame);

                // create JTextField with default data
                JTextField textField = new JTextField(Model.getInstance().getApiKey());
                textField.setPreferredSize(new Dimension(200, 30));

                JPanel panel = new JPanel();
                panel.add(textField);
                dialog.add(panel, BorderLayout.CENTER);

                JButton okButton = new JButton("LOAD");
                okButton.addActionListener(e -> {
                        // get string from input and print
                        String inputText = textField.getText();
                        Model.getInstance().setApiKey(inputText);
                        textArea.setText(Messages.apiMessage(inputText));

                        // close dialog
                        dialog.dispose();
                });
                panel.add(okButton, BorderLayout.SOUTH);

                dialog.setVisible(true);
        }
}
