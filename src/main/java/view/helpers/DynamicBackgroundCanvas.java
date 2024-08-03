package view.helpers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.Random;

public class DynamicBackgroundCanvas extends JPanel implements Runnable {
    private BufferedImage backgroundImage;
    private Thread thread;
    private float[] priceAction;
    private Random random;

    public DynamicBackgroundCanvas() {
        try {
            // Load the background image using the absolute path
            backgroundImage = ImageIO.read(new File("libs/images/stockmarket.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize price action array and random generator
        priceAction = new float[800];
        random = new Random();

        // Generate initial price action
        generateInitialPriceAction();

        // Start the rendering thread
        thread = new Thread(this);
        thread.start();
    }

    private void generateInitialPriceAction() {
        float price = 300; // Starting price
        for (int i = 0; i < priceAction.length; i++) {
            price += (random.nextFloat() - 0.5) * 10; // Random walk
            priceAction[i] = price;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Draw dynamic content
        drawPriceAction(g);
    }

    private void drawPriceAction(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        int width = getWidth();
        int height = getHeight();

        // Calculate the x step to scale the priceAction array to the panel width
        float xStep = (float) width / (priceAction.length - 1);
        int middle = height / 2; // Middle of the panel

        for (int i = 0; i < priceAction.length - 1; i++) {
            int x1 = Math.round(i * xStep);
            int x2 = Math.round((i + 1) * xStep);
            int y1 = middle - (int) (priceAction[i] - 300); // Center the price action vertically
            int y2 = middle - (int) (priceAction[i + 1] - 300);

            // Color variation
            float hue = (float) i / priceAction.length; // Change hue based on position in array
            g2d.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));

            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    private void animate() {
        while (true) {
            // Update price action for animation
            for (int i = 0; i < priceAction.length - 1; i++) {
                priceAction[i] = priceAction[i + 1]; // Shift prices to the left
            }
            priceAction[priceAction.length - 1] += (random.nextFloat() - 0.5) * 10; // New price at
                                                                                    // the end

            repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        animate();
    }
}
