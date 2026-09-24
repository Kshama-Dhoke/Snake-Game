package sankeGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartScreen extends JFrame {

    private JButton playButton;

    StartScreen() {

        setTitle("Snake Game");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK);

        // Game title
        JLabel title = new JLabel("WELCOME TO THE GAME");
        title.setFont(new Font("Monospaced", Font.BOLD, 28));
        title.setForeground(new Color(80, 200, 80));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        title.setBounds(40, 130, 420, 50);

        panel.add(title);

        // Play button
        playButton = new JButton("▶  PLAY GAME");

        playButton.setFont(new Font("Monospaced", Font.BOLD, 18));
        playButton.setForeground(Color.WHITE);
        playButton.setBackground(new Color(60, 140, 60));

        playButton.setFocusPainted(false);
        playButton.setBorderPainted(false);
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        playButton.setBounds(150, 230, 200, 55);

        panel.add(playButton);

        // Start game
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

                new SankeGame();
            }
        });

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new StartScreen();
    }
}