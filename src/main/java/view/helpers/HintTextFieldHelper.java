package view.helpers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.AbstractBorder;

/**
 * Helper class for creating JTextFields with hint text and rounded borders.
 */
public class HintTextFieldHelper {

    /**
     * Custom JTextField that displays a hint when the field is empty and not focused.
     */
    public static class HintTextField extends JTextField implements FocusListener {
        /**
         * The hint text to be displayed.
         */
        private final String hint;

        /**
         * Flag to indicate whether the hint is currently being displayed.
         */
        private boolean showingHint;

        /**
         * Constructor for HintTextField.
         *
         * @param hint the hint text to display when the field is empty and not focused
         */
        public HintTextField(String hint) {
            super(hint);
            this.hint = hint;
            this.showingHint = true;
            super.addFocusListener(this);
            this.setForeground(Color.GRAY);
            // Set a round border
            this.setBorder(new RoundedBorder(10));
            // Add padding to top, left, bottom, right
            this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(),
                    BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        }

        /**
         * Called when the JTextField gains focus. Removes the hint if it is currently displayed.
         *
         * @param e the FocusEvent
         */
        @Override
        public void focusGained(FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText("");
                this.setForeground(Color.BLACK);
                showingHint = false;
            }
        }

        /**
         * Called when the JTextField loses focus. Displays the hint if the field is empty.
         *
         * @param e the FocusEvent
         */
        @Override
        public void focusLost(FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText(hint);
                this.setForeground(Color.GRAY);
                showingHint = true;
            }
        }

        /**
         * Returns the text currently displayed in the JTextField. If the hint is being displayed,
         * returns an empty string.
         *
         * @return the text in the JTextField, or an empty string if the hint is being displayed
         */
        @Override
        public String getText() {
            return showingHint ? "" : super.getText();
        }
    }

    /**
     * Custom border with rounded corners.
     */
    public static class RoundedBorder extends AbstractBorder {
        /**
         * The radius of the rounded corners.
         */
        private final int radius;

        /**
         * Constructor for RoundedBorder.
         *
         * @param radius the radius of the rounded corners
         */
        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        /**
         * Paints the border with rounded corners.
         *
         * @param c the component for which this border is being painted
         * @param g the graphics context
         * @param x the x position of the border
         * @param y the y position of the border
         * @param width the width of the border
         * @param height the height of the border
         */
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(6));
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2d.dispose();
        }
    }
}
