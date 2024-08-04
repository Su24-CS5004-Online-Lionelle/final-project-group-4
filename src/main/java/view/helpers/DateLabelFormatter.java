package view.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;

import java.util.Calendar;

/**
 * Custom formatter for date labels in JFormattedTextField. Formats dates as strings and parses
 * strings to dates using the "yyyy-MM-dd" pattern.
 */
public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    /**
     * The date pattern used for formatting and parsing dates.
     */
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * SimpleDateFormat instance for formatting and parsing dates.
     */
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

    /**
     * Converts a string to a date object.
     *
     * @param text the string to convert
     * @return the parsed date object
     * @throws ParseException if the text cannot be parsed to a date
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    /**
     * Converts a date object to a string.
     *
     * @param value the date object to convert
     * @return the formatted date string
     * @throws ParseException if the value cannot be formatted to a string
     */
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}
