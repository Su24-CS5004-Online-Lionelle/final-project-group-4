package view.helpers;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A custom JPanel to display the min and max dates on top of the XChart panel.
 */
public class DateRangePanel extends JPanel {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final Date minDate;
    private final Date maxDate;

    /**
     * Constructs a DateRangePanel with the given min and max dates.
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
     * Paints the custom panel with min and max dates.
     *
     * @param g the Graphics object to paint on
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set font and color
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.setColor(Color.WHITE); // Ensure the text is visible on dark backgrounds

        // Draw the min and max dates
        String minDateStr = "Min Date: " + DATE_FORMAT.format(minDate);
        String maxDateStr = "Max Date: " + DATE_FORMAT.format(maxDate);

        g2d.drawString(minDateStr, 10, 20); // Adjust the x and y coordinates as needed
        g2d.drawString(maxDateStr, getWidth() - g2d.getFontMetrics().stringWidth(maxDateStr) - 10,
                20);
    }
}
