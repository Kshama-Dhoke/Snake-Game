package sankeGame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private int dots;

    private Image head;
    private Image body;
    private Image food;

    private final int allDots = 250000;
    private final int dotSize = 25;

    private final int randomPosition = 20;

    private int food_x;
    private int food_y;

    private int score = 0;

    private final int x[] = new int[allDots];
    private final int y[] = new int[allDots];

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame = true;

    private Timer timer;

    private JButton restartButton;


    Board() {

        setPreferredSize(new Dimension(500, 500));

        setBackground(Color.black);
        setFocusable(true);
        setLayout(null);
        loadImage();

        // Restart button
        restartButton = new PixelButton("RESTART");

        restartButton.setFocusable(false);

        // Centered horizontally
        restartButton.setBounds(190, 260, 120, 50);

        restartButton.setVisible(false);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        add(restartButton);

        addKeyListener(new TAdapter());

        initGame();
    }


    public void loadImage() {

        ImageIcon il = new ImageIcon(
                ClassLoader.getSystemResource("icons/turtle.png"));
        head = il.getImage();

        ImageIcon il1 = new ImageIcon(
                ClassLoader.getSystemResource("icons/basket.png"));
        body = il1.getImage();

        ImageIcon il2 = new ImageIcon(
                ClassLoader.getSystemResource("icons/watermelon.png"));
        food = il2.getImage();
    }


    private void initGame() {

        dots = 3;
        score = 0;

        rightDirection = true;
        leftDirection = false;
        upDirection = false;
        downDirection = false;

        inGame = true;

        for (int i = 0; i < dots; i++) {
            y[i] = 100;
            x[i] = 150 - i * dotSize;
        }

        locateFood();

        timer = new Timer(170, this);
        timer.start();
    }


    public void locateFood() {

    	 boolean foodOnSnake;

    	    do {
    	        foodOnSnake = false;

    	        int r = (int) (Math.random() * randomPosition);
    	        food_x = r * dotSize;

    	        r = (int) (Math.random() * randomPosition);
    	        food_y = r * dotSize;

    	        for (int i = 0; i < dots; i++) {

    	            if (x[i] == food_x && y[i] == food_y) {
    	                foodOnSnake = true;
    	                break;
    	            }
    	        }

    	    } while (foodOnSnake);
    	}
    


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        draw(g);
    }


    public void draw(Graphics g) {

        if (inGame) {

            // Draw food
            g.drawImage(
                    food,
                    food_x,
                    food_y,
                    dotSize,
                    dotSize,
                    this
            );

            // Draw snake
            for (int i = 0; i < dots; i++) {

                if (i == 0) {

                    g.drawImage(
                            head,
                            x[i],
                            y[i],
                            dotSize,
                            dotSize,
                            this
                    );

                } else {

                    g.drawImage(
                            body,
                            x[i],
                            y[i],
                            dotSize,
                            dotSize,
                            this
                    );
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }


    public void gameOver(Graphics g) {

        String msg = "Game Over";
        String scoreMsg = "Score: " + score;

        Font gameOverFont = new Font("Monospaced", Font.BOLD, 28);
        Font scoreFont = new Font("Monospaced", Font.BOLD, 18);

        FontMetrics gameOverMetrics = getFontMetrics(gameOverFont);
        FontMetrics scoreMetrics = getFontMetrics(scoreFont);

        // Game Over
        g.setFont(gameOverFont);
        g.setColor(Color.WHITE);

        int gameOverX =
                (getWidth() - gameOverMetrics.stringWidth(msg)) / 2;

        g.drawString(msg, gameOverX, 190);

        // Score
        g.setFont(scoreFont);
        g.setColor(new Color(100, 200, 100));

        int scoreX =
                (getWidth() - scoreMetrics.stringWidth(scoreMsg)) / 2;

        g.drawString(scoreMsg, scoreX, 225);
    }


    public void move() {

        // Move the body
        for (int i = dots; i > 0; i--) {

            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }


        // Move the head
        if (rightDirection) {
            x[0] += dotSize;
        }

        if (leftDirection) {
            x[0] -= dotSize;
        }

        if (upDirection) {
            y[0] -= dotSize;
        }

        if (downDirection) {
            y[0] += dotSize;
        }
    }


    public void checkFood() {

        if ((x[0] == food_x) && (y[0] == food_y)) {

            // Increase snake size
            dots++;

            // Add 10 points
            score += 10;

            // Generate new food
            locateFood();
        }
    }


    public void checkCollision() {

    	 // Collision with snake body
        for (int i = 1; i < dots; i++) {

            if ((x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
                break;
            }
        }


        // Check right wall
        if (x[0] >= 500) {
            inGame = false;
        }


        // Check bottom wall
        if (y[0] >= 500) {
            inGame = false;
        }


        // Check left wall
        if (x[0] < 0) {
            inGame = false;
        }


        // Check top wall
        if (y[0] < 0) {
            inGame = false;
        }


        // Stop game
        if (!inGame) {

            timer.stop();

            restartButton.setVisible(true);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            // Move first
            move();

            // Then check food
            checkFood();

            // Then check collision
            checkCollision();
        }

        repaint();
    }


    public void restartGame() {

        // Hide restart button
        restartButton.setVisible(false);

        // Reset game
        dots = 3;
        score = 0;

        // Reset directions
        leftDirection = false;
        rightDirection = true;
        upDirection = false;
        downDirection = false;

        // Reset snake position
        for (int i = 0; i < dots; i++) {

            y[i] = 100;
            x[i] = 150 - i * dotSize;
        }

        // Put food in a new position
        locateFood();

        // Start the game again
        inGame = true;

        // Hide Restart button
        restartButton.setVisible(false);

        // Start timer
        timer.start();

        // Give keyboard focus back to the game
        requestFocusInWindow();

        // Refresh screen
        repaint();
    }


    public class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();


            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {

                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }


            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {

                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }


            if (key == KeyEvent.VK_UP && (!downDirection)) {

                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }


            if (key == KeyEvent.VK_DOWN && (!upDirection)) {

                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}