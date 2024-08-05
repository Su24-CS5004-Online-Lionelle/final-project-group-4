package view.helpers;

import java.awt.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A custom JPanel to display the minimum and maximum dates on top of the XChart panel.
 */
public class DateRangePanel extends JPanel {

    /** Date format for displaying dates. */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /** The minimum date to display. */
    private final Date minDate;

    /** The maximum date to display. */
    private final Date maxDate;

    /**
     * Constructs a DateRangePanel with the given minimum and maximum dates.
     *
     * @param minDate the minimum date to display
     * @param maxDate the maximum date to display
     */
    public DateRangePanel(Date minDate, Date maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
        setPreferredSize(new Dimension(800, 30)); // Set the preferred size to fit your chart width
    }

    /**
     * Paints the custom panel with the minimum and maximum dates.
     *
     * @param g the Graphics object to paint on
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smoother text rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set font and color for the text
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.setColor(Color.WHITE); // Ensure the text is visible on dark backgrounds

        // Format the minimum and maximum dates as strings
        String minDateStr = "Min Date: " + DATE_FORMAT.format(minDate);
        String maxDateStr = "Max Date: " + DATE_FORMAT.format(maxDate);

        // Draw the minimum date at the left side of the panel
        g2d.drawString(minDateStr, 10, 20); // Adjust the x and y coordinates as needed

        // Draw the maximum date at the right side of the panel
        g2d.drawString(maxDateStr, getWidth() - g2d.getFontMetrics().stringWidth(maxDateStr) - 10,
                20);
    }
}
