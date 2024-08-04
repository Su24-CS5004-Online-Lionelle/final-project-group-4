package view.helpers;

import controller.Controller;
import model.DataMgmt.Stock;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    /** The prompt text to be displayed in the date picker. */
    private final String promptText;

    /** Indicates if the prompt text is currently being shown. */
    private boolean showingPrompt;

    /** The minimum selectable date. */
    private LocalDate minDate;

    /** The maximum selectable date. */
    private LocalDate maxDate;

    /** The controller used for fetching stock data. */
    private final Controller controller;

    /** Indicates if the search query has been used. */
    private static boolean isSearchQueryUsed = false;

    /**
     * Constructor for PromptDatePicker.
     *
     * @param datePanel the date panel
     * @param formatter the date formatter
     * @param promptText the prompt text
     * @param controller the controller for fetching data
     */
    public PromptDatePicker(JDatePanelImpl datePanel,
            JFormattedTextField.AbstractFormatter formatter, String promptText,
            Controller controller) {
        super(datePanel, formatter);
        this.promptText = promptText;
        this.showingPrompt = true;
        this.controller = controller;
        setPrompt();

        // Adding focus listener to handle prompt text behavior
        getJFormattedTextField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingPrompt) {
                    getJFormattedTextField().setText("");
                    getJFormattedTextField().setForeground(Color.BLACK);
                    showingPrompt = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getJFormattedTextField().getText().isEmpty()) {
                    setPrompt();
                }
            }
        });

        // Adding mouse listener to handle prompt text behavior on click
        getJFormattedTextField().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isSearchQueryUsed) {
                    DialogHelper.showSingleDialog("Please use the search query first.",
                            "Action Required", DialogHelper.DialogState.ACTION_REQUIRED);
                }
            }
        });

        // Adding action listener to disable date selection until search is used
        datePanel.addActionListener(e -> {
            if (!isSearchQueryUsed) {
                DialogHelper.showSingleDialog("Please perform a search query first.",
                        "Action Required", DialogHelper.DialogState.ACTION_REQUIRED);
            }
        });
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
     * Sets the prompt text in the text field and updates the foreground color.
     */
    private void setPrompt() {
        getJFormattedTextField().setForeground(Color.GRAY);
        getJFormattedTextField().setText(promptText);
        showingPrompt = true;
    }

    /**
     * Parses the selected date from the text field.
     *
     * @return the parsed date or null if parsing fails
     */
    public Date parseDate() {
        try {
            String text = getJFormattedTextField().getText();
            if (text != null && !text.isEmpty() && !text.equals(promptText)) {
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
}
