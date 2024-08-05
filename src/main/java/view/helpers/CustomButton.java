package view.helpers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.ImageIcon;

/**
 * Custom button with custom colors and antialiasing. This class handles regular buttons with custom
 * colors and images.
 */
public class CustomButton extends JButton {
    /**
     * The background color of the button.
     */
    private Color backgroundColor;

    /**
     * The color of the button when pressed.
     */
    private Color pressedColor;

    /**
     * The foreground color (text color) of the button.
     */
    private Color foregroundColor;

    /**
     * The border color of the button.
     */
    private Color borderColor;

    /**
     * Indicates whether the button is currently pressed.
     */
    private boolean isPressed = false;

    /**
     * Indicates if the button is image-only.
     */
    private boolean isImageButton = false; // Indicates if the button is image-only

    /**
     * Constructs a CustomButton with text.
     *
     * @param text the text for the button
     */
    public CustomButton(String text) {
        super(text);
        initialize();
    }

    /**
     * Constructs a CustomButton with an icon image.
     *
     * @param icon the ImageIcon for the button
     */
    public CustomButton(ImageIcon icon) {
        super(icon);
        isImageButton = true;
        initialize();
    }

    /**
     * Initializes the custom button with default settings.
     */
    private void initialize() {
        backgroundColor = new Color(160, 235, 200);
        pressedColor = new Color(80, 150, 110);
        foregroundColor = Color.BLACK;
        borderColor = new Color(160, 235, 200);
        setFont(new Font("Arial", Font.PLAIN, 16));
        setFocusPainted(false);
        setContentAreaFilled(false); // Ensures no default background painting
        setOpaque(false); // Ensures no background painting
        setBorderPainted(false); // Ensures no border painting
        setForeground(foregroundColor);

        // Add a focus listener to manage border painting on focus
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

        // Add a mouse listener to manage pressed state
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

    /**
     * Paints the button component.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isImageButton) {
            // Draw only the image for image-only buttons
            super.paintComponent(g);
        } else {
            // Draw the button background
            g2.setColor(isPressed ? pressedColor : backgroundColor);
            g2.fillRect(0, 0, getWidth(), getHeight()); // Ensure square corners

            // Draw the button border
            g2.setColor(borderColor);
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // Ensure square corners

            // Draw the button text
            g2.setColor(foregroundColor);
            g2.setFont(getFont());
            int stringWidth = g2.getFontMetrics().stringWidth(getText());
            int stringHeight = g2.getFontMetrics().getAscent();
            g2.drawString(getText(), (getWidth() - stringWidth) / 2,
                    (getHeight() + stringHeight) / 2 - 3);
        }

        g2.dispose();
    }

    /**
     * Sets the pressed color for the button.
     *
     * @param pressedColor the new pressed color
     */
    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    /**
     * Sets the border color for the button.
     *
     * @param borderColor the new border color
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
