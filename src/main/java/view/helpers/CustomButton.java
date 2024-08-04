package view.helpers;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {
    private Color backgroundColor;
    private Color pressedColor;
    private Color foregroundColor;
    private Color borderColor;
    private boolean isPressed = false;

    public CustomButton(String text) {
        super(text);
        initialize();
    }

    private void initialize() {
        backgroundColor = new Color(160, 235, 200);
        pressedColor = new Color(80, 150, 110); // Slightly darker color for pressed state
        foregroundColor = Color.BLACK;
        borderColor = new Color(160, 235, 200);
        setFont(new Font("Arial", Font.PLAIN, 16));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setForeground(foregroundColor);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorderPainted(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorderPainted(false);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the background
        g2.setColor(isPressed ? pressedColor : backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Draw the border
        g2.setColor(isPressed ? pressedColor :borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        // Draw the text
        g2.setColor(isPressed ? Color.WHITE :foregroundColor);
        g2.setFont(getFont());
        int stringWidth = g2.getFontMetrics().stringWidth(getText());
        int stringHeight = g2.getFontMetrics().getAscent();
        g2.drawString(getText(), (getWidth() - stringWidth) / 2, (getHeight() + stringHeight) / 2 - 3);

        g2.dispose();
    }

    @Override
    public void paintBorder(Graphics g) {
        // Do not call super.paintBorder(g) to avoid default border painting
    }
}
