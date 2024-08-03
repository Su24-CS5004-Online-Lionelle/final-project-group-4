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

public class PromptDatePicker extends JDatePickerImpl {

    private final String promptText;
    private boolean showingPrompt;
    private LocalDate minDate;
    private LocalDate maxDate;
    private final Controller controller;
    private boolean calendarOpened;
    private int clickCount;

    public PromptDatePicker(JDatePanelImpl datePanel,
            JFormattedTextField.AbstractFormatter formatter, String promptText,
            Controller controller) {
        super(datePanel, formatter);
        this.promptText = promptText;
        this.showingPrompt = true;
        this.calendarOpened = false;
        this.clickCount = 0;
        this.controller = controller;
        setPrompt();

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

        getJFormattedTextField().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickCount++;
                if (clickCount == 1) {
                    calendarOpened = true;
                    clickCount = 0;
                }
            }
        });

        datePanel.addActionListener(e -> {
            if (calendarOpened) {
                handleDateSelection();
                calendarOpened = false;
            }
        });
    }

    private void setPrompt() {
        getJFormattedTextField().setForeground(Color.GRAY);
        getJFormattedTextField().setText(promptText);
        showingPrompt = true;
    }

    private void handleDateSelection() {
        LocalDate selectedDate = getSelectedDate();
        System.out.println("Selected Date: " + selectedDate);
    }

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

    public LocalDate getSelectedDate() {
        Date date = parseDate();
        if (date != null) {
            return LocalDate.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
        }
        return null;
    }

    public void updateDatePickerRange(List<LocalDate> dates) {
        if (dates != null && !dates.isEmpty()) {
            minDate = dates.stream().min(LocalDate::compareTo).orElse(LocalDate.now());
            maxDate = dates.stream().max(LocalDate::compareTo).orElse(LocalDate.now());
        } else {
            minDate = null;
            maxDate = null;
            UtilDateModel model = (UtilDateModel) getModel();
            model.setValue(null);
            JOptionPane.showMessageDialog(null, "No stock data available for the symbol.",
                    "Data Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean isDateWithinRange(LocalDate date) {
        return (date != null && minDate != null && maxDate != null && !date.isBefore(minDate)
                && !date.isAfter(maxDate));
    }

    public void updateDateRangeFromXML() {
        List<Stock> stockData = controller.readTempStockDataFromXML();
        List<LocalDate> dates = stockData.stream().map(stock -> LocalDate.parse(stock.getDate()))
                .collect(Collectors.toList());
        updateDatePickerRange(dates);
    }
}
