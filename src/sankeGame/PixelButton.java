package sankeGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PixelButton extends JButton {

    private boolean hover = false;

    public PixelButton(String text) {
        super(text);

        setFont(new Font("Monospaced", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF
        );

        int w = getWidth();
        int h = getHeight();

        // Button colors
        Color darkGreen = new Color(35, 70, 35);
        Color green = new Color(70, 150, 60);
        Color lightGreen = new Color(110, 190, 80);

        if (hover) {
            darkGreen = new Color(50, 100, 45);
            green = new Color(90, 180, 70);
            lightGreen = new Color(150, 220, 100);
        }

        // Shadow
        g2.setColor(Color.BLACK);
        g2.fillRect(6, 6, w - 6, h - 6);

        // Main button
        g2.setColor(darkGreen);
        g2.fillRect(3, 3, w - 6, h - 6);

        // Top and left highlight
        g2.setColor(lightGreen);
        g2.fillRect(3, 3, w - 6, 4);
        g2.fillRect(3, 3, 4, h - 6);

        // Bottom and right dark edge
        g2.setColor(new Color(15, 35, 15));
        g2.fillRect(3, h - 7, w - 6, 4);
        g2.fillRect(w - 7, 3, 4, h - 6);

        // Inner area
        g2.setColor(green);
        g2.fillRect(9, 9, w - 18, h - 18);

        // Pixel-style text
        g2.setFont(new Font("Monospaced", Font.BOLD, 15));

        FontMetrics fm = g2.getFontMetrics();

        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();

        int textX = (w - textWidth) / 2;
        int textY = (h + textHeight) / 2 - 3;

        // Text shadow
        g2.setColor(new Color(20, 50, 20));
        g2.drawString(getText(), textX + 2, textY + 2);

        // Text
        g2.setColor(Color.WHITE);
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }
}