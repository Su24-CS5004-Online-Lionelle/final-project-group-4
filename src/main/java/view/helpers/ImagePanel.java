package view.helpers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Helper class for creating a place-holder image for the chart.
 */
public class ImagePanel extends JPanel {

    /**
     * The image to be displayed.
     */
    private Image Image;

    /**
     * Constructs an ImagePanel with the specified image path.
     * The image is loaded from the given path and displayed as the background of the panel.
     *
     * @param imagePath the path to the image file
     */
    public ImagePanel(String imagePath) {
        try {
            Image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paints the component by drawing the image as the background.
     * If the image is not null, it is drawn to fit the size of the panel.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Image != null) {
            g.drawImage(Image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
