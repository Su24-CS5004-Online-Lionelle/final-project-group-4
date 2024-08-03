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

public class HintTextFieldHelper {

    public static class HintTextField extends JTextField implements FocusListener {
        private final String hint;
        private boolean showingHint;

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

        @Override
        public void focusGained(FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText("");
                this.setForeground(Color.BLACK);
                showingHint = false;
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText(hint);
                this.setForeground(Color.GRAY);
                showingHint = true;
            }
        }

        @Override
        public String getText() {
            return showingHint ? "" : super.getText();
        }
    }

    public static class RoundedBorder extends AbstractBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2d.dispose();
        }
    }
}
