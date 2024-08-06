package view.helpers;

import controller.Controller;
import model.DataMgmt.Stock;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.imageio.ImageIO;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.swing.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom date picker component with a prompt text.
 */
public class PromptDatePicker extends JDatePickerImpl {

    /** Track calendar visibility. */
    private boolean calendarVisible;

    /** The minimum selectable date. */
    private LocalDate minDate;

    /** The maximum selectable date. */
    private LocalDate maxDate;

    /** The controller used for fetching stock data. */
    private final Controller controller;

    /** The prompt icon. */
    private final ImageIcon promptIcon;

    /** The menu to show calendar. */
    private JPopupMenu popupMenu;

    /** The icon button to show the calendar. */
    private JButton iconButton;

    /** Track if the last click was on the button. */
    private boolean lastClickOnButton;

    /** Indicates if the search query has been used. */
    private static boolean isSearchQueryUsed = false;

    /**
     * Constructor for PromptDatePicker.
     *
     * @param datePanel the date panel
     * @param formatter the date formatter
     * @param controller the controller for fetching data
     */
    public PromptDatePicker(JDatePanelImpl datePanel,
            JFormattedTextField.AbstractFormatter formatter, Controller controller) {
        super(datePanel, formatter);
        this.calendarVisible = false;
        this.controller = controller;

        // Assuming button size should match the icon size
        int iconWidth = 24;
        int iconHeight = 24;
        this.promptIcon = createScaledIcon("libs/images/calendar.png", iconWidth, iconHeight);
        this.popupMenu = new JPopupMenu();
        this.popupMenu.add(datePanel);
        this.lastClickOnButton = false; // Initialize the flag

        // Hide the text field and replace with an icon button
        JFormattedTextField textField = getJFormattedTextField();
        textField.setVisible(false);

        // Create an icon button to trigger the calendar popup
        iconButton = new JButton(promptIcon);
        iconButton.setBorder(BorderFactory.createEmptyBorder());
        iconButton.setContentAreaFilled(false);
        iconButton.setFocusPainted(false);

        // Add the icon button to the date picker
        setLayout(new BorderLayout());
        add(iconButton, BorderLayout.CENTER);

        // Add action listener to toggle the calendar popup
        iconButton.addActionListener(e -> {
            if (calendarVisible) {
                hideCalendar();
            } else {
                showCalendar();
            }
        });

        // Add a mouse listener to hide the calendar if clicked outside
        addGlobalMouseListener();

        // Adding action listener to disable date selection until search is used
        datePanel.addActionListener(e -> {
            if (!isSearchQueryUsed) {
                DialogHelper.showSingleDialog("Please perform a search query first.",
                        "Action Required", DialogHelper.DialogState.ACTION_REQUIRED);
            } else {
                UtilDateModel model = (UtilDateModel) datePanel.getModel();
                if (model.getValue() == null) {
                    // Handle clear date action here
                    clearDate();
                }
            }
        });

        // Add action listener to clear the date if the text field is empty
        textField.addActionListener(e -> {
            if (textField.getText().isEmpty()) {
                clearDate();
            }
        });
    }

    /**
     * Creates a scaled icon from the specified path.
     *
     * @param path the path to the image file
     * @param width the desired width of the icon
     * @param height the desired height of the icon
     * @return the scaled ImageIcon
     */
    private ImageIcon createScaledIcon(String path, int width, int height) {
        try {
            // Load the image from the file system
            BufferedImage img = ImageIO.read(new File(path));

            // Scale the image
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            return new ImageIcon(scaledImg);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Icon file not found: " + path);
        }
    }

    /**
     * Shows the calendar popup menu.
     */
    private void showCalendar() {
        popupMenu.show(iconButton, 0, iconButton.getHeight());
        calendarVisible = true;
    }

    /**
     * Hides the calendar popup menu.
     */
    private void hideCalendar() {
        popupMenu.setVisible(false);
        calendarVisible = false;
        lastClickOnButton = true; // Reset the flag when hiding the calendar
    }

    /**
     * Adds a global mouse listener to hide the calendar if clicked outside.
     */
    private void addGlobalMouseListener() {
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event instanceof MouseEvent) {
                MouseEvent mouseEvent = (MouseEvent) event;
                if (mouseEvent.getID() == MouseEvent.MOUSE_PRESSED) {
                    if (iconButton.getBounds().contains(mouseEvent.getPoint())) {
                        lastClickOnButton = true;
                    } else {
                        lastClickOnButton = false;
                    }
                }
                if (calendarVisible && !lastClickOnButton
                        && !popupMenu.getBounds().contains(mouseEvent.getPoint())) {
                    hideCalendar();
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }

    /**
     * Clears the date from the text field without triggering an error.
     */
    private void clearDate() {
        JFormattedTextField textField = getJFormattedTextField();
        textField.setValue(null);
        // Additional logic to handle clearing the date without triggering an error
        // You can also reset other states if needed
    }

    /**
     * Sets the flag to indicate if the search query has been used.
     *
     * @param used true if the search query has been used, false otherwise
     */
    public static void setSearchQueryUsed(boolean used) {
        isSearchQueryUsed = used;
    }

    /**
     * Checks if the search query has been used.
     *
     * @return true if the search query has been used, false otherwise
     */
    public static boolean isSearchQueryUsed() {
        return isSearchQueryUsed;
    }

    /**
     * Parses the selected date from the text field.
     *
     * @return the parsed date or null if parsing fails
     */
    public Date parseDate() {
        try {
            String text = getJFormattedTextField().getText();
            if (text != null && !text.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.parse(text);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the selected date as a LocalDate object.
     *
     * @return the selected date or null if no date is selected
     */
    public LocalDate getSelectedDate() {
        Date date = parseDate();
        if (date != null) {
            return LocalDate.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
        }
        return null;
    }

    /**
     * Updates the date picker range based on the provided dates.
     *
     * @param dates the list of dates to set as the selectable range
     */
    public void updateDatePickerRange(List<LocalDate> dates) {
        if (dates != null && !dates.isEmpty()) {
            minDate = dates.stream().min(LocalDate::compareTo).orElse(LocalDate.now());
            maxDate = dates.stream().max(LocalDate::compareTo).orElse(LocalDate.now());
        } else {
            minDate = null;
            maxDate = null;
            UtilDateModel model = (UtilDateModel) getModel();
            model.setValue(null);
        }
    }

    /**
     * Checks if the selected date is within the valid range.
     *
     * @param date the date to check
     * @return true if the date is within the range, false otherwise
     */
    public boolean isDateWithinRange(LocalDate date) {
        return (date != null && minDate != null && maxDate != null && !date.isBefore(minDate)
                && !date.isAfter(maxDate));
    }

    /**
     * Updates the date range based on the data read from an XML file.
     */
    public void updateDateRangeFromXML() {
        List<Stock> stockData = controller.readTempStockDataFromXML();
        List<LocalDate> dates = stockData.stream().map(stock -> LocalDate.parse(stock.getDate()))
                .collect(Collectors.toList());
        updateDatePickerRange(dates);
    }

    /**
     * Resets the lastClickOnButton flag.
     */
    public void resetLastClickOnButton() {
        this.lastClickOnButton = false;
    }
}
